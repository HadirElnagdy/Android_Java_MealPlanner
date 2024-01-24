package com.example.mealplanner.authentication.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mealplanner.R;
import com.example.mealplanner.models.AreaListResponse;
import com.example.mealplanner.models.CategoriesResponse;
import com.example.mealplanner.models.CategoryListResponse;
import com.example.mealplanner.models.FilteredMealsResponse;
import com.example.mealplanner.models.IngredientListResponse;
import com.example.mealplanner.models.MealsResponse;
import com.example.mealplanner.networkLayer.ApiCallback;
import com.example.mealplanner.networkLayer.RemoteDataSourceImpl;
import com.example.mealplanner.networkLayer.Constants;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

    }


}
