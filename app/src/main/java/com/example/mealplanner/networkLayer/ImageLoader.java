package com.example.mealplanner.networkLayer;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Map;

public class ImageLoader {

    private Context context;

    public ImageLoader(Context context) {
        this.context = context;
    }

    public void loadImage(String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .into(imageView);
    }

    public void loadIngredientImage(String ingredientName, ImageView imageView){
        Glide.with(context)
                .load(buildUrl(ingredientName))
                .centerCrop()
                .into(imageView);
        Log.i("TAG", "loadIngredientImage: "+buildUrl(ingredientName));
    }

    private String buildUrl(String ingredientName) {
        StringBuilder urlBuilder = new StringBuilder(Constants.BASE_IMG_URL);
        urlBuilder.append(ingredientName)
                .append("-Small.png");
        return urlBuilder.toString();
    }


}
