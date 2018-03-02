package com.gary.spiders.game;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gary.spiders.enums.GameCategory;

/**
 * Created by Gary on 01/10/2017.
 */

public abstract class BaseGame extends AppCompatActivity {

    public GameCategory category;
    public Boolean initialAssessment;
    public int bonusPoints;
    public CountDownTimer countDownTimer;

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        if(this.countDownTimer != null){
            this.countDownTimer.cancel();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String s = getIntent().getStringExtra("category");
        this.category = GameCategory.valueOf(s);

        boolean initialAssess = getIntent().getBooleanExtra("initialAssessment", false);
        this.initialAssessment = Boolean.valueOf(initialAssess);
    }

    public void giveUp(View v){
        if(this.countDownTimer != null){
            this.countDownTimer.cancel();
        }

        // Mark the level as not completed - user gave up
        Intent data = new Intent();
        data.putExtra("completed", "false");
        data.putExtra("category", this.category.toString());
        data.putExtra("initialAssessment", this.initialAssessment.toString());
        data.putExtra("bonusPoints", String.valueOf(this.bonusPoints));
        data.putExtra("tryAgain", "false");
        setResult(RESULT_OK, data);
        this.finish();
    }

    public void levelFailed(final BaseGame game){
        // Mark the level as not failed - user ran out of time
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(game);
        final int bonusPoints = this.bonusPoints;

        alertDialogBuilder.setPositiveButton("Let's try again!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Track progress
                        dialog.dismiss();

                        Intent data = new Intent();
                        data.putExtra("completed", "false");
                        data.putExtra("category", category.toString());
                        data.putExtra("initialAssessment", initialAssessment.toString());
                        data.putExtra("bonusPoints", String.valueOf(bonusPoints));
                        data.putExtra("tryAgain", "true");
                        setResult(RESULT_OK, data);
                        game.finish();

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Time up!");
        alertDialog.setCancelable(true);

        alertDialog.show();
    }

    public void setBonusPoints(int bonusPoints){
        this.bonusPoints = bonusPoints;
    }

    public void setupGameTimer(final TextView textView, final BaseGame game){
        countDownTimer = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                textView.setText("Time Remaining: "+millisUntilFinished / 1000);
            }
            public void onFinish() {
                textView.setText("Times Up!");
                levelFailed(game);
            }
        }.start();
    }

    public void stopTimer(){
        this.countDownTimer.cancel();
    }
}
