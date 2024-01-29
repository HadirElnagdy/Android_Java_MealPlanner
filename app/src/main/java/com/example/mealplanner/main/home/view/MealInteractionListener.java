package com.example.mealplanner.main.home.view;

public interface MealInteractionListener {
    void onAddToFavoritesClick(int position);
    void onAddToPlanClick(int position);
    void onOpenMealClick(int position);
}
