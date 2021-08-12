package com.example.pilreminderapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MedicationDAO {

    @Insert
    void insert(Medication medication);

    @Update
    void update(Medication medication);

    @Delete
    void delete(Medication medication);

    @Query("SELECT * FROM medication_table ORDER BY name ASC")
    LiveData<List<Medication>> getAllMedication();

    @Query("DELETE FROM medication_table")
    void deleteAllMedication ();

}
