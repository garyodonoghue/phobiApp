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

import butterknife.BindView;

public class SharpenTextGame extends BaseGame {

    @BindView(R.id.sharpenTextTimer) TextView timerTextView;
    @BindView(R.id.sharpenText) Button button;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.sharpen_text) TextView textView;

    private int textResourceId;
    private int progress;
    private float blurFactor = 2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharpen_text);
        CountDownTimer timer = super.setupGameTimer(timerTextView, this, 15000);

        super.presentGameInfoPopup(this, "Click focus button to focus the text. " +
                "If the progresss bar reaches the end before the time runs out you successfully complete the level", timer);

        GameResourceLoader resourceLoader = new GameResourceLoader(this);

        if(super.category == null){
            super.category = GameCategory.LINGUISTIC_HIGH;
        }
        textResourceId = resourceLoader.getResource(super.category);
        progressBar.setMax(100);
        textView.setText(getResources().getString(textResourceId));
        sharpenText(textView);
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
