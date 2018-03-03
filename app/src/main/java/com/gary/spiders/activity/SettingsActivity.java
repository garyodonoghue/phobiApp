package com.gary.spiders.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gary.spiders.R;
import com.gary.spiders.util.ReminderScreen;

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

    public void configureReminders(View v){
        Intent myIntent = new Intent(SettingsActivity.this, ReminderScreen.class);
        SettingsActivity.this.startActivity(myIntent);
    }
}
