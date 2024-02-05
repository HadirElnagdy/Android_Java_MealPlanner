package com.example.mealplanner.models;

import com.example.mealplanner.networkLayer.ApiCallback;
import com.example.mealplanner.networkLayer.Constants;
import com.example.mealplanner.networkLayer.RemoteDataSource;

import java.util.HashMap;

public class IngredientsRepositoryImpl implements IngredientsRepository{
    RemoteDataSource remoteDataSource;

    private static IngredientsRepositoryImpl repo = null;

    private IngredientsRepositoryImpl(RemoteDataSource remoteDataSource){
        this.remoteDataSource = remoteDataSource;
    }
    public static IngredientsRepositoryImpl getInstance(RemoteDataSource remoteDataSource){
        if (repo == null){
            repo = new IngredientsRepositoryImpl(remoteDataSource);
        }
        return repo;
    }
    public void getIngredientList(ApiCallback<Object> callback){
        HashMap<String, String> qp = new HashMap<>();
        qp.put("i", "list");
        remoteDataSource.makeGetRequest(Constants.APIEndpoints.LIST_ALL, qp, callback, IngredientListResponse.class);
    }
}
