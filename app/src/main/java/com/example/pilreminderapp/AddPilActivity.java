package com.example.pilreminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddPilActivity extends AppCompatActivity {
    public static final String EXTRA_UUID =
            "com.example.pilreminderapp.EXTRA_UUID";
    public static final String EXTRA_NAME =
            "com.example.pilreminderapp.EXTRA_NAME";
    public static final String EXTRA_BESCHRIJVING =
            "com.example.pilreminderapp.EXTRA_BESCHRIJVING";


    private EditText editTextName;
    private EditText editTextBeschrijving;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pil);

        editTextName = findViewById(R.id.edit_text_name);
        editTextBeschrijving = findViewById(R.id.edit_text_beschrijving);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Voeg pil toe");
    }

    private void savePil(){
        String name = editTextName.getText().toString();
        String beschrijving = editTextBeschrijving.getText().toString();

        if(name.trim().isEmpty() || beschrijving.trim().isEmpty()){
            Toast.makeText(this, "Ongeldige invoer", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_BESCHRIJVING, beschrijving);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_pil_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_pil:
                savePil();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}