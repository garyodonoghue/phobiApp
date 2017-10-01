package com.gary.spiders.game;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gary.spiders.R;
import com.gary.spiders.util.AlertUtility;

public class ZoomTextActivity extends AppCompatActivity implements Game {

    private float fontSize = 0.5f;
    int textResourceId;

    @Override
    public void setupGame(GameGenerator.Category category, boolean initialAssessment) {
        GameResourceLoader resourceLoader = new GameResourceLoader(this);
        textResourceId = resourceLoader.getResource(category);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_text);

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
                    AlertDialog alertDialog = AlertUtility.createAlert(ZoomTextActivity.this);
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
}
