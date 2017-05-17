package com.gary.spiders.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.gary.spiders.R;

/**
 * Created by Gary on 16/05/2017.
 */

public class AlertUtility {

    public static AlertDialog createAlert(final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setPositiveButton("Proceed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
        alertDialogBuilder.setNegativeButton("Stay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.review_alert, null);
        alertDialogBuilder.setView(dialogView);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setContentView(R.layout.review_alert);
        alertDialog.setTitle("Well done!");
        alertDialog.setCancelable(true);

        return alertDialog;
    }
}
