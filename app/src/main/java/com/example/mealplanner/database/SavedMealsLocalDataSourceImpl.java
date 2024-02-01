package com.example.mealplanner.database;

import android.content.Context;

import com.example.mealplanner.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class SavedMealsLocalDataSourceImpl implements SavedMealsLocalDataSource{

    private MealsDAO mealsDAO;
    private Observable<List<Meal>> mealsList;
    private static SavedMealsLocalDataSourceImpl localDataSource = null;

    private SavedMealsLocalDataSourceImpl(Context context){
        mealsDAO = AppDatabase.getInstance(context.getApplicationContext()).getMealDAO();
        mealsList = mealsDAO.getAllMeals();
    }
    public static SavedMealsLocalDataSourceImpl getInstance(Context context){
        if(localDataSource == null){
            localDataSource = new SavedMealsLocalDataSourceImpl(context);
        }
        return localDataSource;
    }

    @Override
    public void insertMeal(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealsDAO.insertMeal(meal);
            }
        }).start();
    }

    @Override
    public void deleteMeal(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealsDAO.deleteMeal(meal);
            }
        }).start();
    }

    @Override
    public Observable<List<Meal>> getAllSavedMeals() {
        return mealsList;
    }
}
