package com.gary.spiders.game;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gary.spiders.R;
import com.gary.spiders.enums.GameCategory;
import com.gary.spiders.util.AlertUtility;

import butterknife.BindView;

public class ZoomTextGame extends BaseGame {

    @BindView(R.id.zoomTextTimer) TextView timerTextView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.zoom_text) TextView textView;
    @BindView(R.id.button) Button button;

    private float fontSize = 0.5f;
    private int textResourceId;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_text);

        CountDownTimer timer = super.setupGameTimer(timerTextView, this, 15000);

        super.presentGameInfoPopup(this, "Click zoom button to zoom into the text. " +
                "If the progresss bar reaches the end before the time runs out you successfully complete the level", timer);

        GameResourceLoader resourceLoader = new GameResourceLoader(this);

        if(super.category == null){
            super.category = GameCategory.LINGUISTIC_HIGH;
        }

        textResourceId = resourceLoader.getResource(super.category);
        progressBar.setMax(100);
        textView.setText(getResources().getString(textResourceId));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_MM, fontSize);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                incrementFontSize();
                textView.setTextSize(TypedValue.COMPLEX_UNIT_MM, getSize());
                progressBar.incrementProgressBy(10);
                progress = progress + 10;
                if(progress > progressBar.getMax() + 1){
                    AlertDialog alertDialog = AlertUtility.createGameCompletedAlert(ZoomTextGame.this);
                    alertDialog.show();
                    stopTimer();
                }
            }
        });
    }

    public void stopTimer(){ super.stopTimer(); }

    private float getSize(){
        return this.fontSize;
    }

    private void incrementFontSize(){
        this.fontSize = this.fontSize + 1.0f;
    }

    public void giveUp(View v){
        super.giveUp(v);
    }
}
