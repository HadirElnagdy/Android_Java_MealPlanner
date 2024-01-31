package com.example.mealplanner.main.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.models.CategoryName;
import com.example.mealplanner.models.FilteredMeal;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context context;
    private List<CategoryName> categoryNames;
    private List<List<FilteredMeal>> meals;
    private MealInteractionListener listener;

    public HomeAdapter(Context context, List<CategoryName> categoryNames, List<List<FilteredMeal>> meals, MealInteractionListener listener) {
        this.context = context;
        this.categoryNames = categoryNames;
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cell, parent, false);
        return new ViewHolder(view);
    }
    public void setList(List<CategoryName> categoryNames, List<List<FilteredMeal>> meals){
        this.categoryNames = categoryNames;
        this.meals = meals;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String categoryName = categoryNames.get(position).getStrCategory();
        holder.txtCategoryName.setText(categoryName);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerViewMeals.setLayoutManager(layoutManager);
        CategoryMealsAdapter mealAdapter = new CategoryMealsAdapter(context, meals.get(position), listener);
        holder.recyclerViewMeals.setAdapter(mealAdapter);

    }

    @Override
    public int getItemCount() {
        return categoryNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCategoryName;
        private RecyclerView recyclerViewMeals;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.txt_category_name);
            recyclerViewMeals = itemView.findViewById(R.id.recycler_view_meals);
        }

    }

}
