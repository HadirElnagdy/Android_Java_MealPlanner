package com.example.mealplanner.main.saved.presenter;


import com.example.mealplanner.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface SavedPresenter {
    public Observable<List<Meal>> getSavedMeals();
    public void deleteSavedMeal(Meal meal);

    public void addMealToPlan(Meal meal, int day);
}
