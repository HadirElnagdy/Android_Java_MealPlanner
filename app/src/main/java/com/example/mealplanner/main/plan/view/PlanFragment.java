package com.example.mealplanner.main.plan.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mealplanner.R;
import com.example.mealplanner.database.MealsLocalDataSourceImpl;
import com.example.mealplanner.main.plan.presenter.PlanPresenter;
import com.example.mealplanner.main.plan.presenter.PlanPresenterImpl;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.MealsRepositoryImpl;
import com.example.mealplanner.networkLayer.RemoteDataSourceImpl;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PlanFragment extends Fragment implements PlanView, PlanListener, PlanClickListener {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    View view;
    PlanPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        presenter = new PlanPresenterImpl(this,
                MealsRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(getContext())));
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {

        }).attach();
        viewPager.setOffscreenPageLimit(1);

    }

    @Override
    public void onOpenMealClicked(String mealId, Meal meal) {

    }

    @Override
    public void onRemovePlanClicked(String mealId, Meal meal) {

    }

    @Override
    public void onSaveClicked(String mealId, Meal meal) {

    }

    @Override
    public void showLoginDialog() {

    }
}