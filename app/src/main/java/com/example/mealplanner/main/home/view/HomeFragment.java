package com.example.mealplanner.main.home.view;

import static androidx.core.content.ContextCompat.getSystemService;

import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.example.mealplanner.database.MealsLocalDataSourceImpl;
import com.example.mealplanner.main.home.presenter.HomePresenter;
import com.example.mealplanner.main.home.presenter.HomePresenterImpl;
import com.example.mealplanner.main.view.MealInteractionListener;
import com.example.mealplanner.models.CategoryName;
import com.example.mealplanner.models.CategoryRepositoryImpl;
import com.example.mealplanner.models.FilteredMeal;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.MealsRepositoryImpl;
import com.example.mealplanner.networkLayer.ImageLoader;
import com.example.mealplanner.networkLayer.RemoteDataSourceImpl;
import com.example.mealplanner.util.DayPickerDialog;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeFragment extends Fragment implements HomeView, MealInteractionListener {

    RecyclerView recyclerView;
    TextView txtRandomMeal;
    Button btnAddToPlan;
    Button btnSaveRandom;
    ImageView imgRandomMeal;

    HomeAdapter adapter;
    ImageLoader imageLoader;

    List<CategoryName> categoryNames;
    List<List<FilteredMeal>> mealList;
    HomePresenter presenter;
    CardView cardView;
    Meal randomMeal;
    Map<String, Boolean> saved;
    HomeFragmentDirections.ActionHomeFragmentToMealFragment action;
    Calendar calendar = Calendar.getInstance();
    View view;


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
        this.view = view;
    }

    @Override
    public void onSaveClicked(String id, Meal meal) {
        presenter.toggleSavedStatus(id, meal);
    }

    @Override
    public void onAddToPlanClicked(String id, Meal meal) {
        MaterialDatePicker<Long> dayPickerDialog = DayPickerDialog.showDialog(requireActivity().getSupportFragmentManager());
        dayPickerDialog.addOnPositiveButtonClickListener(selection -> {
            calendar.setTimeInMillis(selection);
            int date = calendar.get(Calendar.DAY_OF_MONTH);
            presenter.addMealToPlan(id, meal, date);
        });
    }

    @Override
    public void onOpenMealClicked(String id, Meal meal) {
        action.setMeal(meal);
        action.setMealId(id);
        Navigation.findNavController(view).navigate(action);
    }




    public void showRandomMeal(Meal meal){
        randomMeal = meal;
        txtRandomMeal.setText(meal.getStrMeal());
        imageLoader.loadImage(meal.getStrMealThumb(), imgRandomMeal);
        action.setMeal(randomMeal);
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
        cardView = view.findViewById(R.id.cardView);
        imageLoader = new ImageLoader(getContext());
        saved = new HashMap<>();
        action = HomeFragmentDirections.actionHomeFragmentToMealFragment(null, null);
        btnSaveRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.toggleSavedStatus(null, getRandomMeal());
            }
        });

        btnAddToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddToPlanClicked(null, getRandomMeal());
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(action);
            }
        });

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
                , MealsRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(getContext())));
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


    Meal getRandomMeal(){
        return randomMeal;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getContext(), ConnectivityManager.class);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}