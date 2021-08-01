package com.example.pilreminderapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    public static final int EDIT_NOTE_REQUEST = 2;
    TextView titleSecond, beschrijvingSecond;
    Spinner herhaalSecond;
    String data1, data2;
    int data4;
    int uuid;
    private MedicationViewModel medicationViewModel;


    Button fab;
    EditText nameOfTimer;
    EditText timeOfTimer;
    CheckBox repeatOfTimer;
    TimePickerDialog timePickerDialog;
    int hourOfDay;
    int minutes;
    int tOT;
    String time;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        medicationViewModel = new ViewModelProvider(this).get(MedicationViewModel.class);

        titleSecond = findViewById(R.id.medications_names_second);
        beschrijvingSecond = findViewById(R.id.beschrijving_second);
        herhaalSecond = findViewById(R.id.herhaal_second);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.repeat_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        herhaalSecond.setAdapter(adapter);
        herhaalSecond.setEnabled(false);



        getData();
        setData();




        Button fab = (Button) findViewById(R.id.fab);
        nameOfTimer = findViewById(R.id.nameOfTimer);
        repeatOfTimer = findViewById(R.id.repeatOfTimer);

        timeOfTimer = findViewById(R.id.timeOfTimer);
        timeOfTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog = new TimePickerDialog(SecondActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                scheduleNotification(SecondActivity.this, hourOfDay, minutes, herhaalSecond.getSelectedItemPosition(), 42);
                AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Timer voor " + titleSecond.getText() + " is gezet op:");
                builder.setMessage((hourOfDay + ":" + minutes));

                builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

    }

    public void scheduleNotification(Context context, int hourOfDay, int minutes, int herhaal, int notificationId) {
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.voorbeeld);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(titleSecond.getText())
               // .setContentTitle(context.getString(R.string.titleTime))

                .setContentText(beschrijvingSecond.getText())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_baseline_calendar_today_24)
                .setLargeIcon(largeIcon)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, KalenderFragment.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        builder.setChannelId("hoi");
        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minutes);

        int dag_msec = 1000 * 60 * 60 * 24;
        int week_msec = 7 * dag_msec;
        int maand_msec = 4 * week_msec;
        int interval = 0;

        switch (herhaal){
            case 1:
                interval = dag_msec;
                break;
            case 2:
                interval = week_msec;
                break;
            case 3:
                interval = maand_msec;
                break;
        }

        if (interval > 0){
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    interval, pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

    }


    private void getData() {
        if (getIntent().hasExtra("data1") && getIntent().hasExtra("data2") && getIntent().hasExtra("data4")  && getIntent().hasExtra("uuid")) {

            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            data4 = getIntent().getIntExtra("data4", 0);

        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){
        titleSecond.setText(data1);
        beschrijvingSecond.setText(data2);
        herhaalSecond.setSelection(data4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bewerken_menu, menu);
        menuInflater.inflate(R.menu.delete_menu, menu);
        return true;
    }

    final MedicationAdapter adapter = new MedicationAdapter();

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bewerk_pil:
                adapter.setOnItemClickListener(new MedicationAdapter.OnItemClickListener() {
                    @Override
                    // Activity start niet?
                    public boolean onItemClick(Medication medication) {
                        if (onItemClick(medication) == true) {
                            Intent intent = new Intent(SecondActivity.this, AddEditPilActivity.class);
                            intent.putExtra(AddEditPilActivity.EXTRA_UUID, medication.getUuid());
                            Log.d("Test", String.valueOf(medication.getUuid()));
                            intent.putExtra(AddEditPilActivity.EXTRA_NAME, medication.getName());
                            intent.putExtra(AddEditPilActivity.EXTRA_BESCHRIJVING, medication.getBeschrijving());
                            intent.putExtra(AddEditPilActivity.EXTRA_HERHAAL, medication.getHerhaal());
                            startActivity(intent);
                            return true;
                        };
                        return true;
                    };
                });
                return true;
            case R.id.verwijder_pil:
                // Wil een int krijgen maar niet duidelijk welke / hoe.
                //medicationViewModel.delete();
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


