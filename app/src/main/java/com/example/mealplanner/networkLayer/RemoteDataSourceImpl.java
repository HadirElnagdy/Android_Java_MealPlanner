package com.example.mealplanner.networkLayer;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Map;

public class RemoteDataSourceImpl implements RemoteDataSource {

    private ApiService apiService;
    private static Retrofit retrofit;
    private static RemoteDataSourceImpl client = null;
    public static RemoteDataSourceImpl getInstance(){
        if(client == null){
            client = new RemoteDataSourceImpl();
        }
        return client;
    }
    private RemoteDataSourceImpl(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public <T> void makeGetRequest(String endpoint, Map<String, String> queryParams, ApiCallback<Object> callback, Class<T> responseType) {
        Call<T> call = getCall(responseType, buildUrl(endpoint, queryParams));
        if (call != null) {
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body(), endpoint);
                    } else {
                        callback.onError(response.code(), response.message());
                    }
                }
                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    callback.onFailure(t);
                }
            });
        } else {
            throw new IllegalArgumentException("Unsupported response type: " + responseType.getSimpleName());
        }
    }

    String buildUrl(String endpoint, Map<String, String> queryParams) {
        StringBuilder urlBuilder = new StringBuilder(endpoint);
        if (queryParams != null && !queryParams.isEmpty()) {
            urlBuilder.append("?");
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                urlBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());
            }
        }
        return urlBuilder.toString();
    }

    private <T> Call<T> getCall(Class<T> responseType, String url) {
        switch (responseType.getSimpleName()) {
            case "MealsResponse":
                return (Call<T>) apiService.getMeals(url);
            case "CategoriesResponse":
                return (Call<T>) apiService.getCategories(url);
            case "AreaListResponse":
                return (Call<T>) apiService.getAreaList(url);
            case "CategoryListResponse":
                return (Call<T>) apiService.getCategoryList(url);
            case "IngredientListResponse":
                return (Call<T>) apiService.getIngredientList(url);
            case "FilteredMealsResponse":
                return (Call<T>) apiService.getFilteredMeals(url);
            default:
                return null;
        }
    }
}
