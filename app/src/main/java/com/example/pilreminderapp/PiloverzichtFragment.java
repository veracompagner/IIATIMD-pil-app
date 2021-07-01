package com.example.pilreminderapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

public class PiloverzichtFragment extends Fragment {

    Context context;

    private RecyclerView recyclerView;
    public PiloverzichtFragment(Context ct){
        context = ct;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Medication[] pillen = new Medication[6];
        pillen[0] = new Medication("Anticonceptie", "Beschrijving hier", 1);
        pillen[1] = new Medication("Antibiotica", "Beschrijving hier", 2);
        pillen[2] = new Medication("Omega 3", "Beschrijving hier", 3);
        pillen[3] = new Medication("Vitamine B", "Beschrijving hier", 4);
        pillen[4] = new Medication("Vitamine D", "Beschrijving hier", 5);
        pillen[5] = new Medication("Zink", "Beschrijving hier", 6);

        AppDatabase db = AppDatabase.getInstance(getActivity().getApplicationContext());

        //new Thread(new GetMedicationTask(db)).start();
        new Thread(new InsertMedicationTask(db, pillen[2])).start();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pil_overzicht, container, false);

        recyclerView = view.findViewById(R.id.medicationRecyclerView);
        MedicationAdapter medicationAdapter = new MedicationAdapter(context, pillen);
        recyclerView.setAdapter(medicationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return view;

    }

}
