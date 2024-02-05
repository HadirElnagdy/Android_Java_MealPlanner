package com.example.mealplanner.main.search.presenter;

import com.example.mealplanner.main.search.view.SearchView;
import com.example.mealplanner.models.AreaListResponse;
import com.example.mealplanner.models.AreaRepository;
import com.example.mealplanner.models.CategoryListResponse;
import com.example.mealplanner.models.CategoryRepository;
import com.example.mealplanner.models.IngredientListResponse;
import com.example.mealplanner.models.IngredientsRepository;
import com.example.mealplanner.models.MealsRepository;
import com.example.mealplanner.models.MealsResponse;
import com.example.mealplanner.networkLayer.ApiCallback;
import com.example.mealplanner.networkLayer.Constants;

public class SearchPresenterImpl implements SearchPresenter, ApiCallback<Object> {
    MealsRepository mealsRepository;
    CategoryRepository categoryRepository;
    IngredientsRepository ingredientsRepository;
    AreaRepository areaRepository;
    SearchView view;
    String TAG = "SearchPresenterImpl";

    public SearchPresenterImpl(MealsRepository mealsRepository, CategoryRepository categoryRepository, IngredientsRepository ingredientsRepository, AreaRepository areaRepository, SearchView view) {
        this.mealsRepository = mealsRepository;
        this.categoryRepository = categoryRepository;
        this.ingredientsRepository = ingredientsRepository;
        this.areaRepository = areaRepository;
        this.view = view;
    }

    @Override
    public void searchMeals(String mealName) {
        mealsRepository.getMealsByName(mealName, this);
    }



    @Override
    public void addToSaved(String mealId) {

    }

    @Override
    public void delFromSaved(String mealId) {

    }

    @Override
    public void addToPlan(String mealId) {

    }

    @Override
    public void delFromPlan(String mealId) {

    }

    @Override
    public void getCategoryList() {
        categoryRepository.getCategoryList(this);
    }

    @Override
    public void getIngredientList() {
        ingredientsRepository.getIngredientList(this);
    }

    @Override
    public void getAreaList() {
        areaRepository.getAreaList(this);
    }

    @Override
    public void onSuccess(Object response, String endpoint) {
        if (endpoint.equals(Constants.APIEndpoints.SEARCH_MEAL)) {
            view.showSearchResults(((MealsResponse) response).getMeals());
        }else if(endpoint.equals(Constants.APIEndpoints.LIST_ALL)){
            if(response instanceof CategoryListResponse){
                view.showCategoryList(((CategoryListResponse)response).getCategoryNames());
            }else if(response instanceof IngredientListResponse){
                view.showIngredientList(((IngredientListResponse)response).getIngredients());
            }else {
                view.showAreaList(((AreaListResponse)response).getAreaNames());
            }
        }

    }

    @Override
    public void onError(int statusCode, String message) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
