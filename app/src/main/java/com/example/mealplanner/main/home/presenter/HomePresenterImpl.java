package com.example.mealplanner.main.home.presenter;

import android.util.Log;

import com.example.mealplanner.main.home.view.HomeView;
import com.example.mealplanner.models.CategoryListResponse;
import com.example.mealplanner.models.CategoryRepository;
import com.example.mealplanner.models.FilteredMealsResponse;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.MealsRepository;
import com.example.mealplanner.models.MealsResponse;
import com.example.mealplanner.networkLayer.ApiCallback;
import com.example.mealplanner.networkLayer.Constants;

import io.reactivex.rxjava3.core.Observable;


public class HomePresenterImpl implements HomePresenter, ApiCallback<Object> {

    HomeView view;
    CategoryRepository categoryRepository;
    MealsRepository mealsRepository;
    String savedId;
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
    public void toggleSavedStatus(String mealId, Meal meal) {
        if(meal != null) mealsRepository.addMealToSaved(meal);
        else {
            savedId = mealId;
            mealsRepository.getMealById(mealId, this);
        }
    }



    @Override
    public void addMealToPlan(String mealId, Meal meal, int day) {

    }

    @Override
    public Observable<Boolean> isSaved(String mealId) {
        return mealsRepository.isSaved(mealId);
    }


    @Override
    public void onSuccess(Object response, String endpoint) {
        switch (endpoint){
            case Constants.APIEndpoints.RANDOM_MEAL:
                view.showRandomMeal(((MealsResponse)response).getMeals().get(0));
                break;
            case Constants.APIEndpoints.LIST_ALL:
                view.showMealCategories(((CategoryListResponse)response).getCategoryNames());
                break;
            case Constants.APIEndpoints.FILTER_MEALS:
                view.addToMealsList(((FilteredMealsResponse)response).getMeals());
                break;
            case Constants.APIEndpoints.LOOKUP_MEAL:
                Meal meal = ((MealsResponse)response).getMeals().get(0);
                if(savedId != null && savedId.equals(meal.getIdMeal())){
                    mealsRepository.addMealToSaved(meal);
                    savedId = null;
                }else{
                    mealsRepository.addMealToPlan(meal);
                }
                break;
        }

    }

    @Override
    public void onError(int statusCode, String message) {
        Log.e(TAG, "onError: \nStatus code: " + String.valueOf(statusCode) + "\nMessage: " + message);
    }

    @Override
    public void onFailure(Throwable throwable) {
        Log.e(TAG, "onFailure: \nMessage: " + throwable.getMessage());
    }
}
