package com.example.pilreminderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MedicationAdapter extends ListAdapter<Medication, MedicationAdapter.MedicationViewHolder> {

    Context context;

    public MedicationAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Medication> DIFF_CALLBACK = new DiffUtil.ItemCallback<Medication>() {
        @Override
        public boolean areItemsTheSame(@NonNull Medication oldItem, @NonNull Medication newItem) {
            return oldItem.getUuid() == newItem.getUuid();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Medication oldItem, @NonNull Medication newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getBeschrijving().equals(newItem.getBeschrijving());
        }
    };

    public class MedicationViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewBeschrijving;
        ConstraintLayout mainLayout;

        public MedicationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.MedicationName);
            textViewBeschrijving = itemView.findViewById(R.id.beschrijving);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    //private List<Medication> pillen = new ArrayList<>();

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_card, parent, false);

        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position) {
        Medication currentMedication = getItem(position);
        holder.textViewName.setText(currentMedication.getName());
        holder.textViewBeschrijving.setText(currentMedication.getBeschrijving());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("data1", currentMedication.getName());
                intent.putExtra("data2", currentMedication.getBeschrijving());
                context.startActivity(intent);
            }
        });
    }

    public Medication getMedicationAt(int position){
        return getItem(position);
    }

}