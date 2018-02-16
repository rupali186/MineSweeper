package com.example.rupali.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {
    LinearLayout rootLayout;
    final static int BLANK=0;
    final static int MINE=-1;
    int notMineRow=0,notMineCol=0;
    int rows=8,cols=8,mines=10;
    int count=0,maxCount=rows*cols-mines,clickCount=0,flagCount=0;
    boolean gameOver=false;
    int rowIndexes[]={-1,-1,-1,0,0,1,1,1};
    int colIndexes[]={-1,0,1,-1,1,-1,0,1};
    MyButton board[][];
    SharedPreferences sharedPreferences;
    static final String FIRST="first",SECOND="second",THIRD="third",FOURTH="fourth",FIFTH="fifth",LASTWINS="lastWins";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout=findViewById(R.id.rootLayout);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        Toast.makeText(this,"Welcome "+bundle.getString("name"),Toast.LENGTH_SHORT).show();
        rows=bundle.getInt("row");
        cols=bundle.getInt("col");
        mines=bundle.getInt("mines");
        maxCount=rows*cols-mines;
        sharedPreferences=getSharedPreferences(LASTWINS,MODE_PRIVATE);
        initGame();

    }

    private void initGame() {
        count=0;
        clickCount=0;
        flagCount=0;
        gameOver=false;
        board=new MyButton[rows][cols];
        constructBoard();
    }
    private void constructBoard() {
        rootLayout.removeAllViews();
        for(int i=0;i<rows;i++){
            LinearLayout rowLayout=new LinearLayout(this);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            rowLayout.setLayoutParams(params);
            for(int j=0;j<cols;j++){
                MyButton button=new MyButton(this);
                LinearLayout.LayoutParams buttonParams=new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1);
                button.setLayoutParams(buttonParams);
                button.setRowIndex(i);
                button.setColIndex(j);
                button.setEnabled(true);
                button.setOnClickListener(this);
                button.setOnLongClickListener(this);
                board[i][j]=button;
                rowLayout.addView(button);
            }
            rootLayout.addView(rowLayout);
        }
    }
    private void setMines() {
        Random r=new Random();
        for(int i=0;i<mines;){
            int row=r.nextInt(rows);
            int col=r.nextInt(cols);
            if(board[row][col].getValue()!=MINE&&row!=notMineRow&&col!=notMineCol) {
                board[row][col].setValue();
                checkNeighbours(row,col);
                i++;
            }
        }
    }

    private void checkNeighbours(int i, int j) {
        int row,col;
        for(int k=0;k<rowIndexes.length;k++){
            row=i+rowIndexes[k];
            col=j+colIndexes[k];
            if(row>=0&&row<rows&&col>=0&&col<cols&&board[row][col].getValue()!=MINE){
                board[row][col].incrementValue();
            }
        }
    }

    @Override
    public void onClick(View view) {
        clickCount++;
        MyButton button=(MyButton)view;
        if(clickCount==1){
            notMineRow=button.getRowIndex();
            notMineCol=button.getColIndex();
            setMines();
        }
        if(button.getValue()==MINE){
            int score=0;
            for(int i=0;i<rows;i++){
                for(int j=0;j<cols;j++){
                    if(!board[i][j].isEnabled()){
                        score++;
                    }
                    board[i][j].displayValue();
                }
            }
            Toast.makeText(this,"GameOver Score:"+score+"/"+rows*cols,Toast.LENGTH_SHORT).show();
            putInSharedPreference(score);
            gameOver=true;
        }
        else{
            button.displayValue();
            count++;
            if(count==maxCount) {
                checkWin();
                return;
            }
            if(button.getValue()==BLANK) {
                displayNeighbours(button);
            }
        }

    }

    private void putInSharedPreference(int score) {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(sharedPreferences.getAll().size()==5){
            editor.putString(FIRST,sharedPreferences.getString(SECOND,""));
            editor.putString(SECOND,sharedPreferences.getString(THIRD,""));
            editor.putString(THIRD,sharedPreferences.getString(FOURTH,""));
            editor.putString(FOURTH,sharedPreferences.getString(FIFTH,""));
            editor.putString(FIFTH,"Score: "+score+"/"+rows*cols);
        }
        else{
            if(sharedPreferences.getString(FIRST,"").equals("")){
                editor.putString(FIRST,"Score: "+score+"/"+rows*cols);
            }
            else if(sharedPreferences.getString(SECOND,"").equals("")){
                editor.putString(SECOND,"Score: "+score+"/"+rows*cols);
            }
            else if(sharedPreferences.getString(THIRD,"").equals("")){
                editor.putString(THIRD,"Score: "+score+"/"+rows*cols);
            }
            else if(sharedPreferences.getString(FOURTH,"").equals("")){
                editor.putString(FOURTH,"Score: "+score+"/"+rows*cols);
            }
            else if(sharedPreferences.getString(FIFTH,"").equals("")){
                editor.putString(FIFTH,"Score: "+score+"/"+rows*cols);
            }

        }
        editor.commit();
    }

    void checkWin() {
        if (count == maxCount&&flagCount==mines) {
            gameOver = true;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    board[i][j].setEnabled(false);
                }
            }
            Toast.makeText(this, "You Won Score:"+rows*cols+"/"+rows*cols, Toast.LENGTH_SHORT).show();
            putInSharedPreference(rows*cols);
        }
    }
    private void displayNeighbours(MyButton button) {
        int i=button.getRowIndex(),j=button.getColIndex();
        int row,col;
        for(int k=0;k<rowIndexes.length;k++){
            row=i+rowIndexes[k];
            col=j+colIndexes[k];
            if(row>=0&&row<rows&&col>=0&&col<cols&&board[row][col].getValue()!=MINE&&board[row][col].isEnabled()&&!board[row][col].isFlag()){
                board[row][col].displayValue();
                count++;
                if(count==maxCount) {
                    checkWin();
                    return;
                }
                if(board[row][col].getValue()==BLANK){
                    displayNeighbours(board[row][col]);
                }
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        MyButton button= (MyButton) view;
        if(button.isFlag()){
            button.setFlag(false);
            flagCount--;
        }
        else{
            button.setFlag(true);
            flagCount++;
        }
        checkWin();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.reset){
            initGame();
        }
        return super.onOptionsItemSelected(item);
    }
}