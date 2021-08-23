package com.example.pilreminderapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FaqDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Faq> faqList);

    @Query("SELECT * FROM faq_table")
    LiveData<List<Faq>> getAllFaqs();

    @Query("DELETE FROM faq_table")
    void deleteAll();

}
