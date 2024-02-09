package com.example.mealplanner.main.plan.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.models.Meal;

import java.util.List;
import java.util.Map;

public class PlanPagerAdapter extends RecyclerView.Adapter<PlanPagerAdapter.ViewHolder> {
    Map<Integer, List<Meal>> mealsList;
    private Context context;
    private PlanListener planListener;
    private PlanClickListener planClickListener;

    public PlanPagerAdapter(Context context, Map<Integer, List<Meal>> mealsList, PlanListener planListener, PlanClickListener planClickListener) {
        this.context = context;
        this.mealsList = mealsList;
        this.planListener = planListener;
        this.planClickListener = planClickListener;
    }

    public void updateMealList(Map<Integer, List<Meal>> mealsList){
        this.mealsList = mealsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_pager_page, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        holder.recyclerViewPlan.setLayoutManager(linearLayoutManager);
        holder.recyclerViewPlan.setAdapter(new PlanAdapter(context, mealsList.get(position), planClickListener));
    }

    @Override
    public int getItemCount() {
        Log.i("PlanPagerAdapter", "getItemCount: "+mealsList.size());
        return mealsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerViewPlan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewPlan = itemView.findViewById(R.id.recycler_view_plan);
        }
    }
}
