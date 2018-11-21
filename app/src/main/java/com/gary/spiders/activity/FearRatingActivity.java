package com.gary.spiders.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.gary.spiders.R;

public class FearRatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fear_rating);
    }

    public void submitFearRating(View v){
        EditText fearRatingValue = (EditText) findViewById(R.id.fearRatingValue);
        if(!fearRatingValue.getText().toString().isEmpty()){
            this.finish();
        }
    }
}
