package com.example.pilreminderapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "faq_table", indices = @Index(value = {"id"}, unique = true))
public class Faq {

    @PrimaryKey(autoGenerate = true)
    private int uuid;

    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("question")
    @ColumnInfo(name = "question")
    private String question;

    @SerializedName("answer")
    @ColumnInfo(name = "answer")
    private String answer;


    public Faq(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public int getUuid(){
        return uuid;
    }

    public void setUuid(int uuid){
        this.uuid = uuid;
    }


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getQuestion(){
        return question;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    @Override
    public String toString(){
        return "Faq{" + "Vraag: " + question + "Antwoord: " + answer + '}';
    }

}
