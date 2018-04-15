package com.gary.spiders.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.gary.spiders.R;
import com.gary.spiders.game.BaseGame;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Gary on 16/05/2017.
 */

public class AlertUtility {

    // TODO Can call this every so often throughout the game to determine a user's comfort levels
    public static AlertDialog createRatingAlert(final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setPositiveButton("Proceed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Track progress
                        dialog.dismiss();
                        activity.finish();
                    }
                });
        alertDialogBuilder.setNegativeButton("Try Again",
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

    public static AlertDialog createGameCompletedAlert(final BaseGame game) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(game);

        alertDialogBuilder.setPositiveButton("Proceed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Track progress
                        dialog.dismiss();

                        // Mark the level as not completed
                        Intent data = new Intent();
                        data.putExtra("completed", "true");
                        data.putExtra("category", game.category.toString());
                        data.putExtra("initialAssessment", game.initialAssessment.toString());
                        data.putExtra("bonusPoints", String.valueOf(game.bonusPoints));
                        game.setResult(RESULT_OK, data);
                        game.finish();

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Well done!");
        alertDialog.setCancelable(false);
        alertDialog.getWindow().getAttributes().verticalMargin = 0.2f;
        return alertDialog;
    }

    public static AlertDialog createInfoAlertDialog(final Activity activity, final String title, final String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        return alertDialog;
    }
}
