/*
package com.example.mealplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mealplanner.models.Meal;

@Database(entities = {Meal.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase = null;
    public abstract MealsDAO getMealDAO();
    public static synchronized AppDatabase getInstance(Context context){
        if(appDatabase == null){
            appDatabase = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "mealsdb")
                    .build();
        }
        return appDatabase;
    }
}
*/
