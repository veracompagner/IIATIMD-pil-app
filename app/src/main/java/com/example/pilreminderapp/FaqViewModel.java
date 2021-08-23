package com.example.pilreminderapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class FaqViewModel extends AndroidViewModel {

    private FaqRepository faqRepository;
    private LiveData<List<Faq>> getAllFaqs;

    public FaqViewModel(@NonNull Application application){
        super(application);
        faqRepository = new FaqRepository(application);
        getAllFaqs = faqRepository.getAllFaqs();
    }

    public void insert(List<Faq> list){
        faqRepository.insert(list);
    }

    public LiveData<List<Faq>> getAllFaqs(){
        return getAllFaqs;
    }


}
