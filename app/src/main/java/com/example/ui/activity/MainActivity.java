package com.example.ui.activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aluramobile.R;
import com.example.model.Medication;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Student List");
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("StudentListApp", MODE_PRIVATE);
        MedicationForm.loadMedicationList(sharedPreferences);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton1);
        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MedicationForm.class)));
    }

    @Override
    protected void onResume(){ //Ao reabrir a Activity, e não apenas onCreate
        super.onResume();
        ListView medicineList = findViewById(R.id.main_activity_lv1); //ListView de alunos
        SharedPreferences sharedPreferences = getSharedPreferences("StudentListApp", MODE_PRIVATE);
        medicineList.setAdapter(new ArrayAdapter<>( //Adapter que conecta a ListView com a ArrayList, usando um layout padrão simple_list_item_1
                this,
                android.R.layout.simple_list_item_1,
                MedicationForm.loadMedicationList(sharedPreferences)));

        //Ao clicar em medication na lista, abre uma activity com seus dados
        medicineList.setOnItemClickListener((parent, view, position, id) -> {
            Medication clickedMedication = (Medication) parent.getItemAtPosition(position);

            Intent intent = new Intent(MainActivity.this, MedicationOverviewActivity.class);
             intent.putExtra("student", clickedMedication);
             startActivity(intent);
        });

        FloatingActionButton fabAdd = findViewById(R.id.floatingActionButton1);
        fabAdd.setOnClickListener(v -> MedicationForm.addMed);

        FloatingActionButton fabRem = findViewById(R.id.floatingActionButton1);
        fabRem.setOnClickListener(v -> MedicationForm.remMed);
    }
}
