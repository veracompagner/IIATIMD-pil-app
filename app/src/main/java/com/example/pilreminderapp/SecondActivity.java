package com.example.pilreminderapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    ImageView imageView2;
    TextView titleSecond, beschrijvingSecond;

    String data1, data2;
    int myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imageView2 = findViewById(R.id.imageView2);
        titleSecond = findViewById(R.id.medications_names_second);
        beschrijvingSecond = findViewById(R.id.beschrijving_second);

        getData();
        setData();
    }

    private void getData() {
        if (getIntent().hasExtra("MyImage") && getIntent().hasExtra("data1") && getIntent().hasExtra("data2")) {

            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            myImage = getIntent().getIntExtra("MyImage", 1);

        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setData(){
        titleSecond.setText(data1);
        beschrijvingSecond.setText(data2);
        imageView2.setImageResource(myImage);
    }
}
