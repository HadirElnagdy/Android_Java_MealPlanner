package com.example.mealplanner.main.plan.presenter;

import android.content.Context;

import com.example.mealplanner.models.Meal;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface PlanPresenter {
    public Observable<List<Meal>> getPlanByDate(String planDate);

    public Observable<List<Meal>> getAllPlans();

    public void deletePlannedMeal(Meal meal);
    public void addToSaved(Meal meal);
    public void updateUserEmail(Context context);

}
