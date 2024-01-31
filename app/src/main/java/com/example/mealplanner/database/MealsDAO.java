package com.example.mealplanner.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.SavedMeal;

import java.util.List;

//@Dao
public interface MealsDAO {
    /*@Query("SELECT * From saved_meals_table")
    LiveData<List<SavedMeal>> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(SavedMeal Meal);

    @Delete
    void deleteMeal(SavedMeal meal);*/
}
