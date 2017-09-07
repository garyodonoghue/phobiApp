package com.gary.spiders.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gary.spiders.R;

public class MainMenu extends AppCompatActivity implements ISpiderExercise {

    SharedPreferences ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }

    @Override
    public void ratingClicked(View view) {

    }
}
