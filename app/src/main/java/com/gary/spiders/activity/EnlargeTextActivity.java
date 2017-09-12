package com.gary.spiders.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gary.spiders.R;
import com.gary.spiders.util.AlertUtility;
import com.gary.spiders.util.EpochUtil;

public class EnlargeTextActivity extends AppCompatActivity implements ISpiderExercise {

    private float fontSize = 0.5f;

    SharedPreferences ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge_text);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);

        final TextView spiderText = (TextView) findViewById(R.id.spider_text);
        spiderText.setTextSize(TypedValue.COMPLEX_UNIT_MM, fontSize);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                incrementFontSize();
                spiderText.setTextSize(TypedValue.COMPLEX_UNIT_MM, getSize());

                progressBar.incrementProgressBy(10);
                if(progressBar.getProgress() == progressBar.getMax()){
                    AlertDialog alertDialog = AlertUtility.createAlert(EnlargeTextActivity.this);
                    alertDialog.show();
                }
            }
        });
    }

    private float getSize(){
        return this.fontSize;
    }

    private void incrementFontSize(){
        this.fontSize = this.fontSize + 1.0f;
    }

    @Override
    public void ratingClicked(View view) {
        RadioButton radioButton = (RadioButton) view;

        ratings = getSharedPreferences("Ratings", 0);
        SharedPreferences.Editor editor = ratings.edit();
        editor.putString(this.getLocalClassName() + "_" + EpochUtil.getEpochTime(), radioButton.getText().toString());

        editor.commit();
    }
}
