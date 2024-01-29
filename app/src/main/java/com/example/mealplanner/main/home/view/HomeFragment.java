package com.example.mealplanner.main.home.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealplanner.R;


public class HomeFragment extends Fragment implements HomeView, MealInteractionListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onAddToFavoritesClick(int position) {

    }

    @Override
    public void onAddToPlanClick(int position) {

    }

    @Override
    public void onOpenMealClick(int position) {

    }
}