package com.example.mealplanner.main.view;

import com.example.mealplanner.models.Meal;

public interface MealInteractionListener {
    void onSaveClicked(String mealId, Meal meal);
    void onAddToPlanClicked(String mealId, Meal meal);
    void onOpenMealClicked(String mealId, Meal meal);
}
