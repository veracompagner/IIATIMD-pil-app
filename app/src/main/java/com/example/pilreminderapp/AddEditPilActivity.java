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

public class AddEditPilActivity extends AppCompatActivity {
    public static final String EXTRA_UUID =
            "com.example.pilreminderapp.EXTRA_UUID";
    public static final String EXTRA_NAME =
            "com.example.pilreminderapp.EXTRA_NAME";
    public static final String EXTRA_BESCHRIJVING =
            "com.example.pilreminderapp.EXTRA_BESCHRIJVING";
    public static final String EXTRA_INNAMEN =
            "com.example.pilreminderapp.EXTRA_INNAMEN";


    private EditText editTextName;
    private EditText editTextBeschrijving;
    private EditText editTextInnamen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pil);

        editTextName = findViewById(R.id.edit_text_name);
        editTextBeschrijving = findViewById(R.id.edit_text_beschrijving);
        editTextInnamen = findViewById(R.id.edit_text_innamen);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_UUID)){
            setTitle("Bewerken");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextBeschrijving.setText(intent.getStringExtra(EXTRA_BESCHRIJVING));
            editTextInnamen.setText(intent.getStringExtra(EXTRA_INNAMEN));
        }
        else {
            setTitle("Voeg pil toe");
        }
    }

    private void savePil(){
        String name = editTextName.getText().toString();
        String beschrijving = editTextBeschrijving.getText().toString();
        String innamen = editTextInnamen.getText().toString();

        if(name.trim().isEmpty() || beschrijving.trim().isEmpty()){
            Toast.makeText(this, "Ongeldige invoer", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_BESCHRIJVING, beschrijving);
        data.putExtra(EXTRA_INNAMEN, innamen);

        int uuid = getIntent().getIntExtra(EXTRA_UUID, -1);
        if(uuid != -1){
            data.putExtra(EXTRA_UUID, uuid);
        }

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