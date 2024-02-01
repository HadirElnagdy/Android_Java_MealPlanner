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
    @Query("SELECT * From saved_meals_table")
    Observable<List<Meal>> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(Meal Meal);

    @Delete
    void deleteMeal(Meal meal);
}
