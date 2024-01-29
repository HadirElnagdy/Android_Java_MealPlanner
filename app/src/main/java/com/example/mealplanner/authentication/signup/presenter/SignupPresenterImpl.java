package com.example.mealplanner.authentication.signup.presenter;

import android.util.Log;

import com.example.mealplanner.authentication.signup.view.SignupView;
import com.example.mealplanner.models.AuthenticationManager;
import com.example.mealplanner.networkLayer.AuthenticationListener;
import com.google.firebase.auth.FirebaseUser;

public class SignupPresenterImpl implements SignupPresenter, AuthenticationListener {

    SignupView view;
    AuthenticationManager manager;
    public SignupPresenterImpl(SignupView view){
        this.view = view;
        manager = new AuthenticationManager();
    }

    @Override
    public void signUpWithEmail(String name, String email, String password) {
        manager.signUpWithEmail(name, email, password, this);
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        Log.i("TAG", "onSuccess: "+user.getDisplayName());
        view.goToSignIn();
    }

    @Override
    public void onFailure(String errorMessage) {
        view.showErrorMsg(errorMessage);
    }
}
