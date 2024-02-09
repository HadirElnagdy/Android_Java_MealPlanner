package com.example.mealplanner.main.search.view;

import com.example.mealplanner.models.AreaName;
import com.example.mealplanner.models.CategoryName;
import com.example.mealplanner.models.FilteredMeal;
import com.example.mealplanner.models.Ingredient;
import com.example.mealplanner.models.Meal;

import java.util.List;

public interface SearchView {
    public void showMealDetails(String mealId);
    public void showSearchResults(List<Meal> mealList);
    public void showErrorMessage(String error);
    public void showCategoryList(List<CategoryName> categoryNames);
    public void showIngredientList(List<Ingredient> ingredients);
    public void showAreaList(List<AreaName> areaNames);

    public void showLoginAlert();
}
