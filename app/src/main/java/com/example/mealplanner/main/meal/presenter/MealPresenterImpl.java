package com.example.mealplanner.main.meal.presenter;



import com.example.mealplanner.main.meal.view.MealView;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.MealsRepository;
import com.example.mealplanner.models.MealsResponse;
import com.example.mealplanner.models.UserManager;
import com.example.mealplanner.networkLayer.ApiCallback;

import io.reactivex.rxjava3.core.Observable;

public class MealPresenterImpl implements MealPresenter, ApiCallback<Object> {
    MealsRepository repo;
    MealView view;
    UserManager manager = new UserManager();

    public MealPresenterImpl(MealsRepository repo, MealView view) {
        this.repo = repo;
        this.view = view;
    }

    @Override
    public void addMealToSaved(Meal meal) {
        if(manager.isLoggedIn()) {
            repo.addMealToSaved(meal);
        }else {
            view.showLoginAlert();
        }
    }



    @Override
    public void addMealToPlan(Meal meal, int date) {
        if(manager.isLoggedIn()) {
            String dateString;
            dateString = (date < 10?"0" + String.valueOf(date) : String.valueOf(date));
            meal.setPlanDate(dateString);
            repo.addMealToPlan(meal);
        }else {
            view.showLoginAlert();
        }
    }



    @Override
    public void getMealById(String mealId) {
        repo.getMealById(mealId, this);
    }

    @Override
    public void onSuccess(Object response, String endpoint) {
       // Log.i("MealPresenterImpl", "onSuccess: " + ((Meal)response).getStrMeal());
        view.setMeal(((MealsResponse)response).getMeals().get(0));
    }

    @Override
    public void onError(int statusCode, String message) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
