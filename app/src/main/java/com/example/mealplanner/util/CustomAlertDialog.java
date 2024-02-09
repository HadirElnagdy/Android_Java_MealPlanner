package com.example.mealplanner.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;

import com.example.mealplanner.authentication.view.AuthenticationActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class CustomAlertDialog {

    private Context context;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    public CustomAlertDialog(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }

    public static void showCustomDialog(Context context, String title, String message,
                                               String positiveButtonText, String negativeButtonText,
                                               DialogInterface.OnClickListener positiveClickListener,
                                               DialogInterface.OnClickListener negativeClickListener,
                                               DialogInterface.OnDismissListener dismissListener) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, positiveClickListener)
                .setNegativeButton(negativeButtonText, negativeClickListener)
                .setOnDismissListener(dismissListener)
                .show();
    }

    public static void showLoginDialog(Context context, View view){
        showCustomDialog(context, "Login First", "Some features available only when you are logged in, Please login first",
                "Ok", "Cancel",
                (dialog, which) -> {
                    Intent intent = new Intent(context, AuthenticationActivity.class);
                    context.startActivity(intent);
                },
                (dialog, which) -> Navigation.findNavController(view).navigateUp(),
                dialogInterface -> Navigation.findNavController(view).navigateUp());
    }

    public static void showSimpleAlert(Context context, String title, String message) {
        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null) // No action
                .show();
    }
}

