package com.gary.spiders.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gary.spiders.R;
import com.gary.spiders.util.AlertUtility;
import com.gary.spiders.util.ReminderScreen;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void updateAvatar(View v) {
        Intent myIntent = new Intent(SettingsActivity.this, UpdateAvatarActivity.class);
        SettingsActivity.this.startActivity(myIntent);
    }

    public void updateUsername(View v) {
        Intent myIntent = new Intent(SettingsActivity.this, UpdateUsernameActivity.class);
        SettingsActivity.this.startActivity(myIntent);
    }

    public void clearUserData(View v){
        SharedPreferences userData = this.getSharedPreferences("UserDetails", 0);
        SharedPreferences.Editor userDataEditor = userData.edit().clear();
        userDataEditor.commit();

        SharedPreferences fsqRatings = this.getSharedPreferences("Questionnaire", 0);
        SharedPreferences.Editor ratingsEditor = fsqRatings.edit().clear();
        ratingsEditor.commit();

        SharedPreferences gameRatings = this.getSharedPreferences("Ratings", 0);
        SharedPreferences.Editor gameRatingsEditor = gameRatings.edit().clear();
        gameRatingsEditor.commit();

        SharedPreferences progress = this.getSharedPreferences("Progress", 0);
        SharedPreferences.Editor progressEditor = progress.edit().clear();
        progressEditor.commit();

        AlertDialog alert = AlertUtility.createInfoAlertDialog(this, "Info",
                "All user data has been cleared from the device");
        alert.show();
    }

    public void setUserLevel(View v){
        Intent myIntent = new Intent(SettingsActivity.this, UpdateLevelActivity.class);
        SettingsActivity.this.startActivity(myIntent);
    }

    public void configureReminders(View v) {
        Intent myIntent = new Intent(SettingsActivity.this, ReminderScreen.class);
        SettingsActivity.this.startActivity(myIntent);
    }
}
