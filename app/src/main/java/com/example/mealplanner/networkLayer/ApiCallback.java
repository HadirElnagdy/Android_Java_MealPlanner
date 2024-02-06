package com.example.mealplanner.networkLayer;

public interface ApiCallback<T> {
        void onSuccess(T response, String endpoint);
        void onError(int statusCode, String message);
        void onFailure(Throwable throwable);

}
