package com.example.mealplanner.main.home.view;

import com.example.mealplanner.models.CategoryName;
import com.example.mealplanner.models.FilteredMeal;
import com.example.mealplanner.models.Meal;

import java.util.List;

public interface HomeView {

   public void showMealCategories(List<CategoryName> categories);
   public void addToMealsList(List<FilteredMeal> meals);
   public void showRandomMeal(Meal meal);
   public void showLoginAlert();

}
