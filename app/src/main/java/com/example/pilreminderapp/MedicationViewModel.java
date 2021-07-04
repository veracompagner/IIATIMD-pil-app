package com.example.pilreminderapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MedicationViewModel extends AndroidViewModel {

    private MedicationRepository repository;
    private LiveData<List<Medication>> allMedication;

    public MedicationViewModel(@NonNull Application application) {
        super(application);
        repository = new MedicationRepository(application);
        allMedication = repository.getAllMedication();
    }

    public void insert(Medication pil){
        repository.insert(pil);
    }

    public void update(Medication pil){
        repository.update(pil);
    }

    public void delete(Medication pil) {
        repository.delete(pil);
    }

    public void deleteAllMedication(){
        repository.deleteAllMedication();
    }

    public LiveData<List<Medication>> getAllMedication() {
        return allMedication;
    }
}
