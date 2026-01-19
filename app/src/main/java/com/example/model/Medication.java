package com.example.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class Medication implements Parcelable {
    private final String name;
    private int quantity;
    private final String notes;

    public Medication(String name, int quantity, String notes) {
        this.name = name;
        this.quantity = quantity;
        this.notes = notes;
    }

    protected Medication(Parcel in) {
        name = in.readString();
        quantity = in.readInt();
        notes = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(quantity);
        dest.writeString(notes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Medication> CREATOR = new Creator<Medication>() {
        @Override
        public Medication createFromParcel(Parcel in) {
            return new Medication(in);
        }

        @Override
        public Medication[] newArray(int size) {
            return new Medication[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void addOne(int quantity) {
        this.quantity += quantity;
    }
    public void removeOne(int quantity) {
        this.quantity -= quantity;
    }
}
