package com.example.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aluramobile.R;
import com.example.model.Medication;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder> {

    private final List<Medication> medicationList;
    private final Context context;
    public MedicationAdapter(Context context, List<Medication> medicationList) {
        this.context = context;
        this.medicationList = medicationList;
    }

    // This method creates the view for each list item from your XML layout
    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_main_custom, parent, false);
        return new MedicationViewHolder(view);
    }

    // This method binds the data to the views for each item
    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position) {
        // Get the specific medication for this row
        Medication medication = medicationList.get(position);

        // Set the medication name and quantity on the TextViews
        holder.nameTextView.setText(medication.getName());
        holder.quantityTextView.setText(String.valueOf(medication.getQuantity())); // Make sure to use String.valueOf()

        // Sets the click listener for the green "add" button
        holder.fabAdd.setOnClickListener(v -> {
            medication.addOne(1); // Add 1 to the quantity in the Medication object
            holder.quantityTextView.setText(String.valueOf(medication.getQuantity())); // Update the quantity TextView immediately
            saveMedications(); // Save the updated list to SharedPreferences
        });

        // Sets the click listener for the red "remove" button
        holder.fabRemove.setOnClickListener(v -> {
            if (medication.getQuantity() > 0) { // Prevent quantity from going below zero
                medication.removeOne(1); // Remove 1 from the quantity
                holder.quantityTextView.setText(String.valueOf(medication.getQuantity())); // Update the TextView
                saveMedications(); // Save the updated list
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    // This inner class holds the views for a single list item
    static class MedicationViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView quantityTextView;
        FloatingActionButton fabAdd;
        FloatingActionButton fabRemove;

        public MedicationViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find all the views from your list item XML file
            nameTextView = itemView.findViewById(R.id.activity_medication_form_name); // Use the correct ID from your XML
            quantityTextView = itemView.findViewById(R.id.activity_medication_form_quantity); // Use the correct ID from your XML
            fabAdd = itemView.findViewById(R.id.FABAdd1Med); // Use the correct ID for the add button
            fabRemove = itemView.findViewById(R.id.FABRem1Med); // Use the correct ID for the remove button
        }
    }

    // Helper method to save the entire list back to SharedPreferences
    private void saveMedications() {
        MainActivity.saveMedicationList(context, (ArrayList<Medication>) medicationList);
    }
}
