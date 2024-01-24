package com.example.mealplanner.networkLayer;

public interface ApiCallback<T> {
        void onSuccess(T response);
        void onError(int statusCode, String message);
        void onFailure(Throwable throwable);

}
