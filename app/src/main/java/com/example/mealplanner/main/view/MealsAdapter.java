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
import com.example.mealplanner.main.view.MealInteractionListener;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.networkLayer.ImageLoader;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder>{

    private List<Meal> meals;
    private final MealInteractionListener listener;
    private final ImageLoader imgLoader;

    public MealsAdapter(Context context, List<Meal> meals, MealInteractionListener listener) {
        this.meals = meals;
        this.listener = listener;
        imgLoader = new ImageLoader(context);
    }

    @NonNull
    @Override
    public MealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meals_cell, parent, false);
        return new MealsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsAdapter.ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.txtMealName.setText(meal.getStrMeal());
        imgLoader.loadImage(meal.getStrMealThumb(), holder.imgMeal);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onOpenMealClick(meal.getIdMeal());
            }
        });

        holder.btnSaveMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchBtnImg(holder.btnSaveMeal);
                listener.onAddToSaved(meal.getIdMeal());

            }
        });

        holder.btnAddToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddToPlanClick(meal.getIdMeal());
            }
        });
    }

    public void setList(List<Meal> meals){
        this.meals = meals;
        // if (meals == null) Log.e("SearchAdapter", "setList: meals is nullllll");
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    private void switchBtnImg(ImageButton btn) {
        Integer currentImgResID = (Integer) btn.getTag();
        if (currentImgResID == null || currentImgResID == R.drawable.ic_save) {

            btn.setImageResource(R.drawable.ic_saved);

            btn.setTag(R.drawable.ic_saved);
        } else {

            btn.setImageResource(R.drawable.ic_save);

            btn.setTag(R.drawable.ic_save);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout layout;
        ImageView imgMeal;
        TextView txtMealName;
        Button btnAddToPlan;
        ImageButton btnSaveMeal;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.constraing_layout_meals_cell);
            txtMealName = itemView.findViewById(R.id.txt_random_meal);
            imgMeal = itemView.findViewById(R.id.img_random_meal);
            btnAddToPlan = itemView.findViewById(R.id.btn_add_plan_random);
            btnSaveMeal = itemView.findViewById(R.id.btn_save_random);
        }

        public ConstraintLayout getLayout() {
            return layout;
        }

    }

}

