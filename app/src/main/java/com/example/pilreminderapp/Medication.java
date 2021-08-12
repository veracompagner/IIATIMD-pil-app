package com.example.pilreminderapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "medication_table")
public class Medication implements Serializable {

        public enum Repeat {
        ONCE,
        DAILY,
        WEEKLY,
        MONTHLY
    }

    @ColumnInfo
    private String name;
    @ColumnInfo
    private String dose;
    @ColumnInfo
    private Repeat schedule = Repeat.ONCE;
    @ColumnInfo
    private int hour = 0;
    @ColumnInfo
    private int minute = 0;
    @ColumnInfo
    private boolean timerEnabled = false;

    @PrimaryKey(autoGenerate = true)
    private int uuid;

    public Medication(){
    }

    public Medication(String name, String dose, Repeat schedule, int hour, int minute, boolean timerEnabled) {
        this.name = name;
        this.dose = dose;
        this.schedule = schedule;
        this.hour = hour;
        this.minute = minute;
        this.timerEnabled = timerEnabled;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDose(){
        return dose;
    }
    public void setDose(String dose){
        this.dose = dose;
    }

    public Repeat getSchedule(){
        return schedule;
    }
    public void setSchedule(Repeat schedule){
        this.schedule = schedule;
    }

    public int getHour(){
        return hour;
    }
    public void setHour(int hour){
        this.hour = hour;
    }

    public int getMinute(){
        return minute;
    }
    public void setMinute(int minute){
        this.minute = minute;
    }

    public boolean getTimerEnabled(){
        return timerEnabled;
    }
    public void setTimerEnabled(boolean timerEnabled){
        this.timerEnabled = timerEnabled;
    }

    public int getUuid(){
        return uuid;
    }
    public void setUuid(int uuid) {
        this.uuid = uuid;
    }
}