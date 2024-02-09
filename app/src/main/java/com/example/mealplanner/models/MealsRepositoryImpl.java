package com.example.mealplanner.models;

import com.example.mealplanner.database.MealsLocalDataSource;
import com.example.mealplanner.networkLayer.ApiCallback;
import com.example.mealplanner.networkLayer.Constants;
import com.example.mealplanner.networkLayer.RemoteDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;

public class MealsRepositoryImpl implements MealsRepository{

    RemoteDataSource remoteDataSource;
    MealsLocalDataSource localDataSource;

    private static MealsRepositoryImpl repo = null;

    private MealsRepositoryImpl(RemoteDataSource remoteDataSource, MealsLocalDataSource localDataSource){
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }
    public static MealsRepositoryImpl getInstance(RemoteDataSource remoteDataSource, MealsLocalDataSource localDataSource){
        if (repo == null){
            repo = new MealsRepositoryImpl(remoteDataSource, localDataSource);
        }
        return repo;
    }
    public void setLocalDataSource(MealsLocalDataSource localDataSource){
        this.localDataSource = localDataSource;
    }

    @Override
    public void getRandomMeal(ApiCallback apiCallback) {
        remoteDataSource.makeGetRequest(Constants.APIEndpoints.RANDOM_MEAL, null, apiCallback, MealsResponse.class);
    }

    @Override
    public void getMealsByCategory(String CategoryName, ApiCallback apiCallback) {
        Map<String, String> qp = new HashMap<>();
        qp.put("c", CategoryName);
        remoteDataSource.makeGetRequest(Constants.APIEndpoints.FILTER_MEALS, qp, apiCallback, FilteredMealsResponse.class);
    }

    @Override
    public void getMealsByIngredient(String ingredientName, ApiCallback apiCallback) {
        Map<String, String> qp = new HashMap<>();
        qp.put("i", ingredientName);
        remoteDataSource.makeGetRequest(Constants.APIEndpoints.FILTER_MEALS, qp, apiCallback, FilteredMealsResponse.class);
    }

    @Override
    public void getMealsByArea(String areaName, ApiCallback apiCallback) {
        Map<String, String> qp = new HashMap<>();
        qp.put("a", areaName);
        remoteDataSource.makeGetRequest(Constants.APIEndpoints.FILTER_MEALS, qp, apiCallback, FilteredMealsResponse.class);
    }

    @Override
    public void getMealsByName(String name, ApiCallback apiCallback) {
        Map<String, String> qp = new HashMap<>();
        qp.put("s", name);
        remoteDataSource.makeGetRequest(Constants.APIEndpoints.SEARCH_MEAL, qp, apiCallback, MealsResponse.class);
    }



    public void addMealToSaved(Meal meal){
        localDataSource.insertSavedMeal(meal);
    }

    @Override
    public Observable<List<Meal>> getSavedMeals() {
        return localDataSource.getAllSavedMeals();
    }

    @Override
    public void deleteSavedMeal(Meal meal) {
        localDataSource.deleteSavedMeal(meal);
    }

    @Override
    public Observable<Boolean> isSaved(String mealId) {
        return localDataSource.isSaved(mealId);
    }

    @Override
    public void addMealToPlan(Meal meal) {
        localDataSource.insertPlannedMeal(meal);
    }

    @Override
    public Observable<List<Meal>> getPlannedMeals() {
        return localDataSource.getAllPlannedMeals();
    }

    @Override
    public Observable<List<Meal>> getPlanByDate(String date) {
        return localDataSource.getPlanByDate(date);
    }

    @Override
    public void deletePlannedMeal(Meal meal) {
        localDataSource.deletePlannedMeal(meal);
    }

    @Override
    public void getMealById(String mealId, ApiCallback apiCallback) {
        Map<String, String> qp = new HashMap<>();
        qp.put("i", mealId);
        remoteDataSource.makeGetRequest(Constants.APIEndpoints.LOOKUP_MEAL, qp, apiCallback, MealsResponse.class);
    }




}
