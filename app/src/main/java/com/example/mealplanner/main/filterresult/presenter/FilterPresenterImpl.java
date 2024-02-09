package com.example.mealplanner.main.filterresult.presenter;

import com.example.mealplanner.main.filterresult.view.FilterView;
import com.example.mealplanner.models.CategoryListResponse;
import com.example.mealplanner.models.FilteredMealsResponse;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.MealsRepository;
import com.example.mealplanner.models.MealsResponse;
import com.example.mealplanner.models.UserManager;
import com.example.mealplanner.networkLayer.ApiCallback;
import com.example.mealplanner.networkLayer.Constants;

public class FilterPresenterImpl implements FilterPresenter, ApiCallback<Object> {

    MealsRepository mealsRepository;
    FilterView view;
    String date;
    String savedId;

    UserManager manager = new UserManager();


    public FilterPresenterImpl(MealsRepository mealsRepository, FilterView view) {
        this.mealsRepository = mealsRepository;
        this.view = view;
    }

    @Override
    public void addToSaved(String mealId) {
        if(manager.isLoggedIn()) {
            savedId = mealId;
            mealsRepository.getMealById(mealId, this);
        }else {
            view.showLoginAlert();
        }
    }

    @Override
    public void addToPlan(String mealId, int date) {
        if(manager.isLoggedIn()) {
            this.date = (date < 10?"0" + String.valueOf(date) : String.valueOf(date));
            mealsRepository.getMealById(mealId, this);
        }else {
            view.showLoginAlert();
        }
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
        switch (endpoint) {
            case Constants.APIEndpoints.FILTER_MEALS:
                view.showFilteredList(((FilteredMealsResponse)response).getMeals());
                break;
            case Constants.APIEndpoints.LOOKUP_MEAL:
                Meal meal = ((MealsResponse) response).getMeals().get(0);
                if (savedId != null && savedId.equals(meal.getIdMeal())) {
                    mealsRepository.addMealToSaved(meal);
                    savedId = null;
                } else {
                    meal.setPlanDate(date);
                    mealsRepository.addMealToPlan(meal);
                }
                break;
        }
    }

    @Override
    public void onError(int statusCode, String message) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
