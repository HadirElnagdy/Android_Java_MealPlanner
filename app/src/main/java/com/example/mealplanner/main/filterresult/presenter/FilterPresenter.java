package com.example.mealplanner.main.filterresult.presenter;

public interface FilterPresenter {

    public void addToSaved(String mealId);
    public void addToPlan(String mealId, int date);
    public void filterMealsByCategory(String categoryName);
    public void filterMealsByIngredients(String ingredient);
    public void filterMealsByArea(String areaName);
}
