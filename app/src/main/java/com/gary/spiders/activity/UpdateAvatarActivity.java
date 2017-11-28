package com.gary.spiders.activity;

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

        // TODO get the user's current from preferences and set here
        //currentAvatar.

    }

    public void tileClicked(View v){
        String imageTag = (String) v.getTag();
        int resId = getResources().getIdentifier(imageTag, "mipmap", this.getPackageName());

        ImageView imageView = (ImageView) findViewById(R.id.selectedAvatar);
        imageView.setBackgroundResource(resId);
    }
}
