package com.example.mealplanner.authentication.signin.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mealplanner.R;
import com.example.mealplanner.authentication.signin.presenter.SigninPresenter;
import com.example.mealplanner.authentication.signin.presenter.SigninPresenterImpl;
import com.example.mealplanner.main.view.MainActivity;


public class SigninFragment extends Fragment implements SigninView {

    Button btnSignIn;
    EditText edtEmail;
    EditText edtPassword;
    SigninPresenter presenter;

    public SigninFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        edtEmail = view.findViewById(R.id.edt_loginEmail);
        edtPassword = view.findViewById(R.id.edt_loginPassword);
        btnSignIn = view.findViewById(R.id.btn_signin);
        presenter = new SigninPresenterImpl(this);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(edtEmail.getText());
                password = String.valueOf(edtPassword.getText());
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Please fill your info!", Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.signInWithEmail(email, password);
            }
        });

        return view;
    }

    @Override
    public void goToHome() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void showErrorMsg(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(errorMessage).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}