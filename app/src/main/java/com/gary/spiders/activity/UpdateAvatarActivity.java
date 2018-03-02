package com.gary.spiders.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.gary.spiders.R;

public class UpdateAvatarActivity extends AppCompatActivity {

    ImageView currentAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_avatar);

        GridLayout gv = (GridLayout) findViewById(R.id.avatars_grid);
        gv.setBackgroundColor(Color.WHITE);

        gv.setPadding(2, 2, 2,    2);

        // Set the current user's avatar
        ImageView selectedAvatar = (ImageView) findViewById(R.id.selectedAvatar);

        String avatarResId = MainMenuActivity.user.getAvatarResource();
        if(!avatarResId.isEmpty()){
            int resId = getResources().getIdentifier(avatarResId, "mipmap", this.getPackageName());
            selectedAvatar.setImageResource(resId);
        }
    }

    public void tileClicked(View v){
        String imageTag = (String) v.getTag();

        ImageView imageView = (ImageView) findViewById(R.id.selectedAvatar);
        imageView.setTag(imageTag);

        int resId = getResources().getIdentifier(imageTag, "mipmap", this.getPackageName());
        imageView.setBackgroundResource(resId);
    }

    public void setAvatar(View v){
        ImageView selectedAvatar = (ImageView) findViewById(R.id.selectedAvatar);
        String avatarImageId = (String) selectedAvatar.getTag();

        SharedPreferences userData = getSharedPreferences("UserDetails", 0);
        SharedPreferences.Editor editor = userData.edit();
        editor.putString("avatarResource", ""+avatarImageId);
        editor.commit();
    }
}

