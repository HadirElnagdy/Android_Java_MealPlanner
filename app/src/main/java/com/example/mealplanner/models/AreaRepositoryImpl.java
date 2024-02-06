package com.example.mealplanner.models;

import com.example.mealplanner.networkLayer.ApiCallback;
import com.example.mealplanner.networkLayer.Constants;
import com.example.mealplanner.networkLayer.RemoteDataSource;

import java.util.HashMap;

public class AreaRepositoryImpl implements AreaRepository{
    RemoteDataSource remoteDataSource;

    private static AreaRepositoryImpl repo = null;

    private AreaRepositoryImpl(RemoteDataSource remoteDataSource){
        this.remoteDataSource = remoteDataSource;
    }
    public static AreaRepositoryImpl getInstance(RemoteDataSource remoteDataSource){
        if (repo == null){
            repo = new AreaRepositoryImpl(remoteDataSource);
        }
        return repo;
    }
    public void getAreaList(ApiCallback<Object> callback){
        HashMap<String, String> qp = new HashMap<>();
        qp.put("a", "list");
        remoteDataSource.makeGetRequest(Constants.APIEndpoints.LIST_ALL, qp, callback, AreaListResponse.class);
    }
}
