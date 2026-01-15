package com.example.ui.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.aluramobile.R;
import com.example.model.Medication;

public class MedicationOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medication_overview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.medication_overview), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Medication medication = getIntent().getParcelableExtra("student");;// Recupera o objeto passado à activity

        if (medication != null) {
            setTextViews(medication); // Configura os TextViews com os dados do estudante escolhido
        } else {
            Toast.makeText(this, "ERROR: Student == null", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setTextViews(Medication medication) {
        setTitle(medication.getName());

        TextView nameTextView = findViewById(R.id.overview_name);
        TextView phoneTextView = findViewById(R.id.overview_quantity);
        TextView emailTextView = findViewById(R.id.overview_notes);

        nameTextView.setText(medication.getName());
        phoneTextView.setText(medication.getQuantity());
        emailTextView.setText(medication.getNotes());
    }
}