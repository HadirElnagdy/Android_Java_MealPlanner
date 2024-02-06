package com.example.mealplanner.models;

import java.util.List;

public class CategoriesResponse {
    private List<Category> categories;

    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> value) { this.categories = value; }
}
