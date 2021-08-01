package com.example.pilreminderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

    public PiloverzichtFragment(Context ct) {
        context = ct;
    }

    private MedicationViewModel medicationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pil_overzicht, container, false);

        ActivityResultLauncher<Intent> addEditActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            String name = data.getStringExtra(AddEditPilActivity.EXTRA_NAME);
                            String beschrijving = data.getStringExtra(AddEditPilActivity.EXTRA_BESCHRIJVING);
                            String innamen = data.getStringExtra(AddEditPilActivity.EXTRA_INNAMEN);

                            Medication medication = new Medication(name, beschrijving, innamen);
                            medicationViewModel.insert(medication);
                            Toast.makeText(getActivity(), "Pil toegevoegd", Toast.LENGTH_SHORT).show();
                        } else if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            int uuid = data.getIntExtra(AddEditPilActivity.EXTRA_UUID, -1);
                            if (uuid == -1) {
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String name = data.getStringExtra(AddEditPilActivity.EXTRA_NAME);
                            String beschrijving = data.getStringExtra(AddEditPilActivity.EXTRA_BESCHRIJVING);
                            String innamen = data.getStringExtra(AddEditPilActivity.EXTRA_INNAMEN);

                            Medication medication = new Medication(name, beschrijving, innamen);
                            medication.setUuid(uuid);
                            medicationViewModel.update(medication);
                            Toast.makeText(getActivity(), "Succesvol bijgewerkt", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        FloatingActionButton buttonAddPil = view.findViewById(R.id.button_add_pil);
        buttonAddPil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddEditPilActivity.class);
                addEditActivityResultLauncher.launch(intent);
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
                //adapter.notifyDataSetChanged();
                adapter.submitList(medications);
            }

        });

        adapter.setOnItemClickListener(new MedicationAdapter.OnItemClickListener() {
            @Override
            public boolean onItemClick(Medication medication) {
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                intent.putExtra("data1", medication.getName());
                intent.putExtra("data2", medication.getBeschrijving());
                intent.putExtra("data3", medication.getInnamen());
                intent.putExtra("uuid", medication.getUuid());
                startActivity(intent);
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
    }