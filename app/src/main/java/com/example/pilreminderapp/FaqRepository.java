package com.example.pilreminderapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.pilreminderapp.FaqDAO;
import com.example.pilreminderapp.AppDatabase;
import com.example.pilreminderapp.Faq;

import java.util.List;

public class FaqRepository {
    private AppDatabase database;
    private LiveData<List<Faq>> getAllFaqs;

    public FaqRepository(Application application)
    {
        database=AppDatabase.getInstance(application);
        getAllFaqs=database.faqDAO().getAllFaqs();
    }

    public void insert(List<Faq> faqList){
        new InsertFaqAsynTask(database).execute(faqList);
    }

    public LiveData<List<Faq>> getAllFaqs()
    {
        return getAllFaqs;
    }

    static class InsertFaqAsynTask extends AsyncTask<List<Faq>,Void,Void>{
        private FaqDAO faqDAO;
        InsertFaqAsynTask(AppDatabase AppDatabase)
        {
            faqDAO=AppDatabase.faqDAO();
        }
        @Override
        protected Void doInBackground(List<Faq>... lists) {
            faqDAO.insert(lists[0]);
            return null;
        }
    }
}

