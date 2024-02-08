package com.example.mealplanner.main.plan.presenter;

import com.example.mealplanner.models.Meal;
import com.google.firebase.auth.FirebaseUser;

public interface PlanPresenter {
    public void getPlanByDate(String planDate);

    public void getAllPlans();

    public void deletePlannedMeal(Meal meal);
    public void addToSaved(Meal meal);

}
