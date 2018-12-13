package com.gary.spiders.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.gary.spiders.R;
import com.gary.spiders.model.User;

import butterknife.BindView;

public class UpdateUsernameActivity extends AppCompatActivity {

    @BindView(R.id.usernameTxt) EditText usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_username);
        SharedPreferences preferences = getSharedPreferences("UserDetails", 0);
        MainMenuActivity.user = new User(preferences);
        usernameText.setText(MainMenuActivity.user.getName());
    }

    public void setUsername(View v){
        SharedPreferences userData = getSharedPreferences("UserDetails", 0);
        SharedPreferences.Editor editor = userData.edit();
        editor.putString("name", usernameText.getText().toString());
        editor.commit();
        finish();
    }
}
