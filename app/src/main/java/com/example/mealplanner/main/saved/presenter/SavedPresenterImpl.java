package com.example.mealplanner.main.saved.presenter;


import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.MealsRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class SavedPresenterImpl implements SavedPresenter{

    MealsRepository repo;

    public SavedPresenterImpl(MealsRepository repo) {
        this.repo = repo;
    }

    @Override
    public Observable<List<Meal>> getSavedMeals() {
        return repo.getSavedMeals();
    }

    @Override
    public void deleteSavedMeal(Meal meal) {
        repo.deleteSavedMeal(meal);
    }


}
