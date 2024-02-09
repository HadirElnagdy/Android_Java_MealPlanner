package com.example.mealplanner.main.plan.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mealplanner.R;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.networkLayer.ImageLoader;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder>{

    private List<Meal> meals;
    private final PlanClickListener listener;
    private final ImageLoader imgLoader;

    public PlanAdapter(Context context, List<Meal> meals, PlanClickListener listener) {
        this.meals = meals;
        this.listener = listener;
        imgLoader = new ImageLoader(context);
    }

    @NonNull
    @Override
    public PlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meals_cell, parent, false);
        return new PlanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.txtMealName.setText(meal.getStrMeal());
        imgLoader.loadImage(meal.getStrMealThumb(), holder.imgMeal);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOpenMealClicked(null, meal);
            }
        });

        holder.btnSaveMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSaveClicked(null, meal);

            }
        });

        holder.btnDeleteFromPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeletePlanClicked(null, meal);
            }
        });
    }

    public void setList(List<Meal> meals){
        this.meals = meals;
        // if (meals == null) Log.e("SearchAdapter", "setList: meals is nullllll");
    }
    public void resetList() {
        meals.clear();
    }
    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void addToList(Meal meal) {
        boolean exist = false;
        for (Meal m : meals) {
            if (m.getIdMeal().equals(meal.getIdMeal())) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            meals.add(meal);
            notifyDataSetChanged();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout layout;
        ImageView imgMeal;
        TextView txtMealName;
        Button btnDeleteFromPlan;
        Button btnSaveMeal;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.constraing_layout_meals_cell);
            txtMealName = itemView.findViewById(R.id.txt_random_meal);
            imgMeal = itemView.findViewById(R.id.img_random_meal);
            btnDeleteFromPlan = itemView.findViewById(R.id.btn_add_plan);
            btnSaveMeal = itemView.findViewById(R.id.btn_save_random);
            btnDeleteFromPlan.setText("Delete Meal");
        }

        public ConstraintLayout getLayout() {
            return layout;
        }

    }

}
