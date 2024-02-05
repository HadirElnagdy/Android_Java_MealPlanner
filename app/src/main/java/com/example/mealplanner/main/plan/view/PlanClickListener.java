package com.example.mealplanner.main.plan.view;

public interface PlanClickListener {
    void onOpenMealClicked(String mealId);

    void onRemovePlanClicked(String mealId);
}
