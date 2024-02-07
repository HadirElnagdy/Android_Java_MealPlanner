package com.example.mealplanner.main.view;

import android.content.Context;
import android.telephony.BarringInfo;
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
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.networkLayer.ImageLoader;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder>{

    private List<Meal> meals;
    private final MealInteractionListener listener;
    private final ImageLoader imgLoader;
    private String txtBtnSave;
    MealsAdapter.ViewHolder holder;

    public MealsAdapter(Context context, List<Meal> meals, MealInteractionListener listener) {
        this.meals = meals;
        this.listener = listener;
        this.txtBtnSave = "Save";
        imgLoader = new ImageLoader(context);
    }
    public void setTxtBtnSave(String txt){
        txtBtnSave = txt;
    }

    @NonNull
    @Override
    public MealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meals_cell, parent, false);
        holder = new MealsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealsAdapter.ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.txtMealName.setText(meal.getStrMeal());
        holder.btnSaveMeal.setText(txtBtnSave);
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

        holder.btnAddToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddToPlanClicked(null, meal);
            }
        });

    }


    public void setList(List<Meal> meals){
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

