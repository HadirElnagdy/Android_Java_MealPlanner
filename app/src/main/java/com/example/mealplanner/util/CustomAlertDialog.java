package com.example.mealplanner.util;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

public class CustomAlertDialog {

    private Context context;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    public CustomAlertDialog(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }

    public CustomAlertDialog setTitle(String title) {
        builder.setTitle(title);
        return this;
    }

    public CustomAlertDialog setMessage(String message) {
        builder.setMessage(message);
        return this;
    }

    public CustomAlertDialog setPositiveButton(String text, final DialogInterface.OnClickListener listener) {
        builder.setPositiveButton(text, listener);
        return this;
    }

    public CustomAlertDialog setNegativeButton(String text, final DialogInterface.OnClickListener listener) {
        builder.setNegativeButton(text, listener);
        return this;
    }

    public CustomAlertDialog setNeutralButton(String text, final DialogInterface.OnClickListener listener) {
        builder.setNeutralButton(text, listener);
        return this;
    }

    public void show() {
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismiss() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    public static CustomAlertDialog createSimpleDialog(Context context, String message,
                                                       String positiveButtonName, String negativeButtonName,
                                                       DialogInterface.OnClickListener positiveButtonListener,
                                                       DialogInterface.OnClickListener negativeButtonListener) {
        return new CustomAlertDialog(context)
                .setMessage(message)
                .setPositiveButton(positiveButtonName, positiveButtonListener)
                .setNegativeButton(negativeButtonName, negativeButtonListener);
    }
}

