package com.example.mealplanner.models;

import com.example.mealplanner.networkLayer.ApiCallback;

import java.util.Map;

public interface CategoryRepository {
    public void getCategoryList(ApiCallback<Object> callback);
}
