package com.example.mealplanner.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mealplanner.R;
import com.example.mealplanner.authentication.view.AuthenticationActivity;
import com.example.mealplanner.main.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.getInstance().signOut(); //remove this lineeeeeeeeeeeee
        FirebaseUser currentUser = mAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this
                        , (currentUser == null? AuthenticationActivity.class : MainActivity.class));
                startActivity(intent);
                finish();
            }
        }, 2000);

        /*public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Start the preload task
        new PreloadTask(this).execute();

        // Transition to the next screen after the preload task is complete
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, NextActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY_DURATION);
    }
}*/

        /*public class PreloadTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    public PreloadTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Preload necessary components here
        preloadImages();
        return null;
    }

    private void preloadImages() {
        // Preload images from local resources
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image1);
        // You can preload more images as needed
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // Preloading is complete
        // You can perform any additional tasks here, such as transitioning to the next screen
    }
}*/
    }
}