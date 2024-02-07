package com.example.mealplanner.main.plan.view;

import com.example.mealplanner.models.Meal;

public interface PlanClickListener {
    void onOpenMealClicked(String mealId, Meal meal);

    void onRemovePlanClicked(String mealId, Meal meal);
    void onSaveClicked(String mealId, Meal meal);

}
