package com.example.pilreminderapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

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
                    oldItem.getDose().equals(newItem.getDose());
        }
    };

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_card, parent, false);
        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position) {
        Medication currentMedication = getItem(position);
        holder.nameTextView.setText(currentMedication.getName());
        holder.doseTextView.setText(currentMedication.getDose());
        if (currentMedication.getTimerEnabled()){
            holder.timerImg.setImageResource(R.drawable.ic_baseline_notifications_active_24);
        }
        else{
            holder.timerImg.setImageResource(R.drawable.ic_baseline_notifications_off_24);

        }

    }

    public Medication getMedicationAt(int position){
        return getItem(position);
    }

    class MedicationViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView doseTextView;
        private ImageView timerImg;
        ConstraintLayout mainLayout;

        public MedicationViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.MedicationName);
            doseTextView = view.findViewById(R.id.dose);
            mainLayout = view.findViewById(R.id.mainLayout);
            timerImg = view.findViewById(R.id.timerImg);
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