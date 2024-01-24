package com.example.mealplanner.networkLayer;

import java.util.Map;

public interface RemoteDataSource {
    public <T> void makeGetRequest(String endpoint, Map<String, String> queryParams, ApiCallback<Object> callback, Class<T> responseType);
}
