package com.example.mealplanner.main.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.main.home.presenter.HomePresenter;
import com.example.mealplanner.main.home.presenter.HomePresenterImpl;
import com.example.mealplanner.models.CategoryName;
import com.example.mealplanner.models.CategoryRepository;
import com.example.mealplanner.models.CategoryRepositoryImpl;
import com.example.mealplanner.models.FilteredMeal;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.MealsRepositoryImpl;
import com.example.mealplanner.networkLayer.ImageLoader;
import com.example.mealplanner.networkLayer.RemoteDataSourceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeView, MealInteractionListener{

    RecyclerView recyclerView;
    TextView txtRandomMeal;
    Button btnAddToPlan;
    ImageButton btnSaveRandom;
    ImageView imgRandomMeal;

    HomeAdapter adapter;
    ImageLoader imageLoader;

    List<CategoryName> categoryNames;
    List<List<FilteredMeal>> mealList;
    HomePresenter presenter;
    Meal randomMeal;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        intializeViews(view);
        intializeRecyclerView();
        intializePresenter();
        loadData();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAddToFavoritesClick(String id) {

    }

    @Override
    public void onAddToPlanClick(String id) {

    }

    @Override
    public void onOpenMealClick(String id) {

    }

    public void showRandomMeal(Meal meal){
        randomMeal = meal;
        txtRandomMeal.setText(meal.getStrMeal());
        imageLoader.loadImage(meal.getStrMealThumb(), imgRandomMeal);
    }

    @Override
    public void showMealCategories(List<CategoryName> categories) {
        categoryNames = categories;
        for(CategoryName categoryName : categories){
            presenter.getMealsByCategory(categoryName.getStrCategory());
        }
    }

    @Override
    public void addToMealsList(List<FilteredMeal> meals) {
        mealList.add(meals);
        Log.i("TAG", "addToMealsList: " + categoryNames + " " + mealList);
        if(mealList.size() == categoryNames.size()){
            adapter.setList(categoryNames, mealList);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void showError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void intializeViews(View view){
        recyclerView = view.findViewById(R.id.recycler_view_home);
        txtRandomMeal = view.findViewById(R.id.txt_random_meal);
        btnAddToPlan = view.findViewById(R.id.btn_add_plan_random);
        btnSaveRandom = view.findViewById(R.id.btn_save_random);
        imgRandomMeal = view.findViewById(R.id.img_random_meal);
        imageLoader = new ImageLoader(getContext());

        btnSaveRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchBtnImg(btnSaveRandom);
                //onAddToFavoritesClick(randomMeal.getIdMeal());
            }
        });

        btnAddToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onAddToPlanClick(randomMeal.getIdMeal());
            }
        });
    }
    private void switchBtnImg(ImageButton btn) {
        // Get the current image resource ID
        Integer currentImgResID = (Integer) btn.getTag();

        // Check if the current image resource ID matches ic_save
        if (currentImgResID != null && currentImgResID == R.drawable.ic_save) {
            // Set the image resource ID to ic_saved
            btn.setImageResource(R.drawable.ic_saved);
            // Update the tag with the new image resource ID
            btn.setTag(R.drawable.ic_saved);
        } else {
            // Set the image resource ID to ic_save
            btn.setImageResource(R.drawable.ic_save);
            // Update the tag with the new image resource ID
            btn.setTag(R.drawable.ic_save);
        }
    }
    private void intializeRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new HomeAdapter(getContext(), new ArrayList<>(), new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
    }

    private void populateViews() {
        adapter.setList(categoryNames, mealList);
        adapter.notifyDataSetChanged();
        showRandomMeal(randomMeal);
    }
    private void intializePresenter(){
        presenter = new HomePresenterImpl(this
                , CategoryRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance())
                , MealsRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance()));
    }
    private void loadData(){
        if (mealList != null && randomMeal != null && categoryNames != null) {
            populateViews();
        } else {
            mealList = new ArrayList<>();
            presenter.getRandomMeal();
            presenter.getCategoryList();
        }
    }
}