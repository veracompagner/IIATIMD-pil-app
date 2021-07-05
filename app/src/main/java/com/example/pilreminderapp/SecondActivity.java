package com.example.pilreminderapp;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.widget.ImageView;
=======
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
>>>>>>> Marius
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class SecondActivity extends AppCompatActivity {

    public static final int EDIT_NOTE_REQUEST = 2;
    TextView titleSecond, beschrijvingSecond;
    String data1, data2;
    int uuid;
    private MedicationViewModel medicationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

<<<<<<< HEAD
        imageView2 = findViewById(R.id.imageView2);
        titleSecond = findViewById(R.id.medications_names_second);
        beschrijvingSecond = findViewById(R.id.beschrijving_second);
=======
        medicationViewModel = new ViewModelProvider(this).get(MedicationViewModel.class);

        titleSecond = findViewById(R.id.titleSecond);
        beschrijvingSecond = findViewById(R.id.beschrijvingSecond);
>>>>>>> Marius

        getData();
        setData();
    }

    private void getData() {
        if (getIntent().hasExtra("data1") && getIntent().hasExtra("data2")  && getIntent().hasExtra("uuid")) {

            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");

        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){
        titleSecond.setText(data1);
        beschrijvingSecond.setText(data2);
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


