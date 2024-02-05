package com.example.mealplanner.main.plan.presenter;

import com.example.mealplanner.main.plan.view.PlanView;
import com.example.mealplanner.models.MealsRepository;
import com.google.firebase.auth.FirebaseUser;

public class PlanPresenterImpl implements PlanPresenter{
    PlanView view;
    MealsRepository repository;
//    CloudRepoInterface cloudRepository;

    public PlanPresenterImpl(PlanView view, MealsRepository repository/*, CloudRepoInterface cloudRepository*/) {
        this.view = view;
        this.repository = repository;
//        this.cloudRepository = cloudRepository;

      /*  if (cloudRepository.getCurrentUser() == null)
            view.showNotLoggedInMessage();
        else
            getAllPlans();*/
        //Log.i(TAG, "PlanPresenter: current user: " + cloudRepository.getCurrentUser());
    }

    @Override
    public void getPlanWithId(String dayID) {

    }

    @Override
    public void getAllPlans() {

    }

    @Override
    public void removePlan(String dayID, String mealID) {

    }

    @Override
    public FirebaseUser getCurrentUser() {
        return null;
    }
}
