package com.example.mealplanner.main.saved.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealplanner.R;
import com.example.mealplanner.database.MealsLocalDataSourceImpl;
import com.example.mealplanner.main.saved.presenter.SavedPresenter;
import com.example.mealplanner.main.saved.presenter.SavedPresenterImpl;
import com.example.mealplanner.main.view.MealInteractionListener;
import com.example.mealplanner.main.view.MealsAdapter;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.MealsRepository;
import com.example.mealplanner.models.MealsRepositoryImpl;
import com.example.mealplanner.networkLayer.RemoteDataSource;
import com.example.mealplanner.networkLayer.RemoteDataSourceImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SavedFragment extends Fragment implements MealInteractionListener {

    RecyclerView recyclerViewSaved;
    MealsAdapter mealsAdapter;
    View view;
    SavedPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "onCreate: i'm saved");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        presenter = new SavedPresenterImpl(MealsRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance()
                , MealsLocalDataSourceImpl.getInstance(getContext())));
        mealsAdapter = new MealsAdapter(getContext(), new ArrayList<>(), this);
        setupRecyclerView();
    }
    private void setupRecyclerView(){
        recyclerViewSaved = view.findViewById(R.id.recycler_view_saved);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewSaved.setLayoutManager(linearLayoutManager);
        recyclerViewSaved.setAdapter(mealsAdapter);

        Observable<List<Meal>> meals = presenter.getSavedMeals();
        meals.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productList -> {
                            mealsAdapter.setList(productList);
                            mealsAdapter.notifyDataSetChanged();
                        },
                        throwable -> {
                            Log.i("TAG", "showProducts: unable to show products because: "+throwable.getMessage());
                        });
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
}