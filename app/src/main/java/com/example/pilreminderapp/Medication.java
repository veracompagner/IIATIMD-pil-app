package com.example.pilreminderapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medication_table")
public class Medication {

    @ColumnInfo
    private String name;
    @ColumnInfo
    private String beschrijving;
    @ColumnInfo
    private int herhaal;
    @PrimaryKey(autoGenerate = true)
    private int uuid;


    public Medication(String name, String beschrijving, int herhaal) {
        this.name = name;
        this.beschrijving = beschrijving;
        this.herhaal = herhaal;
    }

    public String getName(){
        return name;
    }
    public String getBeschrijving(){
        return beschrijving;
    }
    public int getHerhaal(){
        return herhaal;
    }

    public int getUuid(){
        return uuid;
    }

    public void setUuid(int id) {
        this.uuid = uuid;
    }
}