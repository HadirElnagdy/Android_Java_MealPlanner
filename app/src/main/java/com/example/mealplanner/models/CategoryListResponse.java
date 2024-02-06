package com.example.mealplanner.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryListResponse {
    @SerializedName("meals")
    private List<CategoryName> categoryNames;

    public List<CategoryName> getCategoryNames() { return categoryNames; }
    public void setCategoryNames(List<CategoryName> value) { this.categoryNames = value; }
}

