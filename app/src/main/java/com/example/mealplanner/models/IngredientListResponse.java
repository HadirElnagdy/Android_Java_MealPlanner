package com.example.mealplanner.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientListResponse {
    @SerializedName("meals")
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> value) { this.ingredients = value; }
}

