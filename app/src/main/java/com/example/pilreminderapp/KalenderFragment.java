package com.example.pilreminderapp;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    TimePickerDialog timePickerDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kalender, container, false);

        Button fab = (Button) view.findViewById(R.id.fab);
        nameOfTimer = view.findViewById(R.id.nameOfTimer);

        timeOfTimer = view.findViewById(R.id.timeOfTimer);
        timeOfTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuts) {
                        timeOfTimer.setText(hourOfDay + ":" + minuts);
                    }
                }, 0,0, false);
                timePickerDialog.show();
            }
        });
        //delay met button's bijvoorbeeld



        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("notescreen", "This is the text: " + nameOfTimer.getText());
                activity.scheduleNotification(activity, 1000, 24);

            }
        });

        return view;
    }

}
