package com.example.mealplanner.main.search.view;

import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mealplanner.R;
import com.example.mealplanner.database.MealsLocalDataSourceImpl;
import com.example.mealplanner.main.view.MealInteractionListener;
import com.example.mealplanner.main.search.presenter.SearchPresenter;
import com.example.mealplanner.main.search.presenter.SearchPresenterImpl;
import com.example.mealplanner.main.view.MealsAdapter;
import com.example.mealplanner.models.AreaName;
import com.example.mealplanner.models.AreaRepositoryImpl;
import com.example.mealplanner.models.CategoryName;
import com.example.mealplanner.models.CategoryRepositoryImpl;
import com.example.mealplanner.models.Ingredient;
import com.example.mealplanner.models.IngredientsRepositoryImpl;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.MealsRepositoryImpl;
import com.example.mealplanner.networkLayer.Constants;
import com.example.mealplanner.networkLayer.RemoteDataSourceImpl;
import com.example.mealplanner.util.DayPickerDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchFragment extends Fragment implements SearchView, MealInteractionListener {


    RecyclerView recyclerViewSearchResult;
    EditText edtSearch;
    ChipGroup chipGroupCategory, chipGroupIngredient, chipGroupArea;
    SearchFragmentDirections.ActionSearchFragmentToMealFragment toMealAction;
    SearchFragmentDirections.ActionSearchFragmentToFilterFragment toFilterAction;

    SearchPresenter presenter;
    MealsAdapter adapter;
    Group group;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        intializeComponents(view);
        setupEdtSearch();
        setupRecyclerView();
        group.setVisibility(Group.VISIBLE);
        recyclerViewSearchResult.setVisibility(View.INVISIBLE);
        return view;
    }
    private void intializeComponents(View view){
        edtSearch = view.findViewById(R.id.edt_search);
        edtSearch.setText("");
        recyclerViewSearchResult = view.findViewById(R.id.recycler_view_search);
        chipGroupArea = view.findViewById(R.id.chip_group_area);
        chipGroupCategory = view.findViewById(R.id.chip_group_category);
        chipGroupIngredient = view.findViewById(R.id.chip_group_ingredient);
        group = view.findViewById(R.id.filtering_group);
        presenter = new SearchPresenterImpl(
                MealsRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(getContext()))
                , CategoryRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance())
                , IngredientsRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance())
                , AreaRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance())
                , this);
        presenter.getCategoryList();
        presenter.getIngredientList();
        presenter.getAreaList();

    }
    private void setupEdtSearch(){
        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    group.setVisibility(View.INVISIBLE);
                    recyclerViewSearchResult.setVisibility(View.VISIBLE);
                } else {
                    group.setVisibility(View.VISIBLE);
                    recyclerViewSearchResult.setVisibility(View.INVISIBLE);
                }
            }
        });

        Disposable disposable = Observable.create((ObservableOnSubscribe<String>) emitter -> {
                    edtSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            Log.i("TAG", "beforeTextChanged: ");
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            Log.i("TAG", "onTextChanged: ");
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            Log.i("TAG", "afterTextChanged: ");
                            String mealName = s.toString().trim();
                            if (!mealName.isEmpty()) {
                                emitter.onNext(mealName);
                            } else {
                                adapter.setList(new ArrayList<>());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                })
                .subscribeOn(Schedulers.io())
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(mealName -> presenter.searchMeals(mealName));
    }
    private void setupRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewSearchResult.setLayoutManager(linearLayoutManager);
        adapter = new MealsAdapter(getContext(), new ArrayList<>(), this);
        recyclerViewSearchResult.setAdapter(adapter);
    }


    @Override
    public void onSaveClicked(String mealId, Meal meal) {
        presenter.addToSaved(mealId);
    }

    @Override
    public void onAddToPlanClicked(String mealId, Meal meal) {
        Calendar calendar = Calendar.getInstance();
        MaterialDatePicker<Long> dayPickerDialog = DayPickerDialog.showDialog(requireActivity().getSupportFragmentManager());
        dayPickerDialog.addOnPositiveButtonClickListener(selection -> {
            calendar.setTimeInMillis(selection);
            int date = calendar.get(Calendar.DAY_OF_MONTH);
            presenter.addToPlan(mealId, date);
        });
    }

    @Override
    public void onOpenMealClicked(String mealId, Meal meal) {
        SearchFragmentDirections.ActionSearchFragmentToMealFragment action = SearchFragmentDirections.actionSearchFragmentToMealFragment(meal, mealId);
        Navigation.findNavController(view).navigate(action);
    }



    @Override
    public void showMealDetails(String mealId) {
        toMealAction = SearchFragmentDirections.actionSearchFragmentToMealFragment(null, mealId);
        Navigation.findNavController(view).navigate(toMealAction);
    }
    public void showCategoryList(List<CategoryName> categoryNames){
        for(CategoryName categoryName : categoryNames){
            Chip chip = new Chip(getContext());
            chip.setText(categoryName.getStrCategory());
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toFilterAction = SearchFragmentDirections.actionSearchFragmentToFilterFragment(Constants.Keys.CATEGORY, categoryName.getStrCategory());
                    Navigation.findNavController(view).navigate(toFilterAction);
                }
            });
            chipGroupCategory.addView(chip);
        }

    }
    public void showIngredientList(List<Ingredient> ingredients){
        for(Ingredient ingredient : ingredients){
            Chip chip = new Chip(getContext());
            chip.setText(ingredient.getStrIngredient());
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toFilterAction = SearchFragmentDirections.actionSearchFragmentToFilterFragment(Constants.Keys.INGREDIENT, ingredient.getStrIngredient());
                    Navigation.findNavController(view).navigate(toFilterAction);
                }
            });
            chipGroupIngredient.addView(chip);
        }
    }
    public void showAreaList(List<AreaName> areaNames){
        for(AreaName areaName : areaNames){
            Chip chip = new Chip(getContext());
            chip.setText(areaName.getStrArea());
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toFilterAction = SearchFragmentDirections.actionSearchFragmentToFilterFragment(Constants.Keys.AREA, areaName.getStrArea());
                    Navigation.findNavController(view).navigate(toFilterAction);
                }
            });
            chipGroupArea.addView(chip);
        }
    }


    @Override
    public void showSearchResults(List<Meal> mealList) {
        if(mealList == null) mealList = new ArrayList<>();
        adapter.setList(mealList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String error) {

    }


}

