package com.gary.spiders.game;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.gary.spiders.R;
import com.gary.spiders.enums.GameCategory;

/**
 * Created by Gary on 01/10/2017.
 */

public abstract class BaseGame extends AppCompatActivity {

    public GameCategory category;
    public Boolean initialAssessment;
    public int bonusPoints;
    public CountDownTimer countDownTimer;
    MediaPlayer mp;

    static boolean showDescriptions = true;

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        if(this.countDownTimer != null){
            this.countDownTimer.cancel();
        }

        if(mp != null){
            mp.release();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        String s = getIntent().getStringExtra("category");
        if(s != null) {
            this.category = GameCategory.valueOf(s);
        }
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

    public void presentGameInfoPopup(final BaseGame game, String gameDescription, final CountDownTimer timer){
        if(BaseGame.showDescriptions) {
            View checkBoxView = View.inflate(this, R.layout.checkbox, null);

            CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.disableDescriptionsCheckbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    BaseGame.showDescriptions = false;
                }
            });
            checkBox.setText("Don't show again");


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(game);

            alertDialogBuilder.setPositiveButton("Got it!",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Track progress
                            dialog.dismiss();
                            if (timer != null) {
                                timer.start();
                            }
                        }
                    });

            alertDialogBuilder.setView(checkBoxView);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setMessage(gameDescription);
            alertDialog.setCancelable(false);

            alertDialog.show();

            TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
            textView.setTextSize(25);
        }
        else {
            if (timer != null) {
                timer.start();
            }
        }
    }

    public void failedDueToIncorrectAttempts(final BaseGame game){
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
        alertDialog.setTitle("Too many incorrect attempts");
        alertDialog.setCancelable(true);
        alertDialog.getWindow().getAttributes().verticalMargin = 0.2f;

        alertDialog.show();
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
        alertDialog.getWindow().getAttributes().verticalMargin = 0.2f;

        alertDialog.show();
    }

    public void setBonusPoints(int bonusPoints){
        this.bonusPoints = bonusPoints;
    }

    public CountDownTimer setupGameTimer(final TextView textView, final BaseGame game, final long time){
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                long remainingTime = millisUntilFinished / 1000;
                textView.setText("Time Remaining: "+ remainingTime);

                if(remainingTime == 10){
                    mp = MediaPlayer.create(getBaseContext(), R.raw.timingout);
                    mp.start();
                }
            }
            public void onFinish() {
                textView.setText("Times Up!");
                MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.fail1);
                mp.start();
                levelFailed(game);
            }
        };

        return countDownTimer;

    }

    public void stopTimer(){
        this.countDownTimer.cancel();
        if(mp != null){
            mp.stop();
        }
    }
}
