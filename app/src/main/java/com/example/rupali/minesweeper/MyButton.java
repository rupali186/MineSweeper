package com.example.rupali.minesweeper;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;

/**
 * Created by RUPALI on 01-02-2018.
 */

public class MyButton extends AppCompatButton {
    private int value;
    private boolean flag;
    private int rowIndex;
    private int colIndex;
    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }
    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
        if(flag) {
            //setText("F");
            setBackgroundResource(R.drawable.flag);
        }
        else{
            //setText("");
           setBackgroundResource(R.drawable.button_bg);
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public int getValue() {
        return value;
    }

    public MyButton(Context context) {
        super(context);
        setBackgroundResource(R.drawable.button_bg);
        value=MainActivity.BLANK;
    }
    void setValue(){
        value=MainActivity.MINE;
    }
    void incrementValue(){
        value++;
    }
    void displayValue(){
       switch(value){
           case MainActivity.MINE:
               setBackgroundResource(R.drawable.mine_n);
               break;
           case 1:
               setBackgroundResource(R.drawable.one);
               break;
           case 2:
               setBackgroundResource(R.drawable.two);
               break;
           case 3:
               setBackgroundResource(R.drawable.three);
               break;
           case 4:
               setBackgroundResource(R.drawable.four);
               break;
           case 5:
               setBackgroundResource(R.drawable.five);
               break;
           case 6:
               setBackgroundResource(R.drawable.six);
               break;
           case 7:
               setBackgroundResource(R.drawable.seven);
               break;
           default:
               setBackgroundResource(R.drawable.blank);

       }
        setEnabled(false);
    }

}
