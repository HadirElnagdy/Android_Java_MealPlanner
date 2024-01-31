package com.example.mealplanner.models;

import com.example.mealplanner.networkLayer.ApiCallback;
import com.example.mealplanner.networkLayer.Constants;
import com.example.mealplanner.networkLayer.RemoteDataSource;

import java.util.HashMap;
import java.util.Map;

public class MealsRepositoryImpl implements MealsRepository{

    RemoteDataSource remoteDataSource;

    private static MealsRepositoryImpl repo = null;

    private MealsRepositoryImpl(RemoteDataSource remoteDataSource){
        this.remoteDataSource = remoteDataSource;
    }
    public static MealsRepositoryImpl getInstance(RemoteDataSource remoteDataSource){
        if (repo == null){
            repo = new MealsRepositoryImpl(remoteDataSource);
        }
        return repo;
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

}
