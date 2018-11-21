package com.gary.spiders.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.gary.spiders.R;

public class FearRatingActivity extends AppCompatActivity {

    boolean initialAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fear_rating);

        boolean initialAssess = getIntent().getBooleanExtra("initialAssessment", false);
        this.initialAssessment = Boolean.valueOf(initialAssess);
    }

    public void submitFearRating(View v){
        EditText fearRatingValue = (EditText) findViewById(R.id.fearRatingValue);
        if(!fearRatingValue.getText().toString().isEmpty()){
            Intent data = getIntent();
            data.putExtra("initialAssessment", this.initialAssessment);

            this.setResult(RESULT_OK, data);
            this.finish();
        }
    }
}
