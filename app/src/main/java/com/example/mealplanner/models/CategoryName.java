package com.example.mealplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CategoryName implements Parcelable {
    private String strCategory;

    protected CategoryName(Parcel in) {
        strCategory = in.readString();
    }

    public static final Creator<CategoryName> CREATOR = new Creator<CategoryName>() {
        @Override
        public CategoryName createFromParcel(Parcel in) {
            return new CategoryName(in);
        }

        @Override
        public CategoryName[] newArray(int size) {
            return new CategoryName[size];
        }
    };

    public String getStrCategory() { return strCategory; }
    public void setStrCategory(String value) { this.strCategory = value; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(strCategory);
    }
}

