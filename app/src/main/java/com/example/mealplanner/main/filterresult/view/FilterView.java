package com.example.mealplanner.main.filterresult.view;

import com.example.mealplanner.models.FilteredMeal;

import java.util.List;

public interface FilterView {
    public void showFilteredList(List<FilteredMeal> mealList);
    public void showLoginAlert();
}
