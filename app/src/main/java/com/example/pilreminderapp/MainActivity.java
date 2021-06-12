package com.example.pilreminderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String s1[], s2[];
    //hard code ophalen van afbeeldingen van medicatie
    int image[] = {R.drawable.pil1,R.drawable.pil2,R.drawable.pil3,R.drawable.pil4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.medicationRecyclerView);


        //hard code ophalen van de medicatie (die moet vanaf een database komen nog)
        s1 = getResources().getStringArray(R.array.medications_names);
        s2 = getResources().getStringArray(R.array.beschrijving);

        MedicationAdapter medicationAdapter = new MedicationAdapter(this, s1, s2, image);
        recyclerView.setAdapter(medicationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


}