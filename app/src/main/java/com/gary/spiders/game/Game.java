package com.gary.spiders.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Gary on 01/10/2017.
 */

public abstract class Game extends AppCompatActivity {

    public GameGenerator.Category category;
    public boolean initialAssessment;

    public void giveUp(View v){
        // Mark the level as not completed - user gave up
        Intent data = new Intent();
        data.putExtra("completed", "false");
        data.putExtra("category", this.category.toString());
        setResult(RESULT_OK, data);
        this.finish();
    }
}
