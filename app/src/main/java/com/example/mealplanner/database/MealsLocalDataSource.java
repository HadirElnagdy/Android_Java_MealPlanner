package com.example.mealplanner.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.mealplanner.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MealsLocalDataSource {
    void insertSavedMeal(Meal meal);
    void deleteSavedMeal(Meal meal);
    Observable<List<Meal>> getAllSavedMeals();
    Observable<Boolean> isSaved(String idMeal);

    Observable<List<Meal>> getAllPlannedMeals();
    void insertPlannedMeal(Meal meal);

    void deletePlannedMeal(Meal meal);

}
