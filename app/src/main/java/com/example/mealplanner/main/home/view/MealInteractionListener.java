package com.example.mealplanner.main.home.view;

public interface MealInteractionListener {
    void onAddToFavoritesClick(String mealId);
    void onAddToPlanClick(String mealId);
    void onOpenMealClick(String mealId);
}
