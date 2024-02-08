package com.example.mealplanner.models;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mealplanner.networkLayer.AuthenticationListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class UserManager {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private GoogleSignInClient mGoogleSignInClient;

    public UserManager() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

    }

    public void signInWithEmail(String email, String password, AuthenticationListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            listener.onSuccess(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            listener.onFailure(task.getException().getMessage());

                        }
                    }
                });
    }

    public void signUpWithEmail(String name, String email, String password, AuthenticationListener listener) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    setUserName(user, name, listener);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                    listener.onFailure(task.getException().getMessage());
                }
            }
        });

    }
    public  void setUserName(FirebaseUser user, String name, AuthenticationListener listener){
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest
                .Builder()
                .setDisplayName(name)
                .build();
        user.updateProfile(changeRequest).addOnCompleteListener(profileTask -> {
            if (profileTask.isSuccessful()) {
                Log.i("TAG", "setUserName: success");
                listener.onSuccess(user);
            } else {
                Log.e("TAG", "setUserName: "+ profileTask.getException().getMessage());
                listener.onSuccess(user);
            }
        });
    }
    public String getCurrentUserEmail(){
        return mAuth.getCurrentUser().getEmail();
    }
    public FirebaseUser getUser(){
        return mAuth.getCurrentUser();
    }
    public void signUpWithGoogle(AuthenticationListener listener){

    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

}
