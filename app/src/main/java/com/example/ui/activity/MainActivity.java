package com.example.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aluramobile.R;
import com.example.model.Medication;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MedicationAdapter.OnMedicationChangedListener {

    private ArrayList<Medication> medicationList;
    private MedicationAdapter adapter;
    private SharedPreferences sharedPreferences; //File where data will be stored
    private ActivityResultLauncher<Intent> addMedicationLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Medication List");

        sharedPreferences = getSharedPreferences("medication_list", MODE_PRIVATE);
        medicationList = loadMedicationList();
        adapter = new MedicationAdapter(this, medicationList);

        setupRecyclerView(); //Guarantees the list is updated after adding more meds
        initializeActivityLauncher();
        setupAddButton(); //FAB logic
    }

    private void initializeActivityLauncher() {
        addMedicationLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getData() != null) {
                        Medication newMedication = (Medication) result.getData().getSerializableExtra("new_medication");
                        if (newMedication != null) {
                            medicationList.add(newMedication);
                            saveMedicationList(medicationList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void setupAddButton() {
        FloatingActionButton fab = findViewById(R.id.floatingActionButton1);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MedicationForm.class);
            addMedicationLauncher.launch(intent);
        });
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.medication_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void saveMedicationList(ArrayList<Medication> medicationListToSave) {
        Gson gson = new Gson();
        String json = gson.toJson(medicationListToSave);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("medication_list", json); // Make sure this key is correct
        editor.apply();
    }

    private ArrayList<Medication> loadMedicationList() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("medication_list", null);

        if (json == null) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<ArrayList<Medication>>() {}.getType();
        return gson.fromJson(json, type);
    }
    @Override
    public void onMedicationDataChanged() {
        saveMedicationList(medicationList);
    }
}