package com.example.mealplanner.main.filterresult.presenter;

import com.example.mealplanner.main.filterresult.view.FilterView;
import com.example.mealplanner.models.FilteredMealsResponse;
import com.example.mealplanner.models.MealsRepository;
import com.example.mealplanner.networkLayer.ApiCallback;

public class FilterPresenterImpl implements FilterPresenter, ApiCallback<Object> {

    MealsRepository mealsRepository;
    FilterView view;


    public FilterPresenterImpl(MealsRepository mealsRepository, FilterView view) {
        this.mealsRepository = mealsRepository;
        this.view = view;
    }
    @Override
    public void filterMealsByCategory(String categoryName) {
        mealsRepository.getMealsByCategory(categoryName, this);
    }

    @Override
    public void filterMealsByIngredients(String ingredient) {
        mealsRepository.getMealsByIngredient(ingredient, this);
    }

    @Override
    public void filterMealsByArea(String areaName) {
        mealsRepository.getMealsByArea(areaName, this);
    }

    @Override
    public void onSuccess(Object response, String endpoint) {
        view.showFilteredList(((FilteredMealsResponse)response).getMeals());
    }

    @Override
    public void onError(int statusCode, String message) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
