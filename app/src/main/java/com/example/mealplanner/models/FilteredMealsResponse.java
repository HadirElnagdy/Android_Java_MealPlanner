package com.example.mealplanner.models;

import java.util.List;

public class FilteredMealsResponse {
    private List<FilteredMeal> meals;

    public List<FilteredMeal> getMeals() { return meals; }
    public void setMeals(List<FilteredMeal> value) { this.meals = value; }
}
