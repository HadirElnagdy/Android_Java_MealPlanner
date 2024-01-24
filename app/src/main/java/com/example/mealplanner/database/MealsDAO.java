/*
package com.example.mealplanner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealplanner.models.Meal;

import java.util.List;

@Dao
public interface MealsDAO {
    @Query("SELECT * From meals_table")
    LiveData<List<Meal>> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(Meal Meal);

    @Delete
    void deleteMeal(Meal meal);
}
*/
