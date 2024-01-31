package com.example.mealplanner.main.home.presenter;

import android.util.Log;

import com.example.mealplanner.main.home.view.HomeView;
import com.example.mealplanner.models.CategoryListResponse;
import com.example.mealplanner.models.CategoryName;
import com.example.mealplanner.models.CategoryRepository;
import com.example.mealplanner.models.FilteredMealsResponse;
import com.example.mealplanner.models.MealsRepository;
import com.example.mealplanner.models.MealsResponse;
import com.example.mealplanner.networkLayer.ApiCallback;
import com.example.mealplanner.networkLayer.Constants;


public class HomePresenterImpl implements HomePresenter, ApiCallback<Object> {

    HomeView view;
    CategoryRepository categoryRepository;
    MealsRepository mealsRepository;
    String TAG = "HomePresenterImpl";

    public HomePresenterImpl(HomeView view, CategoryRepository categoryRepository, MealsRepository mealsRepository) {
        this.view = view;
        this.categoryRepository = categoryRepository;
        this.mealsRepository = mealsRepository;
    }

    public void getCategoryList() {
        categoryRepository.getCategoryList(this);
    }

    @Override
    public void getMealsByCategory(String categoryName) {
        mealsRepository.getMealsByCategory(categoryName, this);
    }

    @Override
    public void getRandomMeal() {
        mealsRepository.getRandomMeal(this);
    }


    @Override
    public void onSuccess(Object response, String endpoint) {
        switch (endpoint){
            case Constants.APIEndpoints.RANDOM_MEAL:
                view.ShowRandomMeal(((MealsResponse)response).getMeals().get(0));
                Log.i(TAG, "RANDOM_MEAL: "+(((MealsResponse)response).getMeals().get(0)));
                break;
            case Constants.APIEndpoints.LIST_ALL:
                view.showMealCategories(((CategoryListResponse)response).getCategoryNames());
                Log.i(TAG, "LIST_ALL: "+((CategoryListResponse)response).getCategoryNames());
                break;
            case Constants.APIEndpoints.FILTER_MEALS:
                view.showMeals(((FilteredMealsResponse)response).getMeals());
                Log.i(TAG, "FILTER_MEALS: " + ((FilteredMealsResponse)response).getMeals());
                break;
        }

    }

    @Override
    public void onError(int statusCode, String message) {
        Log.e(TAG, "onError: \nStatus code: " + String.valueOf(statusCode) + "\nMessage: " + message);
    }

    @Override
    public void onFailure(Throwable throwable) {
        Log.e(TAG, "onError: \nMessage: " + throwable.getMessage());
    }
}
