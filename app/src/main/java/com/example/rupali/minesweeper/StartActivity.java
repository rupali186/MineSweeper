package com.example.rupali.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        editText=findViewById(R.id.editText1);
    }

    public void onClick(View view) {
        int id=view.getId();
        Intent intent=new Intent(this,MainActivity.class);
        Bundle bundle=new Bundle();
        String name=editText.getText().toString();
        bundle.putString("name",name);
        switch (id){
            case R.id.begginer:
                bundle.putInt("row",8);
                bundle.putInt("col",8);
                bundle.putInt("mines",10);
                intent.putExtras(bundle);
                break;
            case R.id.intermediate:
                bundle.putInt("row",16);
                bundle.putInt("col",16);
                bundle.putInt("mines",40);
                intent.putExtras(bundle);
                break;
            case R.id.expert:
                bundle.putInt("row",24);
                bundle.putInt("col",24);
                bundle.putInt("mines",99);
                intent.putExtras(bundle);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.lastFiveWin){
            Intent intent=new Intent(this,LastWin.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
