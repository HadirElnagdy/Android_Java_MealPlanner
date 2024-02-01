package com.example.mealplanner.networkLayer;

import android.util.Log;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
        Observable<T> observable = getObservable(responseType, buildUrl(endpoint, queryParams));
        if (observable != null) {
            Disposable subscribe = observable.subscribeOn(Schedulers.io())
                    .retry(10)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
//                                Log.i("TAG", "onResponse: " + response.getProducts().get(0).getTitle());
                                callback.onSuccess(response, endpoint);
                            },
                            error -> {
                                Log.i("TAG", "Error: " + error.getMessage());
                                callback.onFailure(error);
                            }
                    );
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

    private <T> Observable<T> getObservable(Class<T> responseType, String url) {
        switch (responseType.getSimpleName()) {
            case "MealsResponse":
                return (Observable<T>) apiService.getMeals(url);
            case "CategoriesResponse":
                return (Observable<T>) apiService.getCategories(url);
            case "AreaListResponse":
                return (Observable<T>) apiService.getAreaList(url);
            case "CategoryListResponse":
                return (Observable<T>) apiService.getCategoryList(url);
            case "IngredientListResponse":
                return (Observable<T>) apiService.getIngredientList(url);
            case "FilteredMealsResponse":
                return (Observable<T>) apiService.getFilteredMeals(url);
            default:
                return null;
        }
    }
}
