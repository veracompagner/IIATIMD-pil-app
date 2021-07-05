package com.example.pilreminderapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private MedicationViewModel medicationViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medicationViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MedicationViewModel.class);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PiloverzichtFragment(this)).commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.titleTime);
            String description = getString(R.string.contentTime);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("hoi", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

    private PiloverzichtFragment setPiloverzichtFragment(){
        PiloverzichtFragment piloverzichtFragment = new PiloverzichtFragment(this);
        return piloverzichtFragment;
    }


    private FaqFragment setFaqFragment(){
        FaqFragment faqFragment = new FaqFragment(this);
        return faqFragment;
    }

   /* private KalenderFragment setKalenderFragment(){
        KalenderFragment kalenderFragment = new KalenderFragment(this);
        return kalenderFragment;
    }*/






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.overzicht_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_pillen:
                medicationViewModel.deleteAllMedication();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        //case R.id.nav_kalender:
                          //  selectedFragment = setKalenderFragment();
                           // break;
                        case R.id.nav_pil_overzicht:
                            selectedFragment = setPiloverzichtFragment();
                            break;
                        case R.id.nav_FAQ:
                            selectedFragment = setFaqFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;

                }
            };

}