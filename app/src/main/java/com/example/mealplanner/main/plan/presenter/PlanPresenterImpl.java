package com.example.mealplanner.main.plan.presenter;

import android.content.Context;
import android.util.Log;

import com.example.mealplanner.database.MealsLocalDataSourceImpl;
import com.example.mealplanner.main.plan.view.PlanView;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.MealsRepository;
import com.example.mealplanner.models.UserManager;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImpl implements PlanPresenter{
    PlanView view;
    MealsRepository repository;
    UserManager manager = new UserManager();


    public PlanPresenterImpl(PlanView view, MealsRepository repository/*, CloudRepoInterface cloudRepository*/) {
        this.view = view;
        this.repository = repository;

    }

    @Override
    public Observable<List<Meal>> getPlanByDate(String planDate) {
        Observable<List<Meal>> observable = getAllPlans();
        Disposable disposable = observable
                .subscribeOn(Schedulers.io())
                .flatMapIterable(meals -> meals) // Flatten the list to individual items
                .filter(meal -> meal.getPlanDate().equals(planDate)) // Filter by planDate
                .toList() // Collect the filtered items back into a list
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(filteredList -> {
                    if (!filteredList.isEmpty()) {
                        Log.i("PlanPresenterImpl", "getPlanByDate: " + filteredList.get(0).getPlanDate());
                    } else {
                        Log.i("PlanPresenterImpl", "getPlanByDate: filtered list is empty!!");
                    }
                }, throwable -> {
                    Log.i("PlanPresenterImpl", "throwable: " + throwable.getMessage());
                });
        return repository.getPlanByDate(planDate);
    }

    @Override
    public Observable<List<Meal>> getAllPlans() {
        return repository.getPlannedMeals();
    }
    public void updateUserEmail(Context context){
        MealsLocalDataSourceImpl.getInstance(context).updateUserEmail(manager.getCurrentUserEmail());
        repository.setLocalDataSource(MealsLocalDataSourceImpl.getInstance(context));
    }

    @Override
    public void deletePlannedMeal(Meal meal) {
        repository.deletePlannedMeal(meal);

    }

    @Override
    public void addToSaved(Meal meal) {
        repository.addMealToSaved(meal);
    }


}
