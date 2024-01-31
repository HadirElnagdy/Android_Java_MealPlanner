package com.example.mealplanner.main.home.presenter;

public interface HomePresenter {
    public void getCategoryList();
    public void getMealsByCategory(String categoryName);
    public void getRandomMeal();
}
