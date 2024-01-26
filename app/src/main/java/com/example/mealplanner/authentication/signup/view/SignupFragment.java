package com.example.mealplanner.authentication.signup.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mealplanner.R;
import com.example.mealplanner.authentication.signin.view.SigninFragment;
import com.example.mealplanner.authentication.signup.presenter.SignupPresenter;
import com.example.mealplanner.authentication.signup.presenter.SignupPresenterImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignupFragment extends Fragment implements SignupView{
    Button btnSignup;
    EditText edtEmail;
    EditText edtPassword;
    SignupPresenter presenter;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        edtEmail = view.findViewById(R.id.edt_regEmail);
        edtPassword = view.findViewById(R.id.edt_regPassword);
        btnSignup = view.findViewById(R.id.btn_signup);
        presenter = new SignupPresenterImpl(this);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(edtEmail.getText());
                password = String.valueOf(edtPassword.getText());
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(getContext(), "Please fill your info!", Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.signUpWithEmail(email, password);
            }
        });
        return view;
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.authContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void goToSignIn() {
        replaceFragment(new SigninFragment());
    }

    @Override
    public void showErrorMsg(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(errorMessage).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}