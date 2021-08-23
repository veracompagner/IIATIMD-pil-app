package com.example.pilreminderapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {


    TextView questionSecond, answerSecond;

    String data1, data2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        questionSecond = findViewById(R.id.question_second);
        answerSecond = findViewById(R.id.answer_second);

        getData();
        setData();
    }

    private void getData() {
        if (getIntent().hasExtra("data1") && getIntent().hasExtra("data2")) {

            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");


        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData(){
        questionSecond.setText(data1);
        answerSecond.setText(data2);

    }
}
