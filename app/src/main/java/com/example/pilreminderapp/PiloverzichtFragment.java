package com.example.pilreminderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
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

public class PiloverzichtFragment extends Fragment {

    Context context;

    private RecyclerView recyclerView;

    public PiloverzichtFragment(Context ct){
        context = ct;
    }

    private MedicationViewModel medicationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pil_overzicht, container, false);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            String name = data.getStringExtra(AddPilActivity.EXTRA_NAME);
                            String beschrijving = data.getStringExtra(AddPilActivity.EXTRA_BESCHRIJVING);

                            Medication medication = new Medication(name, beschrijving);
                            medicationViewModel.insert(medication);
                            Toast.makeText(getActivity(), "Pil toegevoegd", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        FloatingActionButton buttonAddPil = view.findViewById(R.id.button_add_pil);
        buttonAddPil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), AddPilActivity.class);
                    someActivityResultLauncher.launch(intent);
            }
        });

        recyclerView = view.findViewById(R.id.medicationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);

        final MedicationAdapter adapter = new MedicationAdapter();
        recyclerView.setAdapter(adapter);

        medicationViewModel = new ViewModelProvider(this).get(MedicationViewModel.class);
        medicationViewModel.getAllMedication().observe(getViewLifecycleOwner(), new Observer<List<Medication>>() {
            @Override
            public void onChanged(List<Medication> pillen) {
                adapter.submitList(pillen);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                medicationViewModel.delete(adapter.getMedicationAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Pil verwijderd", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

}