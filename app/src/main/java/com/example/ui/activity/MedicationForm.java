package com.example.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.aluramobile.R;
import com.example.model.Medication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MedicationForm extends AppCompatActivity {
    private EditText capturedName;
    private EditText capturedQuantity;
    private EditText capturedNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add New Medication");
        setContentView(R.layout.activity_medication_form);
        layoutConfig();
        fieldInitialization();
        Button saveButton = findViewById(R.id.activity_medication_form_save_button);
        saveButton.setOnClickListener(v -> addMedication());
    }

    private void layoutConfig() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void addMedication() {
        String name = capturedName.getText().toString();
        int quantity = Integer.parseInt(capturedQuantity.getText().toString());
        String notes = capturedNotes.getText().toString();
        Medication newMedication = new Medication(name, quantity, notes);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("new_medication", newMedication);

        setResult(AppCompatActivity.RESULT_OK, resultIntent);
        finish();
    }

    private void fieldInitialization() {
        capturedName = findViewById(R.id.activity_medication_form_name);
        capturedQuantity = findViewById(R.id.activity_medication_form_quantity);
        capturedNotes = findViewById(R.id.activity_medication_form_notes);
    }
}