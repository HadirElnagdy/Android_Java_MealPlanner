package com.example.mealplanner.database;

import android.content.Context;

import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.UserManager;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {

    private MealsDAO mealsDAO;
    private Observable<List<Meal>> savedMealsList, plannedMealsList;
    private static MealsLocalDataSourceImpl localDataSource = null;
    private UserManager manager;
    String userEmail;

    private MealsLocalDataSourceImpl(Context context){
        manager = new UserManager();
        mealsDAO = AppDatabase.getInstance(context.getApplicationContext()).getMealDAO();
        if(manager.getUser() != null){
            userEmail = manager.getCurrentUserEmail();
            savedMealsList = mealsDAO.getAllSavedMeals(userEmail);
            plannedMealsList = mealsDAO.getAllPlannedMeals(userEmail);
        }
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
        meal.setUserEmail(userEmail);
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
        meal.setUserEmail(userEmail);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealsDAO.deleteSavedMeal(meal);
            }
        }).start();
    }


    @Override
    public Observable<List<Meal>> getAllSavedMeals() {
        return savedMealsList;
    }

    @Override
    public Observable<Boolean> isSaved(String idMeal) {
       return mealsDAO.isSaved(idMeal, userEmail);
    }

    @Override
    public Observable<List<Meal>> getAllPlannedMeals() {
        return plannedMealsList;
    }

    @Override
    public void insertPlannedMeal(Meal meal) {
        meal.setDbType("Planned");
        meal.setUserEmail(userEmail);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealsDAO.insertPlannedMeal(meal);
            }
        }).start();
    }

    @Override
    public void deletePlannedMeal(Meal meal) {
        meal.setDbType("Planned");
        meal.setUserEmail(userEmail);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealsDAO.deletePlannedMeal(meal);
            }
        }).start();
    }


}
