package com.example.mealplanner.main.plan.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;

import java.util.List;

public class PlanPagerAdapter extends RecyclerView.Adapter<PlanPagerAdapter.ViewHolder> {
    private List<PlanAdapter> adapterList;
    private Context context;
    private PlanListener planListener;

    public PlanPagerAdapter(Context context, List<PlanAdapter> adapterList, PlanListener planListener) {
        this.context = context;
        this.adapterList = adapterList;
        this.planListener = planListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_pager_page, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        holder.recyclerViewPlan.setLayoutManager(gridLayoutManager);
        holder.recyclerViewPlan.setAdapter(adapterList.get(position));
        //Log.i(TAG, "onBindViewHolder: adapter list size: " + adapterList.get(position).getItemCount());
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerViewPlan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerViewPlan = itemView.findViewById(R.id.recycler_view_plan);
        }
    }
}
