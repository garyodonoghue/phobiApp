package com.gary.spiders.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.gary.spiders.R;

public class UpdateLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_level);
    }

    public void setUserLevel(View v){
        EditText userLvlText = (EditText) findViewById(R.id.userLevel);
        String userLevel = userLvlText.getText().toString();

        SharedPreferences userData = this.getSharedPreferences("UserDetails", 0);
        SharedPreferences.Editor editor = userData.edit();
        editor.putString("level", userLevel);
        editor.commit();
        MainMenuActivity.user.setLevel(Integer.parseInt(userLevel));
        Log.d("UpdateUserLevel", "level="+userLevel);
        finish();
    }
}
