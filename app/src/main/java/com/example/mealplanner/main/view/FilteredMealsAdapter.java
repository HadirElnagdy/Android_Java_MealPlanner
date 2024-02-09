package com.example.mealplanner.main.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.models.FilteredMeal;
import com.example.mealplanner.networkLayer.ImageLoader;

import java.util.List;

public class FilteredMealsAdapter extends RecyclerView.Adapter<FilteredMealsAdapter.ViewHolder>{

    private List<FilteredMeal> meals;
    private final MealInteractionListener listener;
    private final ImageLoader imgLoader;

    public FilteredMealsAdapter(Context context, List<FilteredMeal> meals, MealInteractionListener listener) {
        this.meals = meals;
        this.listener = listener;
        imgLoader = new ImageLoader(context);
    }

    @NonNull
    @Override
    public FilteredMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meals_cell, parent, false);
        return new FilteredMealsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilteredMealsAdapter.ViewHolder holder, int position) {
        FilteredMeal meal = meals.get(position);
        holder.txtMealName.setText(meal.getStrMeal());
        imgLoader.loadImage(meal.getStrMealThumb(), holder.imgMeal);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOpenMealClicked(meal.getIDMeal(), null);
            }
        });

        holder.btnSaveMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSaveClicked(meal.getIDMeal(), null);

            }
        });

        holder.btnAddToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddToPlanClicked(meal.getIDMeal(), null);
            }
        });
    }

    public void setList(List<FilteredMeal> meals){
        this.meals = meals;

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout layout;
        ImageView imgMeal;
        TextView txtMealName;
        Button btnAddToPlan;
        Button btnSaveMeal;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.constraing_layout_meals_cell);
            txtMealName = itemView.findViewById(R.id.txt_random_meal);
            imgMeal = itemView.findViewById(R.id.img_random_meal);
            btnAddToPlan = itemView.findViewById(R.id.btn_add_plan);
            btnSaveMeal = itemView.findViewById(R.id.btn_save_random);
        }

        public ConstraintLayout getLayout() {
            return layout;
        }

    }

}
