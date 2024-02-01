package com.example.mealplanner.database;

import com.example.mealplanner.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface SavedMealsLocalDataSource {
    void insertMeal(Meal meal);
    void deleteMeal(Meal meal);
    Observable<List<Meal>> getAllSavedMeals();
}
