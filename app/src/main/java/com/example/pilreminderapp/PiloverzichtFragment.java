package com.example.pilreminderapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PiloverzichtFragment extends Fragment {

    Context context;

    private RecyclerView recyclerView;
    public PiloverzichtFragment(Context ct){
        context = ct;
    }

    String s1[], s2[];
    //hard code ophalen van afbeeldingen van medicatie
    int image[] = {R.drawable.pil1,R.drawable.pil2,R.drawable.pil3,R.drawable.pil4};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pil_overzicht, container, false);

        recyclerView = view.findViewById(R.id.medicationRecyclerView);
        //hard code ophalen van de medicatie (die moet vanaf een database komen nog)
        s1 = getResources().getStringArray(R.array.medications_names);
        s2 = getResources().getStringArray(R.array.beschrijving);

        MedicationAdapter medicationAdapter = new MedicationAdapter(context, s1, s2, image);
        recyclerView.setAdapter(medicationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;



    }


}
