package com.example.mealplanner.networkLayer;


import com.example.mealplanner.models.AreaListResponse;
import com.example.mealplanner.models.CategoriesResponse;
import com.example.mealplanner.models.CategoryListResponse;
import com.example.mealplanner.models.FilteredMeal;
import com.example.mealplanner.models.FilteredMealsResponse;
import com.example.mealplanner.models.IngredientListResponse;
import com.example.mealplanner.models.MealsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    Call<MealsResponse> getMeals(@Url String url);

    @GET
    Call<CategoriesResponse> getCategories(@Url String url);

    @GET
    Call<FilteredMealsResponse> getFilteredMeals(@Url String url);

    @GET
    Call<CategoryListResponse> getCategoryList(@Url String url);

    @GET
    Call<AreaListResponse> getAreaList(@Url String url);

    @GET
    Call<IngredientListResponse> getIngredientList(@Url String url);

}
