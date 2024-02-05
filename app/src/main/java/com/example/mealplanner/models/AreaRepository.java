package com.example.mealplanner.models;

import com.example.mealplanner.networkLayer.ApiCallback;

public interface AreaRepository {
    public void getAreaList(ApiCallback<Object> callback);
}
