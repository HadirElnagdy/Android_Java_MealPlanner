package com.example.mealplanner.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealplanner.models.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MealsDAO {

    //Saved
    @Query("SELECT * FROM meals_table WHERE dbType = 'Saved' AND userEmail = :userEmail")
    Observable<List<Meal>> getAllSavedMeals(String userEmail);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSavedMeal(Meal meal);

    @Delete
    void deleteSavedMeal(Meal meal);

    @Query("SELECT EXISTS(SELECT 1 FROM meals_table WHERE idMeal = :idMeal AND userEmail = :userEmail AND dbType = 'Saved')")
    Observable<Boolean> isSaved(String idMeal, String userEmail);

    //Plan
    @Query("SELECT * FROM meals_table WHERE dbType = 'Planned' AND userEmail = :userEmail")
    Observable<List<Meal>> getAllPlannedMeals(String userEmail);
    @Query("SELECT * FROM meals_table WHERE dbType = 'Planned' AND userEmail = :userEmail AND planDate = :planDate")
    Observable<List<Meal>> getPlanByDate(String userEmail, String planDate);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlannedMeal(Meal meal);

    @Delete
    void deletePlannedMeal(Meal meal);




}
