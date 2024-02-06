package com.example.mealplanner.main.plan.view;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Locale;

public class PlanFragment extends Fragment implements PlanView, PlanListener, PlanClickListener {

    ArrayList<PlanAdapter> adapterList;
    TabLayout tabLayout;
    ViewPager2 planPager;
    PlanPagerAdapter adapter;
    PlanPresenter presenter;
    Calendar calendar = Calendar.getInstance();
    int dayOfMonth;
    View view;

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
                MealsRepositoryImpl.getInstance(
                        RemoteDataSourceImpl.getInstance(),
                        MealsLocalDataSourceImpl.getInstance(getContext())
                )/*,
                CloudRepo.getInstance(MealsRepository.getInstance(
                        MealClient.getInstance(),
                        ConcreteLocalSource.getInstance(getContext()))
                )*/
        );

        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        adapterList = new ArrayList<>();
        ArrayList<String> tabName = getDates();

        //presenter.getAllPlans();

        tabLayout = view.findViewById(R.id.tab_layout);
        planPager = view.findViewById(R.id.view_pager);
        adapter = new PlanPagerAdapter(getContext(), adapterList, this);

        planPager.setAdapter(adapter);
        planPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        new TabLayoutMediator(tabLayout, planPager, (tab, position) -> {
            if (position + 1 == dayOfMonth)
                tab.setText("Today");
            else if (position + 2 == dayOfMonth)
                tab.setText("Yesterday");
            else if (position + 1 == dayOfMonth + 1)
                tab.setText("Tomorrow");
            else
                tab.setText(tabName.get(position));
        }).attach();

        planPager.setCurrentItem(dayOfMonth - 1, false);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String dayID = String.valueOf(tab.getText().charAt(tab.getText().length() - 1));
                //Log.i(TAG, "onTabSelected: plans of day: " + dayID);
//                getPlanOfDay(dayID);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                String dayID = String.valueOf(tab.getText().charAt(tab.getText().length() - 1));
                //Log.i(TAG, "onTabReselected: plans of day: " + dayID);
            }
        });

    }

    public ArrayList<String> getDates() {
        int month = calendar.get(Calendar.MONTH);

        Log.i("TAG", "getDates: calender, now: " + calendar.getTime());

        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        String[] months = {
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };

        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        ArrayList<Integer> days = new ArrayList<>();
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= maxDay; i++) {
            days.add(i);
            adapterList.add(new PlanAdapter(getContext(), new ArrayList<>(), this));
        }

        ArrayList<String> tabNames = new ArrayList<>();
        days.forEach(day -> {
            int index = (day - 1) % dayNames.length;
            String formattedTabName = String.format(new Locale(Locale.getDefault().getLanguage(), "EG"), "%s %02d/%s", dayNames[index], day, months[month]);
            tabNames.add(formattedTabName);
        });

        return tabNames;
    }

    @Override
    public void getPlanOfDay(String dayID) {
        presenter.getPlanWithId(dayID);
    }

    @Override
    public void updateAdapterWithMeal(String tabID, Meal meal) {
        //Log.i(TAG, "updateAdapterWithMeal: tab: " + Integer.parseInt(tabID) + " with " + planSimpleMeal);
//        adapterList.get(Integer.parseInt(tabID) - 1).addToList(meal);
    }

    @Override
    public void resetAdapterList(String tabID) {
        adapterList.get(Integer.parseInt(tabID) - 1).resetList();
    }

    @Override
    public void showNotLoggedInMessage() {

    }


    @Override
    public void onOpenMealClicked(String mealId) {

    }

    @Override
    public void onRemovePlanClicked(String mealId) {
        presenter.removePlan(String.valueOf(dayOfMonth), mealId);
    }
}