package com.example.mealplanner.main.plan.view;

import com.example.mealplanner.models.Meal;

public interface PlanView {
    void updateAdapterWithMeal(String date, Meal meal);

    void resetAdapterList(String date);
    void showLoginDialog();
}
