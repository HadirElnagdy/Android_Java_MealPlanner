package com.example.mealplanner.main.search.view;

import com.example.mealplanner.models.Meal;

import java.util.List;

public interface SearchView {
    public void showMealDetails(Meal meal);
    public void showSearchResults(List<Meal> mealList);
    public void showErrorMessage(String error);
    public void setChipGroupCategory(List<String> categories);
    public void setChipGroupIngredient(List<String> ingredients);
    public void setChipGroupArea(List<String> areas);


}
