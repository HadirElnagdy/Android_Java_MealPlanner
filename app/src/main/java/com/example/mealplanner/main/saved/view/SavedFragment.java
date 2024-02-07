package com.example.mealplanner.main.saved.view;

import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.example.mealplanner.models.MealsRepositoryImpl;
import com.example.mealplanner.networkLayer.RemoteDataSourceImpl;
import com.example.mealplanner.util.DayPickerDialog;
import com.google.android.material.datepicker.MaterialDatePicker;

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
    List<Meal> mealList;
    Calendar calendar = Calendar.getInstance();



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
        mealsAdapter.setTxtBtnSave("Delete");
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
                .subscribe(mealList -> {
                            mealsAdapter.setList(mealList);
                            mealsAdapter.notifyDataSetChanged();
                            this.mealList = mealList;
                        },
                        throwable -> {
                            Log.i("TAG", "showProducts: unable to show products because: "+throwable.getMessage());
                        });

    }

    @Override
    public void onSaveClicked(String mealId, Meal meal) {
        presenter.deleteSavedMeal(meal);
    }

    @Override
    public void onAddToPlanClicked(String mealId, Meal meal) {
        MaterialDatePicker<Long> dayPickerDialog = DayPickerDialog.showDialog(requireActivity().getSupportFragmentManager());
        dayPickerDialog.addOnPositiveButtonClickListener(selection -> {
            calendar.setTimeInMillis(selection);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            Log.i("TAG", "onViewCreated: selected day: " + day);
            presenter.addMealToPlan(meal, day);
        });
    }

    @Override
    public void onOpenMealClicked(String mealId, Meal meal) {
        SavedFragmentDirections.ActionSavedFragmentToMealFragment action = SavedFragmentDirections.actionSavedFragmentToMealFragment(meal, mealId);
        Navigation.findNavController(view).navigate(action);
    }




}