package com.example.pilreminderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MedicationFragment extends Fragment {

    Context context;

    public MedicationFragment(Context ct) {
        context = ct;
    }

    public static MedicationViewModel medicationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pil_overzicht, container, false);
        FloatingActionButton buttonAddPil = view.findViewById(R.id.button_add_pil);
        buttonAddPil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Medication medication = new Medication();
                openMedicationActivity(medication, true);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.medicationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(false);

        final MedicationAdapter adapter = new MedicationAdapter();
        recyclerView.setAdapter(adapter);

        medicationViewModel = new ViewModelProvider(getActivity()).get(MedicationViewModel.class);
        medicationViewModel.getAllMedication().observe(getViewLifecycleOwner(), new Observer<List<Medication>>() {
            @Override
            public void onChanged(@Nullable List<Medication> medications) {
                adapter.submitList(medications);
            }

        });

        adapter.setOnItemClickListener(new MedicationAdapter.OnItemClickListener() {
            @Override
            public boolean onItemClick(Medication medication) {
                openMedicationActivity(medication, false);
                return false;
            }
        });

        // Werkt om de één of andere reden niet goed. Item komt weer terug na refres.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                medicationViewModel.delete(adapter.getMedicationAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Pil verwijderd", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    public void openMedicationActivity(Medication medication, boolean createNew){
        Intent intent = new Intent(getActivity(), MedicationActivity.class);
        intent.putExtra("medication", medication);
        intent.putExtra("createNew", createNew);
        startActivity(intent);
    }

}