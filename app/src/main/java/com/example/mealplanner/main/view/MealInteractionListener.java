package com.example.mealplanner.main.view;

public interface MealInteractionListener {
    void onAddToSaved(String mealId);
    void onAddToPlanClick(String mealId);
    void onOpenMealClick(String mealId);
    void onDelFromSaved(String mealId);
}
