package com.example.mealplanner.main.search.view;

import android.os.Bundle;

import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
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
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.MealsRepositoryImpl;
import com.example.mealplanner.networkLayer.RemoteDataSourceImpl;
import com.google.android.material.chip.ChipGroup;

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
    SearchPresenter presenter;
    SearchAdapter adapter;
    Group group;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "onCreate: i'm search");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        intializeComponents(view);
        setupEdtSearch();
        setupRecyclerView();
        return view;
    }
    private void intializeComponents(View view){
        edtSearch = view.findViewById(R.id.edt_search);
        recyclerViewSearchResult = view.findViewById(R.id.recycler_view_search);
        chipGroupArea = view.findViewById(R.id.chip_group_area);
        chipGroupCategory = view.findViewById(R.id.chip_group_category);
        chipGroupIngredient = view.findViewById(R.id.chip_group_ingredient);
        group = view.findViewById(R.id.filtering_group);
        presenter = new SearchPresenterImpl(MealsRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(getContext())), this);

    }
    private void setupEdtSearch(){
        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    group.setVisibility(Group.INVISIBLE);
                    recyclerViewSearchResult.setVisibility(View.VISIBLE);
                } else {
                    group.setVisibility(Group.VISIBLE);
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
        adapter = new SearchAdapter(getContext(), new ArrayList<>(), this);
        recyclerViewSearchResult.setAdapter(adapter);
    }


    @Override
    public void onAddToSaved(String mealId) {
        presenter.addToSaved(mealId);
    }

    @Override
    public void onAddToPlanClick(String mealId) {

    }

    @Override
    public void onOpenMealClick(String mealId) {

    }

    @Override
    public void onDelFromSaved(String mealId) {

    }

    @Override
    public void showMealDetails(Meal meal) {

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

    @Override
    public void setChipGroupCategory(List<String> categories) {

    }

    @Override
    public void setChipGroupIngredient(List<String> ingredients) {

    }

    @Override
    public void setChipGroupArea(List<String> areas) {

    }
}

