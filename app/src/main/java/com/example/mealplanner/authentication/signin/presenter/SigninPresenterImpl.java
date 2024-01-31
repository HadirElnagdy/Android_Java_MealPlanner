package com.example.mealplanner.authentication.signin.presenter;

import com.example.mealplanner.authentication.signin.view.SigninFragment;
import com.example.mealplanner.authentication.signin.view.SigninView;
import com.example.mealplanner.models.AuthenticationManager;
import com.example.mealplanner.networkLayer.AuthenticationListener;
import com.google.firebase.auth.FirebaseUser;

public class SigninPresenterImpl implements SigninPresenter, AuthenticationListener {
    SigninView view;
    AuthenticationManager manager;
    public SigninPresenterImpl(SigninView view){
        this.view = view;
        manager = new AuthenticationManager();
    }
    @Override
    public void signInWithEmail(String email, String password) {
        manager.signInWithEmail(email, password, this);
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        view.goToHome();
    }

    @Override
    public void onFailure(String errorMessage) {
        view.showErrorMsg(errorMessage);
    }
}
