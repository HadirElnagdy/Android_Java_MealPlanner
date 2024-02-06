package com.example.mealplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FilteredMeal implements Parcelable {
    private String strMeal;
    private String strMealThumb;
    private String idMeal;

    protected FilteredMeal(Parcel in) {
        strMeal = in.readString();
        strMealThumb = in.readString();
        idMeal = in.readString();
    }

    public static final Creator<FilteredMeal> CREATOR = new Creator<FilteredMeal>() {
        @Override
        public FilteredMeal createFromParcel(Parcel in) {
            return new FilteredMeal(in);
        }

        @Override
        public FilteredMeal[] newArray(int size) {
            return new FilteredMeal[size];
        }
    };

    public String getStrMeal() { return strMeal; }
    public void setStrMeal(String value) { this.strMeal = value; }

    public String getStrMealThumb() { return strMealThumb; }
    public void setStrMealThumb(String value) { this.strMealThumb = value; }

    public String getIDMeal() { return idMeal; }
    public void setIDMeal(String value) { this.idMeal = value; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(strMeal);
        parcel.writeString(strMealThumb);
        parcel.writeString(idMeal);
    }
}
