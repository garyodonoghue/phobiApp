package com.gary.spiders.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.gary.spiders.R;
import com.gary.spiders.enums.GameCategory;

/**
 * Created by Gary on 01/10/2017.
 */

public abstract class BaseGame extends AppCompatActivity {

    public GameCategory category;
    public boolean initialAssessment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String s = getIntent().getStringExtra("category");
        this.category = GameCategory.valueOf(s);
        this.initialAssessment = getIntent().getBooleanExtra("initialAssessment", false);

        if(initialAssessment){
            ImageButton giveUpBtn = (ImageButton) findViewById(R.id.giveUpButton);
            giveUpBtn.setVisibility(View.VISIBLE);
        }
    }

    public void giveUp(View v){
        // Mark the level as not completed - user gave up
        Intent data = new Intent();
        data.putExtra("completed", "false");
        data.putExtra("category", this.category.toString());
        setResult(RESULT_OK, data);
        this.finish();
    }
}
