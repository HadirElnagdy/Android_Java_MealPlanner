package com.example.mealplanner.main.search.presenter;

public interface SearchPresenter {
    public void searchMeals(String mealName);
    public void addToSaved(String mealId);
    public void addToPlan(String mealId, int date);
   public void getCategoryList();
   public void getIngredientList();
   public void getAreaList();
}
