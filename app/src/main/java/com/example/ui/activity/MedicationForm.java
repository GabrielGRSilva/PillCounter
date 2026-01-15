package com.example.ui.activity;

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
    private static final ArrayList<Medication> MEDICATION_LIST = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add New Medication");
        setContentView(R.layout.activity_student_form);
        layoutConfig();
        fieldInitialization();
        Button saveButton = findViewById(R.id.activity_student_form_save_button);
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
        String quantity = capturedQuantity.getText().toString();
        String notes = capturedNotes.getText().toString();
        Medication newMedication = new Medication(name, quantity, notes);
        saveMedicationList(newMedication);
        finish(); //Após salvar, finalizar esta activity (voltando na lista em MainActivity)
    }

    private void fieldInitialization() {
        capturedName = findViewById(R.id.activity_student_form_name);
        capturedQuantity = findViewById(R.id.activity_student_form_phone);
        capturedNotes = findViewById(R.id.activity_student_form_email);
    }
        //METHOD BELOW NEEDS TO BE CHANGED IN FAVOR OF DATABASE
    private void saveMedicationList(Medication newMedication) { //Saves list between app sessions
        SharedPreferences sharedPreferences = getSharedPreferences("StudentListApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        MEDICATION_LIST.add(newMedication);
        String json = gson.toJson(MEDICATION_LIST);

        editor.putString("student_list_key", json);
        editor.apply();
    }

    public static ArrayList<Medication> loadMedicationList(SharedPreferences sharedPreferences) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("student_list_key", null);

        if (json == null) {
            return MEDICATION_LIST;
        }

        Type type = new TypeToken<ArrayList<Medication>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}