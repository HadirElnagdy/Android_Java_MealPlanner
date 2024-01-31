package com.example.mealplanner.models;

import com.example.mealplanner.networkLayer.ApiCallback;

public interface MealsRepository {
    public void getRandomMeal(ApiCallback apiCallback);
    public void getMealsByCategory(String CategoryName, ApiCallback apiCallback);
}
