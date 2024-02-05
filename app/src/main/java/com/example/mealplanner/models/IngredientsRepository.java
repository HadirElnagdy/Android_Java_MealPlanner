package com.example.mealplanner.models;

import com.example.mealplanner.networkLayer.ApiCallback;

public interface IngredientsRepository {
    public void getIngredientList(ApiCallback<Object> callback);
}
