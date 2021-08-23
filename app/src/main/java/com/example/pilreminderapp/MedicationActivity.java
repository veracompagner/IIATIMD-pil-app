package com.example.pilreminderapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.List;

public class MedicationActivity extends AppCompatActivity {

    private EditText nameEditText, doseEditText, timeOfTimer;
    private Spinner scheduleSpinner;
    private Switch timerEnabled;
    private Button saveButton;
    private boolean editEnabled;
    private boolean createNew;
    private MedicationViewModel medicationViewModel;
    private TimePickerDialog timePickerDialog;

    private Medication medication;


    private void enableEdit(boolean enable){
        nameEditText.setEnabled(enable);
        doseEditText.setEnabled(enable);
        scheduleSpinner.setEnabled(enable);
        timeOfTimer.setEnabled(enable);
        timerEnabled.setEnabled(enable);
        if (enable){
            saveButton.setVisibility(View.VISIBLE);
        }else {
            saveButton.setVisibility(View.INVISIBLE);
        }
        editEnabled = enable;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        medicationViewModel = new ViewModelProvider(this).get(MedicationViewModel.class);

        medication = (Medication) getIntent().getSerializableExtra("medication");

        nameEditText = findViewById(R.id.medication_name);
        doseEditText = findViewById(R.id.medication_dose);
        scheduleSpinner = findViewById(R.id.medication_schedule);
        timeOfTimer = findViewById(R.id.medication_time_of_timer);
        timerEnabled = findViewById(R.id.medication_timer_enabled);
        saveButton = findViewById(R.id.medication_save_button);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.repeat_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scheduleSpinner.setAdapter(adapter);

        createNew = getIntent().getBooleanExtra("createNew", false);
        enableEdit(createNew);
        updateUI();

        timeOfTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(MedicationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        medication.setHour(h);
                        medication.setMinute(m);
                        timeOfTimer.setText((h + ":" + m));
                        timerEnabled.setChecked(true);
                    }
                }, 0,0, false);
                timePickerDialog.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medication.setName(nameEditText.getText().toString());
                medication.setDose(doseEditText.getText().toString());
                Medication.Repeat repeat = Medication.Repeat.values()[scheduleSpinner.getSelectedItemPosition()];
                medication.setSchedule(repeat);
                medication.setTimerEnabled(timerEnabled.isChecked());

                if (createNew) {
                    MedicationFragment.medicationViewModel.insert(medication);
                }
                else {
                    MedicationFragment.medicationViewModel.update(medication);
                }

                scheduleNotification(MedicationActivity.this, medication.getHour(), medication.getMinute(), repeat, medication.getUuid(), timerEnabled.isChecked());

                enableEdit(false);
                finish();
            }
        });

    }

    public void scheduleNotification(Context context, int hourOfDay, int minutes, Medication.Repeat repeat, int notificationId, boolean enable) {
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.voorbeeld);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(nameEditText.getText())
                .setContentText(doseEditText.getText())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_baseline_calendar_today_24)
                .setLargeIcon(largeIcon)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        builder.setChannelId("hoi");
        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (enable){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minutes);

            int day_msec = 1000 * 60 * 60 * 24;
            int week_msec = 7 * day_msec;
            int month_msec = 4 * week_msec;
            int interval = 0;

            switch (repeat){
                case DAILY:
                    interval = day_msec;
                    break;
                case WEEKLY:
                    interval = week_msec;
                    break;
                case MONTHLY:
                    interval = month_msec;
                    break;
            }

            if (interval > 0){
                manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
            } else {
                manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
        else {
            manager.cancel(pendingIntent);
        }

    }

    private void updateUI(){
        nameEditText.setText(medication.getName());
        doseEditText.setText(medication.getDose());
        scheduleSpinner.setSelection(medication.getSchedule().ordinal());
        timeOfTimer.setText(medication.getHour() + ":" + medication.getMinute());
        timerEnabled.setChecked(medication.getTimerEnabled());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bewerken_menu, menu);
        menuInflater.inflate(R.menu.delete_menu, menu);
        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu){
//        super.onPrepareOptionsMenu(menu);
//        //MenuItem item = menu.findItem(R.menu.bewerken_menu);
//
//        editMenuItem.setVisible(!editEnabled);
//
//        return true;
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bewerk_pil:
                enableEdit(true);
                return true;
            case R.id.verwijder_pil:
                MedicationFragment.medicationViewModel.delete(medication);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


