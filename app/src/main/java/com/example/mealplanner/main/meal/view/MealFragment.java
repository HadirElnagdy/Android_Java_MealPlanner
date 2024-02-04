package com.example.mealplanner.main.meal.view;

import static java.util.Arrays.stream;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.main.home.view.HomeAdapter;
import com.example.mealplanner.models.Meal;
import com.example.mealplanner.networkLayer.ImageLoader;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MealFragment extends Fragment {

    ImageView imgMeal;
    TextView txtName;
    ImageButton btnSaveMeal;
    Button btnAddToPlan;
    RecyclerView recyclerViewIngredients;
    TextView txtInstructions;

    YouTubePlayerView youtubePlayerView;
    ImageLoader imageLoader;

    Meal meal;
    IngredientsAdapter adapter;

    public MealFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private String getId(String url) {
        String result = "";
        if (url != null && url.split("\\?v=").length > 1)
            result = url.split("\\?v=")[1];
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);
        initializeComponents(view);
        setupView();
        return view;
    }

    private void initializeComponents(View view){
        imgMeal = view.findViewById(R.id.img_meal);
        txtName = view.findViewById(R.id.txt_meal_name);
        btnSaveMeal = view.findViewById(R.id.btn_save_meal);
        btnAddToPlan = view.findViewById(R.id.btn_add_to_plan);
        recyclerViewIngredients = view.findViewById(R.id.recycler_view_ingredients);
        txtInstructions = view.findViewById(R.id.txt_instructions);
        youtubePlayerView = view.findViewById(R.id.ytPlayer);
        meal = MealFragmentArgs.fromBundle(getArguments()).getMeal();
        imageLoader = new ImageLoader(getContext());
        adapter = new IngredientsAdapter(getContext() , getIngredients(), getMeasures());
    }

    private List<String> getMeasures() {
        List<String> measures = new ArrayList<>();
        measures.add(meal.getStrMeasure1());
        measures.add(meal.getStrMeasure2());
        measures.add(meal.getStrMeasure3());
        measures.add(meal.getStrMeasure4());
        measures.add(meal.getStrMeasure5());
        measures.add(meal.getStrMeasure6());
        measures.add(meal.getStrMeasure7());
        measures.add(meal.getStrMeasure8());
        measures.add(meal.getStrMeasure9());
        measures.add(meal.getStrMeasure10());
        measures.add(meal.getStrMeasure11());
        measures.add(meal.getStrMeasure12());
        measures.add(meal.getStrMeasure13());
        measures.add(meal.getStrMeasure14());
        measures.add(meal.getStrMeasure15());
        measures.add(meal.getStrMeasure16());
        measures.add(meal.getStrMeasure17());
        measures.add(meal.getStrMeasure18());
        measures.add(meal.getStrMeasure19());
        measures.add(meal.getStrMeasure20());
        measures = measures.stream()
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toList());
        System.out.println("Measures: " + measures);
        return measures;
    }

    private List<String> getIngredients() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add(meal.getStrIngredient1());
        ingredients.add(meal.getStrIngredient2());
        ingredients.add(meal.getStrIngredient3());
        ingredients.add(meal.getStrIngredient4());
        ingredients.add(meal.getStrIngredient5());
        ingredients.add(meal.getStrIngredient6());
        ingredients.add(meal.getStrIngredient7());
        ingredients.add(meal.getStrIngredient8());
        ingredients.add(meal.getStrIngredient9());
        ingredients.add(meal.getStrIngredient10());
        ingredients.add(meal.getStrIngredient11());
        ingredients.add(meal.getStrIngredient12());
        ingredients.add(meal.getStrIngredient13());
        ingredients.add(meal.getStrIngredient14());
        ingredients.add(meal.getStrIngredient15());
        ingredients.add(meal.getStrIngredient16());
        ingredients.add(meal.getStrIngredient17());
        ingredients.add(meal.getStrIngredient18());
        ingredients.add(meal.getStrIngredient19());
        ingredients.add(meal.getStrIngredient20());
        ingredients = ingredients.stream()
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toList());
        System.out.println("Ingredients: " + ingredients);
        return ingredients;
    }

    private void setupView(){
        imageLoader.loadImage(meal.getStrMealThumb(), imgMeal);
        txtName.setText(meal.getStrMeal());
        btnAddToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnSaveMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setupRecyclerView();
        setupTxtInstructions();
        getLifecycle().addObserver(youtubePlayerView);
        youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = getId(meal.getStrYoutube());
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

    }
    private void setupRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewIngredients.setLayoutManager(linearLayoutManager);
        recyclerViewIngredients.setAdapter(adapter);
    }
    private void setupTxtInstructions(){
        String []instructions = meal.getStrInstructions().split("\\.");
        for(int i = 1 ; i<= instructions.length ; i++){
            txtInstructions.append("Step "+i+"\n"+instructions[i-1]+".\n");
            txtInstructions.append("\n----------------------------------\n\n");
        }
    }

}