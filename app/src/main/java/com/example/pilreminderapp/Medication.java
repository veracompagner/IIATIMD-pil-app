package com.example.pilreminderapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Medication {

    @ColumnInfo
    private String name;
    @ColumnInfo
    private String beschrijving;
    @PrimaryKey
    private int uuid;

    public Medication(String name, String beschrijving, int uuid){
        this.name = name;
        this.beschrijving = beschrijving;
        this.uuid = uuid;
    }

    public String getName(){
        return this.name;
    }

    public String getBeschrijving(){
        return this.beschrijving;
    }

    public int getUuid(){
        return this.uuid;
    }
}
