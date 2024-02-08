package com.example.mealplanner.main.home.presenter;


import com.example.mealplanner.models.Meal;

import io.reactivex.rxjava3.core.Observable;

public interface HomePresenter {
    public void getCategoryList();
    public void getMealsByCategory(String categoryName);
    public void getRandomMeal();
    public void toggleSavedStatus(String mealId, Meal meal);
    public void addMealToPlan(String mealId, Meal meal, int date);
    public Observable<Boolean> isSaved(String mealId);


}
