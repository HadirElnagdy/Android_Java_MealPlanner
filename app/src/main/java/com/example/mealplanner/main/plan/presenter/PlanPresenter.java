package com.example.mealplanner.main.plan.presenter;

import com.google.firebase.auth.FirebaseUser;

public interface PlanPresenter {
    void getPlanWithId(String dayID);

    void getAllPlans();

    void removePlan(String dayID, String mealID);

    FirebaseUser getCurrentUser();
}
