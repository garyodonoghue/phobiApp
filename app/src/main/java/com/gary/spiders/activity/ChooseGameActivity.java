package com.gary.spiders.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gary.spiders.R;
import com.gary.spiders.game.PlayStopVideoGame;

public class ChooseGameActivity extends AppCompatActivity {

    public void videoClicked(View view) {
        Intent myIntent = new Intent(ChooseGameActivity.this, PlayStopVideoGame.class);
        ChooseGameActivity.this.startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);
    }
}
