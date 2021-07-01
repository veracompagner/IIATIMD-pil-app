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
import androidx.recyclerview.widget.RecyclerView;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder> {

    private Medication[] pillen;
    Context context;

    public class MedicationViewHolder extends RecyclerView.ViewHolder {

        TextView text1, text2;
        //ImageView myImage;
        ConstraintLayout mainLayout;

        public MedicationViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.MedicationName);
            text2 = itemView.findViewById(R.id.beschrijving);
            //myImage = itemView.findViewById(R.id.imageView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    public MedicationAdapter(Context ct, Medication[] pillen){
        this.pillen = pillen;
        context = ct;
    }

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_card, parent, false);
        MedicationViewHolder medicationViewHolder = new MedicationViewHolder(view);
        return medicationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position) {

        holder.text1.setText(pillen[position].getName());
        holder.text2.setText(pillen[position].getBeschrijving());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("data1", pillen[position].getName());
                intent.putExtra("data2", pillen[position].getBeschrijving());
                //intent.putExtra("MyImage", images[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pillen.length;
    }

}