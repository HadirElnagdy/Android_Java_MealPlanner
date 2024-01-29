package com.example.mealplanner.models;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mealplanner.authentication.signin.view.SigninFragment;
import com.example.mealplanner.networkLayer.AuthenticationListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class AuthenticationManager {
    private FirebaseAuth mAuth;

    public AuthenticationManager() {
        mAuth = FirebaseAuth.getInstance();
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

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

}
