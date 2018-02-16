package com.example.rupali.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LastWin extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView textView1,textView2,textView3,textView4,textView5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_win);
        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
        textView5=findViewById(R.id.textView5);
        sharedPreferences=getSharedPreferences(MainActivity.LASTWINS,MODE_PRIVATE);
        textView1.setText("1. "+sharedPreferences.getString(MainActivity.FIRST,""));
        textView2.setText("2. "+sharedPreferences.getString(MainActivity.SECOND,""));
        textView3.setText("3. "+sharedPreferences.getString(MainActivity.THIRD,""));
        textView4.setText("4. "+sharedPreferences.getString(MainActivity.FOURTH,""));
        textView5.setText("5. "+sharedPreferences.getString(MainActivity.FIFTH,""));

    }
}
