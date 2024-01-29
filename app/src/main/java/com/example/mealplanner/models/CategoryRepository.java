package com.example.mealplanner.models;

import com.example.mealplanner.networkLayer.ApiCallback;

import java.util.Map;

public interface CategoryRepository {
    public<T> void getCategoryList(String endpoint, Map<String, String> queryParams, ApiCallback<Object> callback, Class<T> responseType);
}
