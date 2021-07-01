package com.example.pilreminderapp;

import android.util.Log;

public class GetMedicationTask implements Runnable{

    AppDatabase db;

    public GetMedicationTask(AppDatabase db){
        this.db = db;
    }

    @Override
    public void run() {
        String name = db.medicationDAO().getAll().get(0).getName();
        Log.d("dbtest", name);
    }
}
