package com.example.mealplanner.main.plan.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanFragment extends Fragment implements PlanView, PlanListener, PlanClickListener {

    private TabLayout tabLayout;
    private PlanPagerAdapter pagerAdapter;
    private ViewPager2 viewPager;
    private View view;
    private PlanPresenter presenter;
    private Calendar calendar;
    private Map<Integer, List<Meal>> mealsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        presenter = new PlanPresenterImpl(this, MealsRepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), MealsLocalDataSourceImpl.getInstance(getContext())));
        if(!presenter.updateUserEmail(getContext())){
            showLoginDialog();
        }else {
            mealsList = new HashMap<>();
            tabLayout = view.findViewById(R.id.tab_layout);
            viewPager = view.findViewById(R.id.view_pager);
            calendar = Calendar.getInstance();

            int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date firstDayOfMonth = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DATE, -1);
            Date lastDayOfMonth = calendar.getTime();

            List<String> tabNames = generateTabNames(firstDayOfMonth, lastDayOfMonth);
            pagerAdapter = new PlanPagerAdapter(getContext(), new HashMap<>(), this, this);

            viewPager.setAdapter(pagerAdapter);
            viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                int currentTabDay = position + 1;
                if (currentTabDay == currentDayOfMonth) {
                    tab.setText("Today");
                } else if (currentTabDay == currentDayOfMonth - 1) {
                    tab.setText("Yesterday");
                } else if (currentTabDay == currentDayOfMonth + 1) {
                    tab.setText("Tomorrow");
                } else {
                    tab.setText(tabNames.get(position));
                }
            }).attach();

            viewPager.setCurrentItem(currentDayOfMonth - 1, false);
            pagerAdapter.updateMealList(mealsList);
            pagerAdapter.notifyDataSetChanged();
        }
    }

    private List<String> generateTabNames(Date firstDayOfMonth, Date lastDayOfMonth) {
        List<String> tabNames = new ArrayList<>();
        calendar.setTime(firstDayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd", Locale.getDefault());

        while (calendar.getTime().before(lastDayOfMonth) || calendar.getTime().equals(lastDayOfMonth)) {
            tabNames.add(dateFormat.format(calendar.getTime()));
            String date = dateFormat.format(calendar.getTime()).split(" ")[1];
            setMealsList(date);
            calendar.add(Calendar.DATE, 1);
        }
        return tabNames;
    }

    private void setMealsList(String date) {
        Observable<List<Meal>> mealObservable = presenter.getPlanByDate(date);
        Disposable disposable = mealObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealList -> {
                            if (!mealList.isEmpty()) {
                                mealsList.put(Integer.parseInt(date)-1, mealList);
                            } else {
                                mealsList.put(Integer.parseInt(date)-1, new ArrayList<>());
                            }
                            pagerAdapter.updateMealList(mealsList);
                            pagerAdapter.notifyDataSetChanged();
                        },
                        throwable -> {
                            Log.i("PlanFragment", "throwable: " + throwable.getMessage());
                        });
    }

    @Override
    public void onOpenMealClicked(String mealId, Meal meal) {
        PlanFragmentDirections.ActionCalendarFragmentToMealFragment action = PlanFragmentDirections.actionCalendarFragmentToMealFragment(meal, null);
        Navigation.findNavController(view).navigate(action);
    }


    @Override
    public void onDeletePlanClicked(String mealId, Meal meal) {
        presenter.deletePlannedMeal(meal);
        Toast.makeText(getContext(), "Meal deleted successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveClicked(String mealId, Meal meal) {
        presenter.addToSaved(meal);
        Toast.makeText(getContext(), "Meal saved successfully!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLoginDialog() {
        CustomAlertDialog.showLoginDialog(getContext(), view);
    }
}
