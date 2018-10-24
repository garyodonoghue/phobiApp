package com.gary.spiders.game;

import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gary.spiders.R;
import com.gary.spiders.enums.GameCategory;
import com.gary.spiders.util.AlertUtility;

public class SharpenTextGame extends BaseGame {

    int textResourceId;
    int progress;
    float blurFactor = 2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharpen_text);

        final TextView timerTextView = (TextView) findViewById(R.id.sharpenTextTimer);
        CountDownTimer timer = super.setupGameTimer(timerTextView, this, 15000);

        super.presentGameInfoPopup(this, "Click focus button to focus the text. " +
                "If the progresss bar reaches the end before the time runs out you successfully complete the level", timer);

        GameResourceLoader resourceLoader = new GameResourceLoader(this);

        if(super.category == null){
            super.category = GameCategory.LINGUISTIC_HIGH;
        }
        textResourceId = resourceLoader.getResource(super.category);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);

        final TextView textView = (TextView) findViewById(R.id.sharpen_text);
        textView.setText(getResources().getString(textResourceId));

        sharpenText(textView);

        final Button button = (Button) findViewById(R.id.sharpenText);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sharpenText(textView);

                progressBar.incrementProgressBy(10);
                progress = progress + 10;
                if(progress > progressBar.getMax() + 1){
                    AlertDialog alertDialog = AlertUtility.createGameCompletedAlert(SharpenTextGame.this);
                    alertDialog.show();
                    stopTimer();
                }
            }
        });
    }

    private void sharpenText(TextView textView){
        textView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        textView.getPaint().setMaskFilter(null);

        float radius = textView.getTextSize() / this.blurFactor;
        BlurMaskFilter filter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL);
        textView.getPaint().setMaskFilter(filter);
        this.blurFactor += 0.6f;

        if(radius<11.0f){
            this.blurFactor+=2.5f;
        }
    }
}
