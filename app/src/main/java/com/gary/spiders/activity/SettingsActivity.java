package com.gary.spiders.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gary.spiders.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void updateAvatar(View v){
        Intent myIntent = new Intent(SettingsActivity.this, UpdateAvatarActivity.class);
        SettingsActivity.this.startActivity(myIntent);
    }

    public void updateUsername(View v){
        Intent myIntent = new Intent(SettingsActivity.this, UpdateUsernameActivity.class);
        SettingsActivity.this.startActivity(myIntent);
    }
}
