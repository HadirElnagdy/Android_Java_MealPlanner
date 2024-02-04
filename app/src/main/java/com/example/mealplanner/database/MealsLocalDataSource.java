package com.example.mealplanner.database;

import com.example.mealplanner.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MealsLocalDataSource {
    void insertSavedMeal(Meal meal);
    void deleteSavedMeal(Meal meal);
    Observable<List<Meal>> getAllSavedMeals();
}
