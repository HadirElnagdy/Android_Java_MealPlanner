package com.example.mealplanner.authentication.onboarding.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.authentication.onboarding.presenter.OnboardingPresenter;
import com.example.mealplanner.authentication.onboarding.presenter.OnboardingPresenterImpl;
import com.example.mealplanner.main.view.MainActivity;

public class OnboardingFragment extends Fragment implements OnboardingView{
    Button btnSignUpMail;
    Button btnGoogle;
    Button btnFacebook;
    Button btnMicrosoft;

    TextView txtSignInMail;
    TextView txtSkip;
    OnboardingPresenter presenter;

    public OnboardingFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);
        btnSignUpMail = view.findViewById(R.id.btn_sign_up_with_email);
        btnGoogle = view.findViewById(R.id.btn_google);
        btnFacebook = view.findViewById(R.id.btn_facebook);
        btnMicrosoft = view.findViewById(R.id.btn_microsoft);
        txtSignInMail = view.findViewById(R.id.txt_login);
        txtSkip = view.findViewById(R.id.txt_skip);
        presenter = new OnboardingPresenterImpl();
        btnSignUpMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_onboardingFragment_to_signupFragment);
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.signUpWithGoogle();
            }
        });

        txtSignInMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_onboardingFragment_to_signinFragment);
            }
        });

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
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

    }
}