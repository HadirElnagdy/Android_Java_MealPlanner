package com.example.mealplanner.networkLayer;

import com.google.firebase.auth.FirebaseUser;

public interface AuthenticationListener {
    void onSuccess(FirebaseUser user);
    void onFailure(String errorMessage);

}
