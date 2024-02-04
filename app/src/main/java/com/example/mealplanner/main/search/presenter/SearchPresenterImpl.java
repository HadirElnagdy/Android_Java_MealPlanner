package com.example.mealplanner.main.search.presenter;

import android.util.Log;

import com.example.mealplanner.main.search.view.SearchView;
import com.example.mealplanner.models.FilteredMealsResponse;
import com.example.mealplanner.models.MealsRepository;
import com.example.mealplanner.models.MealsResponse;
import com.example.mealplanner.networkLayer.ApiCallback;
import com.example.mealplanner.networkLayer.Constants;

public class SearchPresenterImpl implements SearchPresenter, ApiCallback<Object> {
    MealsRepository repo;
    SearchView view;
    String TAG = "SearchPresenterImpl";

    public SearchPresenterImpl(MealsRepository repo, SearchView view) {
        this.repo = repo;
        this.view = view;
    }

    @Override
    public void searchMeals(String mealName) {
        repo.getMealsByName(mealName, this);
    }

    @Override
    public void filterMealsByCategory(String categoryName) {

    }

    @Override
    public void filterMealsByIngredients(String... ingredients) {

    }

    @Override
    public void filterMealsByArea(String areaName) {

    }

    @Override
    public void addToSaved(String mealId) {

    }

    @Override
    public void delFromSaved(String mealId) {

    }

    @Override
    public void addToPlan(String mealId) {

    }

    @Override
    public void delFromPlan(String mealId) {

    }

    @Override
    public void onSuccess(Object response, String endpoint) {
        if (endpoint.equals(Constants.APIEndpoints.SEARCH_MEAL)) {
            view.showSearchResults(((MealsResponse) response).getMeals());
        }else if(endpoint.equals(Constants.APIEndpoints.LIST_ALL)){

        }

    }

    @Override
    public void onError(int statusCode, String message) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
