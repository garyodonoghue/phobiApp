package com.gary.spiders.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gary.spiders.R;
import com.gary.spiders.game.Game;
import com.gary.spiders.game.GameGenerator;

public class MainMenuActivity extends AppCompatActivity {

    public void startInitialAssessment(View view) {
        // for the initial assessment we want to test the user with the 'HIGH' categories of each type,
        // so LINGUISTIC_HIGH, CARTOON_HIGH, DRAWINGS_BW_HIGH etc, so need to pass the level to retrieve these,
        // e.g. initially set to 5 (LINGUISTIC_HIGH), then 15 (CARTOON_HIGH), then 30 (DRAWINGS_BW_HIGH)
        Game game = GameGenerator.generateGame(5, true);
        Intent myIntent = new Intent(MainMenuActivity.this, game.getClass());
        MainMenuActivity.this.startActivity(myIntent);
    }

    public void presentChooseGameScreen(View view) {
        Intent myIntent = new Intent(MainMenuActivity.this, ChooseGameActivity.class);
        MainMenuActivity.this.startActivity(myIntent);
    }

    public void showProgress(View view) {
        Intent myIntent = new Intent(MainMenuActivity.this, MetricsActivity.class);
        MainMenuActivity.this.startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }
}
