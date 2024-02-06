package com.example.mealplanner.main.filterresult.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealplanner.R;
import com.example.mealplanner.database.MealsLocalDataSourceImpl;
import com.example.mealplanner.main.filterresult.presenter.FilterPresenter;
import com.example.mealplanner.main.filterresult.presenter.FilterPresenterImpl;
import com.example.mealplanner.main.view.FilteredMealsAdapter;
import com.example.mealplanner.main.view.MealInteractionListener;
import com.example.mealplanner.models.FilteredMeal;
import com.example.mealplanner.models.MealsRepositoryImpl;
import com.example.mealplanner.networkLayer.Constants;
import com.example.mealplanner.networkLayer.RemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;


public class FilterFragment extends Fragment implements FilterView, MealInteractionListener{

    RecyclerView recyclerViewFilter;
    FilteredMealsAdapter adapter;
    FilterPresenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewFilter = view.findViewById(R.id.recycler_view_filter);
        adapter = new FilteredMealsAdapter(getContext(), new ArrayList<>(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewFilter.setLayoutManager(linearLayoutManager);
        recyclerViewFilter.setAdapter(adapter);

        presenter = new FilterPresenterImpl(MealsRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(getContext())), this);
        getFilteredList();


    }

    @Override
    public void showFilteredList(List<FilteredMeal> mealList) {
        adapter.setList(mealList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddToSaved(String mealId) {

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

    private void getFilteredList(){
        String filterType = FilterFragmentArgs.fromBundle(getArguments()).getFilterType();
        String filter = FilterFragmentArgs.fromBundle(getArguments()).getFilter();
        switch (filterType){
            case Constants.Keys.CATEGORY:
                presenter.filterMealsByCategory(filter);
                break;
            case Constants.Keys.INGREDIENT:
                presenter.filterMealsByIngredients(filter);
                break;
            case Constants.Keys.AREA:
                presenter.filterMealsByArea(filter);
                break;
        }
    }
}