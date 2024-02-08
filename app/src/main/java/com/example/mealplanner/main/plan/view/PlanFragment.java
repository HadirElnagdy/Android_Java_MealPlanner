package com.example.mealplanner.main.plan.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mealplanner.R;
import com.example.mealplanner.database.MealsLocalDataSourceImpl;
import com.example.mealplanner.main.plan.presenter.PlanPresenter;
import com.example.mealplanner.main.plan.presenter.PlanPresenterImpl;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.models.MealsRepositoryImpl;
import com.example.mealplanner.networkLayer.RemoteDataSourceImpl;
import com.example.mealplanner.util.CustomAlertDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class PlanFragment extends Fragment implements PlanView, PlanListener, PlanClickListener {

    TabLayout tabLayout;
    List<PlanAdapter> planAdapterList;
    PlanPagerAdapter pagerAdapter;
    ViewPager2 viewPager;
    View view;
    PlanPresenter presenter;
    Calendar calendar;
    int dayOfMonth;

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

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        Date lastDayOfMonth = calendar.getTime();
        planAdapterList = new ArrayList<>();
        pagerAdapter = new PlanPagerAdapter(getContext(), planAdapterList, this);

        List<String> tabNames = generateTabNames(firstDayOfMonth, lastDayOfMonth);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if(position+1 == dayOfMonth-1)
                tab.setText("Yesterday");
            else if(position+1 == dayOfMonth)
                tab.setText("Today");
            else if (position == dayOfMonth)
                tab.setText("Tomorrow");
            else
                tab.setText(tabNames.get(position));
        }).attach();

        viewPager.setCurrentItem(dayOfMonth - 1, false);

    }

    private List<String> generateTabNames(Date firstDayOfMonth, Date lastDayOfMonth) {
        List<String> tabNames = new ArrayList<>();
        calendar.setTime(firstDayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd", Locale.getDefault());

        while (calendar.getTime().before(lastDayOfMonth) || calendar.getTime().equals(lastDayOfMonth)) {
            Log.i("Calendar", "generateTabNames: " + dateFormat.format(calendar.getTime()));
            tabNames.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
        return tabNames;
    }
    @Override
    public void onOpenMealClicked(String mealId, Meal meal) {
        PlanFragmentDirections.ActionCalendarFragmentToMealFragment action = PlanFragmentDirections.actionCalendarFragmentToMealFragment(meal, null);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onDeletePlanClicked(String mealId, Meal meal) {
        presenter.deletePlannedMeal(meal);
    }

    @Override
    public void onSaveClicked(String mealId, Meal meal) {
        presenter.addToSaved(meal);
    }

    @Override
    public void updateAdapterWithMeal(String date, Meal meal) {
        planAdapterList.get(Integer.parseInt(date)-1).addToList(meal);
    }

    @Override
    public void resetAdapterList(String date) {
        planAdapterList.get(Integer.parseInt(date) - 1).resetList();
    }

    @Override
    public void showLoginDialog() {
        CustomAlertDialog.showLoginDialog(getContext(), view);
    }
}