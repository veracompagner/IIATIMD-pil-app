package com.example.pilreminderapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MedicationAdapter extends ListAdapter<Medication, MedicationAdapter.MedicationViewHolder> {

    private OnItemClickListener listener;

    public MedicationAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Medication> DIFF_CALLBACK = new DiffUtil.ItemCallback<Medication>() {
        @Override
        public boolean areItemsTheSame(Medication oldItem, Medication newItem) {
            return oldItem.getUuid() == newItem.getUuid();
        }

        @Override
        public boolean areContentsTheSame(Medication oldItem, Medication newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getBeschrijving().equals(newItem.getBeschrijving());
        }
    };


    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medication_card, parent, false);
        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position) {
        Medication currentMedication = getItem(position);
        holder.textViewName.setText(currentMedication.getName());
        holder.textViewBeschrijving.setText(currentMedication.getBeschrijving());
    }

    public Medication getMedicationAt(int position){
        return getItem(position);
    }

    class MedicationViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewBeschrijving;
        ConstraintLayout mainLayout;

        public MedicationViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.MedicationName);
            textViewBeschrijving = view.findViewById(R.id.beschrijving);
            mainLayout = view.findViewById(R.id.mainLayout);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }

    }

    public interface OnItemClickListener{
        boolean onItemClick(Medication medication);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}