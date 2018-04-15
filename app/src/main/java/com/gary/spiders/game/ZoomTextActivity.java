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

public class ZoomTextActivity extends BaseGame {

    private float fontSize = 0.5f;
    int textResourceId;
    int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_text);

        final TextView timerTextView = (TextView) findViewById(R.id.zoomTextTimer);
        CountDownTimer timer = super.setupGameTimer(timerTextView, this, 15000);

        super.presentGameInfoPopup(this, "Click zoom button to zoom into the text. " +
                "If the progresss bar reaches the end before the time runs out you successfully complete the level", timer);

        GameResourceLoader resourceLoader = new GameResourceLoader(this);

        if(super.category == null){
            super.category = GameCategory.LINGUISTIC_HIGH;
        }
        textResourceId = resourceLoader.getResource(super.category);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);

        final TextView textView = (TextView) findViewById(R.id.zoom_text);
        textView.setText(getResources().getString(textResourceId));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_MM, fontSize);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                incrementFontSize();
                textView.setTextSize(TypedValue.COMPLEX_UNIT_MM, getSize());
                progressBar.incrementProgressBy(10);
                progress = progress + 10;
                if(progress > progressBar.getMax() + 1){
                    AlertDialog alertDialog = AlertUtility.createGameCompletedAlert(ZoomTextActivity.this);
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
