package com.gary.spiders.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gary.spiders.R;

public class MainMenuActivity extends AppCompatActivity implements ISpiderExercise {

    SharedPreferences ratings;

    public void presentChooseGameScreen(View view) {
        Intent myIntent = new Intent(MainMenuActivity.this, ChooseGameActivity.class);
        MainMenuActivity.this.startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }

    @Override
    public void ratingClicked(View view) {

    }
}
