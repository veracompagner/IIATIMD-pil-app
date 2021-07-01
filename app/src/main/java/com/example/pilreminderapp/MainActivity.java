package com.example.pilreminderapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PiloverzichtFragment(this)).commit();

    }

    private PiloverzichtFragment setPiloverzichtFragment(){
        PiloverzichtFragment piloverzichtFragment = new PiloverzichtFragment(this);
        return piloverzichtFragment;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_kalender:
                            selectedFragment = new KalenderFragment();
                            break;
                        case R.id.nav_pil_overzicht:
                            selectedFragment = setPiloverzichtFragment();
                            break;
                        case R.id.nav_FAQ:
                            selectedFragment = new FaqFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;

                }
            };

}