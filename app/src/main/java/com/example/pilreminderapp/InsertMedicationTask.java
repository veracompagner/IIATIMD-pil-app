package com.example.pilreminderapp;

import android.util.Log;

public class InsertMedicationTask implements Runnable {

    AppDatabase db;
    Medication pillen;

    public InsertMedicationTask(AppDatabase db, Medication pillen){
        this.db = db;
        this.pillen = pillen;
    }
    @Override
    public void run() {
        db.medicationDAO().InsertMedication(this.pillen);

    }
}
