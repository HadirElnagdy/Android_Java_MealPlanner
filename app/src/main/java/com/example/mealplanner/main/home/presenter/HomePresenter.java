package com.example.mealplanner.main.home.presenter;

import com.example.mealplanner.models.Meal;

public interface HomePresenter {
    public void getCategoryList();
    public void getMealsByCategory(String categoryName);
    public void getRandomMeal();
    public void addMealToSaved(Meal meal);
    public void deleteMealFromSaved(Meal meal);
    public void addMealToPlan(Meal meal);
    public void deleteMealFromPlan(Meal meal);

}
