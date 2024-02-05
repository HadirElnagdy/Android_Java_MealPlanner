package com.example.mealplanner.models;

import com.example.mealplanner.networkLayer.ApiCallback;

public interface MealsRepository {
    public void getRandomMeal(ApiCallback apiCallback);
    public void getMealsByCategory(String categoryName, ApiCallback apiCallback);
    public void getMealsByIngredient(String ingredientName, ApiCallback apiCallback);
    public void getMealsByArea(String areaName, ApiCallback apiCallback);
    public void getMealsByName(String name, ApiCallback apiCallback);
    public void addMealToSaved(Meal meal);
    public void getMealById(String mealId, ApiCallback apiCallback);
}
