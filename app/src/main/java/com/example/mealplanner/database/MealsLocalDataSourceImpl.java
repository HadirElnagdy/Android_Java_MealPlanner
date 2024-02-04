package com.example.mealplanner.database;

import android.content.Context;

import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.UserManager;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {

    private MealsDAO mealsDAO;
    private Observable<List<Meal>> mealsList;
    private static MealsLocalDataSourceImpl localDataSource = null;
    private UserManager manager;

    private MealsLocalDataSourceImpl(Context context){
        manager = new UserManager();
        mealsDAO = AppDatabase.getInstance(context.getApplicationContext()).getMealDAO();
        if(manager.getUser() != null)
            mealsList = mealsDAO.getAllSavedMeals(manager.getCurrentUserEmail());
    }
    public static MealsLocalDataSourceImpl getInstance(Context context){
        if(localDataSource == null){
            localDataSource = new MealsLocalDataSourceImpl(context);
        }
        return localDataSource;
    }

    @Override
    public void insertSavedMeal(Meal meal) {
        meal.setDbType("Saved");
        meal.setUserEmail(manager.getCurrentUserEmail());
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealsDAO.insertSavedMeal(meal);
            }
        }).start();
    }

    @Override
    public void deleteSavedMeal(Meal meal) {
        meal.setDbType("Saved");
        meal.setUserEmail(manager.getCurrentUserEmail());
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealsDAO.deleteSavedMeal(meal);
            }
        }).start();
    }

    @Override
    public Observable<List<Meal>> getAllSavedMeals() {
        return mealsList;
    }
}
