package com.example.pilreminderapp;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class KalenderFragment extends Fragment{


    public KalenderFragment(MainActivity a){
        activity = a;
    }

    MainActivity activity;
    Button fab;
    EditText nameOfTimer;
    EditText timeOfTimer;
    CheckBox repeatOfTimer;
    TimePickerDialog timePickerDialog;
    int hourOfDay;
    int minutes;
    boolean repeat;
    int tOT;
    String time;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kalender, container, false);

        Button fab = (Button) view.findViewById(R.id.fab);
        nameOfTimer = view.findViewById(R.id.nameOfTimer);
        repeatOfTimer = view.findViewById(R.id.repeatOfTimer);

        timeOfTimer = view.findViewById(R.id.timeOfTimer);
        timeOfTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        hourOfDay = h;
                        minutes = m;
                        timeOfTimer.setText(hourOfDay + ":" + minutes);
                    }
                }, 0,0, false);
                timePickerDialog.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activity.scheduleNotification(activity, hourOfDay, minutes, repeatOfTimer.isChecked(), 42);
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setCancelable(true);
                builder.setTitle("Timer is gezet op:");
                builder.setMessage((hourOfDay + ":" + minutes));

                builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
        return view;
    }

}
