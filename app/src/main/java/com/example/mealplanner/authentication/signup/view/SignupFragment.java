package com.example.mealplanner.authentication.signup.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.mealplanner.R;
import com.example.mealplanner.authentication.signin.view.SigninFragment;
import com.example.mealplanner.authentication.signin.view.SigninFragmentDirections;
import com.example.mealplanner.authentication.signup.presenter.SignupPresenter;
import com.example.mealplanner.authentication.signup.presenter.SignupPresenterImpl;
import com.example.mealplanner.main.view.MainActivity;
import com.example.mealplanner.util.CustomAlertDialog;
import com.google.android.material.textfield.TextInputLayout;


public class SignupFragment extends Fragment implements SignupView{
    Button btnSignup;
    EditText edtEmail;
    EditText edtPassword;
    EditText edtName;
    TextView txtLogin;
    SignupPresenter presenter;
    View view;

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
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        TextInputLayout nameInputLayout = view.findViewById(R.id.edt_name);
        TextInputLayout emailInputLayout = view.findViewById(R.id.edt_register_email);
        TextInputLayout passwordInputLayout = view.findViewById(R.id.edt_register_password);
        edtName = nameInputLayout.getEditText();
        edtEmail = emailInputLayout.getEditText();
        edtPassword = passwordInputLayout.getEditText();
        btnSignup = view.findViewById(R.id.btn_signup);
        txtLogin = view.findViewById(R.id.txt_login_signup);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(SignupFragmentDirections.actionSignupFragmentToSigninFragment());
            }
        });
        presenter = new SignupPresenterImpl(this);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, name;
                email = String.valueOf(edtEmail.getText());
                password = String.valueOf(edtPassword.getText());
                name = String.valueOf(edtName.getText());
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(getContext(), "Please fill your info!", Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.signUpWithEmail(name, email, password);
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
        CustomAlertDialog.showSimpleAlert(getContext(), "An error occured", errorMessage);
    }
}