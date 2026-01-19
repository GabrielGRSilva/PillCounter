package com.example.ui.activity;

import android.content.Context;
import android.content.Intent;import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {
    private ArrayList<Medication> medicationList = loadMedicationList();
    private MedicationAdapter adapter = new MedicationAdapter(this, medicationList);
    private static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("student_list_key", Context.MODE_PRIVATE);

        ActivityResultLauncher<Intent> addMedicationLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getData() != null) {
                        // The form sent back a new medication!
                        Medication newMedication = (Medication) result.getData().getSerializableExtra("new_medication");
                        if (newMedication != null) {
                            // Add it to our main list and save
                            medicationList.add(newMedication);
                            saveMedicationList(medicationList); // You'll need this method here now
                            adapter.notifyDataSetChanged(); // Tell the adapter to refresh the view
                        }
                    }
                });

        setTitle("Medication List");
        setContentView(R.layout.activity_main);

        // This FAB is for adding a NEW medication
        FloatingActionButton fab = findViewById(R.id.floatingActionButton1);
        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MedicationForm.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.medication_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static void saveMedicationList(ArrayList<Medication> medicationList) {
        // Now it uses the context that was passed in to get SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(medicationList); // Convert the whole list to JSON

        editor.putString("student_list_key", json);
        editor.apply();
    }
    private ArrayList<Medication> loadMedicationList() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("student_list_key", null);

        if (json == null) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<ArrayList<Medication>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
