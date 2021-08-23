package com.example.pilreminderapp;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Medication.class, Faq.class}, version = 14, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract MedicationDAO medicationDAO();
    public abstract FaqDAO faqDAO();

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "appdatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
            }
            return instance;
        }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private MedicationDAO medicationDAO;
        private FaqDAO faqDAO;

        private PopulateDbAsyncTask(AppDatabase db){
            medicationDAO = db.medicationDAO();
            faqDAO = db.faqDAO();
        }

        @Override
        protected Void doInBackground(Void... voids){
            medicationDAO.insert(new Medication("Antibiotica", "Dose: 1", Medication.Repeat.DAILY,22,00, true));
            medicationDAO.insert(new Medication("Anticonceptie", "Dose: 1", Medication.Repeat.DAILY,22,00,true));
            medicationDAO.insert(new Medication("Omega 3", "Dose: 1", Medication.Repeat.DAILY,22,00,false));
            medicationDAO.insert(new Medication("Vitamine D", "Dose: 1", Medication.Repeat.MONTHLY,22,00,false));
            medicationDAO.insert(new Medication("Zink", "Dose: 1", Medication.Repeat.WEEKLY,22,00,false));
            faqDAO.deleteAll();
            return null;
        }

    }

}
