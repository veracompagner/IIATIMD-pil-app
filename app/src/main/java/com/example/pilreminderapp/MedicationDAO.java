package com.example.pilreminderapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedicationDAO {

    @Query("SELECT * FROM medication")
        List<Medication> getAll();

    @Insert
    void InsertMedication(Medication pillen);

    @Delete
    void delete(Medication pillen);

}
