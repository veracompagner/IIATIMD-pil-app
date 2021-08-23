package com.example.pilreminderapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


public class MedicationRepository {
    private MedicationDAO medicationDAO;
    private LiveData<List<Medication>> allMedication;

    public MedicationRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        medicationDAO = database.medicationDAO();
        allMedication = medicationDAO.getAllMedication();
    }


    public void insert(Medication medication){
        new InsertMedicationAsyncTask(medicationDAO).execute(medication);
    }

    public void update(Medication medication){
        new UpdateMedicationAsyncTask(medicationDAO).execute(medication);
    }

    public void delete(Medication medication){
        new DeleteMedicationAsyncTask(medicationDAO).execute(medication);
    }


    public void deleteAllMedication(){
        new DeleteAllMedicationAsyncTask(medicationDAO).execute();
    }

    public LiveData<List<Medication>> getAllMedication() {
        return allMedication;
    }

    private static class InsertMedicationAsyncTask extends AsyncTask<Medication, Void, Void>{
        private MedicationDAO medicationDAO;

        private InsertMedicationAsyncTask(MedicationDAO medicationDAO){
            this.medicationDAO = medicationDAO;
        }

        @Override
        protected Void doInBackground(Medication... medication){
            medicationDAO.insert(medication[0]);
            return null;
        }
    }

    private static class UpdateMedicationAsyncTask extends AsyncTask<Medication, Void, Void>{
        private MedicationDAO medicationDAO;

        private UpdateMedicationAsyncTask(MedicationDAO medicationDAO){
            this.medicationDAO = medicationDAO;
        }

        @Override
        protected Void doInBackground(Medication... medication){
            medicationDAO.update(medication[0]);
            return null;
        }
    }

    private static class DeleteMedicationAsyncTask extends AsyncTask<Medication, Void, Void>{
        private MedicationDAO medicationDAO;

        private DeleteMedicationAsyncTask(MedicationDAO medicationDAO){
            this.medicationDAO = medicationDAO;
        }

        @Override
        protected Void doInBackground(Medication... medication){
            medicationDAO.delete(medication[0]);
            return null;
        }
    }

    private static class DeleteAllMedicationAsyncTask extends AsyncTask<Void, Void, Void>{
        private MedicationDAO medicationDAO;
        private DeleteAllMedicationAsyncTask(MedicationDAO medicationDAO){
            this.medicationDAO = medicationDAO;
        }

        @Override
        protected Void doInBackground(Void... voids){
            medicationDAO.deleteAllMedication();
            return null;
        }
    }

}
