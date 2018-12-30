package com.gary.spiders.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.gary.spiders.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateAvatarActivity extends AppCompatActivity {

    @BindView(R.id.avatars_grid) GridLayout gv;
    @BindView(R.id.selectedAvatar) ImageView selectedAvatar;

    private ImageView currentAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_avatar);
        ButterKnife.bind(this);

        gv.setBackgroundColor(Color.WHITE);
        gv.setPadding(2, 2, 2,    2);

        String avatarResId = MainMenuActivity.user.getAvatarResource();
        if(!avatarResId.isEmpty()){
            int resId = getResources().getIdentifier(avatarResId, "drawable", this.getPackageName());
            selectedAvatar.setBackgroundResource(resId);
        }
    }

    public void tileClicked(View v){
        String imageTag = (String) v.getTag();
        selectedAvatar.setTag(imageTag);
        int resId = getResources().getIdentifier(imageTag, "drawable", this.getPackageName());
        selectedAvatar.setBackgroundResource(resId);
    }

    public void setAvatar(View v){
        String avatarImageId = (String) selectedAvatar.getTag();
        SharedPreferences userData = getSharedPreferences("UserDetails", 0);
        SharedPreferences.Editor editor = userData.edit();
        editor.putString("avatarResource", ""+avatarImageId);
        editor.commit();
    }
}

