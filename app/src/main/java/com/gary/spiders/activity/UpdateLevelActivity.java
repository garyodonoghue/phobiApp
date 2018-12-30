package com.gary.spiders.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.gary.spiders.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateLevelActivity extends AppCompatActivity {

    @BindView(R.id.userLevel) EditText userLvlText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_level);

        ButterKnife.bind(this);
    }

    public void setUserLevel(View v){
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
