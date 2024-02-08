package com.example.mealplanner.authentication.onboarding.presenter;

import com.example.mealplanner.models.UserManager;
import com.example.mealplanner.networkLayer.AuthenticationListener;
import com.google.firebase.auth.FirebaseUser;

public class OnboardingPresenterImpl implements OnboardingPresenter, AuthenticationListener {

    UserManager manager;
    @Override
    public void signUpWithGoogle() {
        manager = new UserManager();
        manager.signUpWithGoogle(this);
    }

    @Override
    public void signInWithGoogle() {

    }


    @Override
    public void onSuccess(FirebaseUser user) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }
}
