package com.example.mealplanner.main.search.presenter;

public interface SearchPresenter {
    public void searchMeals(String mealName);
    public void filterMealsByCategory(String categoryName);
    public void filterMealsByIngredients(String...ingredients);
    public void filterMealsByArea(String areaName);
    public void addToSaved(String mealId);
    public void delFromSaved(String mealId);
    public void addToPlan(String mealId);
    public void delFromPlan(String mealId);
}
