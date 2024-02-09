package com.example.mealplanner.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.UserManager;
import com.example.mealplanner.util.NetworkUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {

    private MealsDAO mealsDAO;
    private Observable<List<Meal>> savedMealsList, plannedMealsList;
    private static MealsLocalDataSourceImpl localDataSource = null;
    private DatabaseReference databaseReference;
    private UserManager manager;
    String userEmail;
    String sanitizedEmail;
    Context context;

    private MealsLocalDataSourceImpl(Context context){
        manager = new UserManager();
        this.context = context;
        mealsDAO = AppDatabase.getInstance(context.getApplicationContext()).getMealDAO();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("meals");

        if(manager.getUser() != null){
            userEmail = manager.getCurrentUserEmail();
            sanitizedEmail = userEmail.replace(".", "_").replace("#", "_").replace("$", "_").replace("[", "_").replace("]", "_");

            savedMealsList = mealsDAO.getAllSavedMeals(userEmail);
            plannedMealsList = mealsDAO.getAllPlannedMeals(userEmail);
        }
    }

    public static MealsLocalDataSourceImpl getInstance(Context context){
        if(localDataSource == null){
            localDataSource = new MealsLocalDataSourceImpl(context);
        }
        return localDataSource;
    }
    public void updateUserEmail(String userEmail){
        this.userEmail = userEmail;
        savedMealsList = mealsDAO.getAllSavedMeals(userEmail);
        plannedMealsList = mealsDAO.getAllPlannedMeals(userEmail);
    }
    @Override
    public void insertSavedMeal(Meal meal) {
        meal.setDbType("Saved");
        meal.setUserEmail(userEmail);
        Observable.fromCallable(() -> {
                    mealsDAO.insertSavedMeal(meal);
                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            databaseReference.child(sanitizedEmail).child("savedMeals").child(meal.getIdMeal()).setValue(meal);
                        },
                        error -> {
                            // Handle error
                            Log.e("TAG", "Error inserting saved meal: " + error.getMessage());
                        }
                );
    }

    @Override
    public void deleteSavedMeal(Meal meal) {
        meal.setDbType("Saved");
        meal.setUserEmail(userEmail);
        Observable.fromCallable(() -> {
                    mealsDAO.deleteSavedMeal(meal);
                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            databaseReference.child(sanitizedEmail).child("savedMeals").child(meal.getIdMeal()).removeValue();
                        },
                        error -> {
                            Log.e("TAG", "Error inserting saved meal: " + error.getMessage());
                        }
                );
    }


    @Override
    public Observable<List<Meal>> getAllSavedMeals() {
        return savedMealsList;
    }

    @Override
    public Observable<Boolean> isSaved(String idMeal) {
       return mealsDAO.isSaved(idMeal, userEmail);
    }

    @Override
    public Observable<List<Meal>> getAllPlannedMeals() {
        return plannedMealsList;
    }

    @Override
    public Observable<List<Meal>> getPlanByDate(String planDate) {
        return mealsDAO.getPlanByDate(userEmail, planDate);
    }

    @Override
    public void insertPlannedMeal(Meal meal) {
        meal.setDbType("Planned");
        meal.setUserEmail(userEmail);
        Observable.fromCallable(() -> {
                    mealsDAO.insertSavedMeal(meal);
                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            databaseReference.child(sanitizedEmail).child("plannedMeals").child(meal.getPlanDate()).setValue(meal);
                        },
                        error -> {
                            Log.e("TAG", "Error inserting saved meal: " + error.getMessage());
                        }
                );
    }

    @Override
    public void deletePlannedMeal(Meal meal) {
        meal.setDbType("Planned");
        meal.setUserEmail(userEmail);
        Observable.fromCallable(() -> {
                    mealsDAO.deletePlannedMeal(meal);
                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            databaseReference.child(sanitizedEmail).child("plannedMeals").child(meal.getPlanDate()).removeValue();
                        },
                        error -> {
                            Log.e("TAG", "Error inserting saved meal: " + error.getMessage());
                        }
                );
    }




}
