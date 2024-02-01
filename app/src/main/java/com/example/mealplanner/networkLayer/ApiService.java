package com.example.mealplanner.networkLayer;


import com.example.mealplanner.models.AreaListResponse;
import com.example.mealplanner.models.CategoriesResponse;
import com.example.mealplanner.models.CategoryListResponse;
import com.example.mealplanner.models.FilteredMeal;
import com.example.mealplanner.models.FilteredMealsResponse;
import com.example.mealplanner.models.IngredientListResponse;
import com.example.mealplanner.models.MealsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    Observable<MealsResponse> getMeals(@Url String url);

    @GET
    Observable<CategoriesResponse> getCategories(@Url String url);

    @GET
    Observable<FilteredMealsResponse> getFilteredMeals(@Url String url);

    @GET
    Observable<CategoryListResponse> getCategoryList(@Url String url);

    @GET
    Observable<AreaListResponse> getAreaList(@Url String url);

    @GET
    Observable<IngredientListResponse> getIngredientList(@Url String url);

}
