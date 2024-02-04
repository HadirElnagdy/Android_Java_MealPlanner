package com.example.mealplanner.main.meal.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.networkLayer.ImageLoader;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<String> ingredientNames;
    private List<String> ingredientMeasures;
    private Context context;
    private ImageLoader imageLoader;

    public IngredientsAdapter(Context context, List<String> ingredientNames, List<String> ingredientMeasures) {
        this.ingredientNames = ingredientNames;
        this.ingredientMeasures = ingredientMeasures;
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.img_title_cell, parent, false);
        return new IngredientsAdapter.ViewHolder(view);
    }
    public void setList(List<String> ingredientNames, List<String> ingredientMeasure){
        this.ingredientNames = ingredientNames;
        this.ingredientMeasures = ingredientMeasure;
    }
    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {
        imageLoader = new ImageLoader(context);
        String ingredientName = ingredientNames.get(position);
        String ingredientMeasure = ingredientMeasures.get(position);
        Log.i("TAG", "onBindViewHolder: measure " + ingredientMeasure);
        imageLoader.loadIngredientImage(ingredientName, holder.getImgIngredient());
        holder.txtIngredientTitle.setText(ingredientName);
        holder.txtIngredientSubtitle.setText(ingredientMeasure);
    }

    @Override
    public int getItemCount() {
        return ingredientNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtIngredientTitle;
        private TextView txtIngredientSubtitle;
        private CircularImageView imgIngredient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIngredientTitle = itemView.findViewById(R.id.txt_cell_title);
            txtIngredientSubtitle = itemView.findViewById(R.id.txt_cell_subtitle);
            imgIngredient = itemView.findViewById(R.id.img_cell_item);
        }

        public TextView getTxtIngredientTitle() {
            return txtIngredientTitle;
        }

        public TextView getTxtIngredientSubtitle() {
            return txtIngredientSubtitle;
        }

        public CircularImageView getImgIngredient() {
            return imgIngredient;
        }
    }

}