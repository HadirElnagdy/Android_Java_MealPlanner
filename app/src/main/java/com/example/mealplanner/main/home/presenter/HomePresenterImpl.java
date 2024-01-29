package com.example.mealplanner.main.home.presenter;

import com.example.mealplanner.networkLayer.ApiCallback;

public class HomePresenterImpl implements HomePresenter, ApiCallback<Object> {

    @Override
    public void onSuccess(Object response, String endpoint) {
        
    }

    @Override
    public void onError(int statusCode, String message) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
