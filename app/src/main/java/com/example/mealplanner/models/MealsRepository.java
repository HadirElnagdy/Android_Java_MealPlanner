package com.example.mealplanner.models;

import com.example.mealplanner.networkLayer.ApiCallback;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MealsRepository {

    //Remote
    public void getRandomMeal(ApiCallback apiCallback);
    public void getMealsByCategory(String categoryName, ApiCallback apiCallback);
    public void getMealsByIngredient(String ingredientName, ApiCallback apiCallback);
    public void getMealsByArea(String areaName, ApiCallback apiCallback);
    public void getMealsByName(String name, ApiCallback apiCallback);
    public void getMealById(String mealId, ApiCallback apiCallback);

    //Local
    public void addMealToSaved(Meal meal);
    public Observable<List<Meal>> getSavedMeals();

    public void deleteSavedMeal(Meal meal);
    public Observable<Boolean> isSaved(String mealId);

}
