package com.gary.spiders.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.gary.spiders.R;

import butterknife.BindView;

public class FearRatingActivity extends AppCompatActivity {

    @BindView(R.id.fearRatingValue) EditText fearRatingValue;
    private boolean initialAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fear_rating);

        boolean initialAssess = getIntent().getBooleanExtra("initialAssessment", false);
        this.initialAssessment = Boolean.valueOf(initialAssess);
    }

    public void submitFearRating(View v){
        if(!fearRatingValue.getText().toString().isEmpty()){
            Intent data = getIntent();
            data.putExtra("initialAssessment", this.initialAssessment);

            this.setResult(RESULT_OK, data);
            this.finish();
        }
    }
}
