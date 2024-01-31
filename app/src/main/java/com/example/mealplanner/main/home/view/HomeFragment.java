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
    List<FilteredMeal> mealList;
    HomePresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.i("TAG", "onViewCreated: i'm home");
        recyclerView = view.findViewById(R.id.recycler_view_home);
        txtRandomMeal = view.findViewById(R.id.txt_random_meal);
        btnAddToPlan = view.findViewById(R.id.btn_add_plan_random);
        btnSaveRandom = view.findViewById(R.id.btn_save_random);
        imgRandomMeal = view.findViewById(R.id.img_random_meal);
        imageLoader = new ImageLoader(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new HomeAdapter(getContext(), new ArrayList<>(), new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        presenter = new HomePresenterImpl(this
                , CategoryRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance())
                , MealsRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance()));
        presenter.getRandomMeal();
        presenter.getCategoryList();
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

    public void ShowRandomMeal(Meal meal){
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
    //we should send List<List<FilteredMeal>> meals to the adapter so that each category takes one

    @Override
    public void showMeals(List<FilteredMeal> meals) {
        adapter.setList(categoryNames, meals);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}