package com.example.mealplanner.models;

import com.example.mealplanner.networkLayer.ApiCallback;
import com.example.mealplanner.networkLayer.Constants;
import com.example.mealplanner.networkLayer.RemoteDataSource;

import java.util.HashMap;
import java.util.Map;

public class CategoryRepositoryImpl {
    RemoteDataSource remoteDataSource;

    private static CategoryRepositoryImpl repo = null;

    private CategoryRepositoryImpl(RemoteDataSource remoteDataSource){
        this.remoteDataSource = remoteDataSource;
    }
    public static CategoryRepositoryImpl getInstance(RemoteDataSource remoteDataSource){
        if (repo == null){
            repo = new CategoryRepositoryImpl(remoteDataSource);
        }
        return repo;
    }
    public void getCategoryList(ApiCallback<Object> callback){
        HashMap<String, String> qp = new HashMap<>();
        qp.put("c", "list");
        remoteDataSource.makeGetRequest(Constants.APIEndpoints.LIST_ALL, qp, callback, CategoryListResponse.class);
    }

}
