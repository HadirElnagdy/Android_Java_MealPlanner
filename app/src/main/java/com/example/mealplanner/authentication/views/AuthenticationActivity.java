package com.example.mealplanner.authentication.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.mealplanner.R;
import com.example.mealplanner.authentication.signin.view.SigninFragment;
import com.example.mealplanner.authentication.signup.view.SignupFragment;

public class AuthenticationActivity extends AppCompatActivity {
    OnboardingFragment onboardingFragment;
    SigninFragment signinFragment;
    SignupFragment signupFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        loadFragment(new SignupFragment());
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.authContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}