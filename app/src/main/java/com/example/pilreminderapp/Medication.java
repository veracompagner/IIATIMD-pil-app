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
    private String innamen;
    @PrimaryKey(autoGenerate = true)
    private int uuid;

    /*
    public Medication(String name, String beschrijving, int uuid){
        this.name = name;
        this.beschrijving = beschrijving;
        this.uuid = uuid;
    }

     */

    public Medication(String name, String beschrijving, String innamen) {
        this.name = name;
        this.beschrijving = beschrijving;
        this.innamen = innamen;
    }

    public String getName(){
        return name;
    }

    public String getBeschrijving(){
        return beschrijving;
    }

    public String getInnamen(){
        return innamen;
    }

    public int getUuid(){
        return uuid;
    }

    public void setUuid(int id) {
        this.uuid = uuid;
    }
}