package com.example.mealplanner.main.meal.presenter;

import com.example.mealplanner.models.Meal;

import io.reactivex.rxjava3.core.Observable;

public interface MealPresenter {

    public void addMealToSaved(Meal meal);
    public void deleteMealFromSaved(Meal meal);
    public void addMealToPlan(Meal meal);
    public Observable<Boolean> isSaved(Meal meal);

    public void getMealById(String mealId);
}
