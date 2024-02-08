package com.example.mealplanner.main.saved.presenter;


import android.content.Context;

import com.example.mealplanner.database.MealsLocalDataSourceImpl;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.MealsRepository;
import com.example.mealplanner.models.UserManager;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class SavedPresenterImpl implements SavedPresenter{

    MealsRepository repo;

    UserManager manager = new UserManager();

    public SavedPresenterImpl(MealsRepository repo) {
        this.repo = repo;
    }

    public void updateUserEmail(Context context){
        MealsLocalDataSourceImpl.getInstance(context).updateUserEmail(manager.getCurrentUserEmail());
        repo.setLocalDataSource(MealsLocalDataSourceImpl.getInstance(context));
    }
    @Override
    public Observable<List<Meal>> getSavedMeals() {
        return repo.getSavedMeals();
    }

    @Override
    public void deleteSavedMeal(Meal meal) {
        repo.deleteSavedMeal(meal);
    }

    @Override
    public void addMealToPlan(Meal meal, int day) {
        String date;
        if (day < 10)
            date = "0" + String.valueOf(day);
        else
            date = String.valueOf(day);

        meal.setPlanDate(date);
        repo.addMealToPlan(meal);
    }


}
