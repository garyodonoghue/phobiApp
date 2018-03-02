package com.gary.spiders.rating;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.gary.spiders.util.EpochUtil;

/**
 * Created by Gary on 18/09/2017.
 */

public class RatingManager extends AppCompatActivity  {

    public void ratingClicked(View view) {
        RadioButton radioButton = (RadioButton) view;

        SharedPreferences ratings = getSharedPreferences("Ratings", 0);
        SharedPreferences.Editor editor = ratings.edit();
        editor.putString(this.getLocalClassName() + "_" + EpochUtil.getEpochTime(), radioButton.getText().toString());

        editor.commit();
    }
}
