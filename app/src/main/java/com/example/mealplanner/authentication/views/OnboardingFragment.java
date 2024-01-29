package com.example.mealplanner.authentication.views;

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
import com.example.mealplanner.main.view.MainActivity;

public class OnboardingFragment extends Fragment {
    Button btnSignUpMail;

    TextView txtSignInMail;
    TextView txtSkip;

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
        txtSignInMail = view.findViewById(R.id.txt_login);
        txtSkip = view.findViewById(R.id.txt_skip);
        btnSignUpMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_onboardingFragment_to_signupFragment);
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
}