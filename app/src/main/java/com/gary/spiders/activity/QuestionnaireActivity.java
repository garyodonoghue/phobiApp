package com.gary.spiders.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gary.spiders.R;
import com.gary.spiders.util.EpochUtil;

public class QuestionnaireActivity extends AppCompatActivity {

    SharedPreferences questionnaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
    }

    public void submitQuestionnaire(View v){
        questionnaire = getSharedPreferences("Questionnaire", 0);
        SharedPreferences.Editor editor = questionnaire.edit();

        TextView question1 = (TextView) findViewById(R.id.question_1);
        TextView question2 = (TextView) findViewById(R.id.question_2);
        TextView question3 = (TextView) findViewById(R.id.question_3);
        TextView question4 = (TextView) findViewById(R.id.question_4);
        TextView question5 = (TextView) findViewById(R.id.question_5);
        TextView question6 = (TextView) findViewById(R.id.question_6);
        TextView question7 = (TextView) findViewById(R.id.question_7);
        TextView question8 = (TextView) findViewById(R.id.question_8);
        TextView question9 = (TextView) findViewById(R.id.question_9);
        TextView question10 = (TextView) findViewById(R.id.question_10);
        TextView question11 = (TextView) findViewById(R.id.question_11);
        TextView question12 = (TextView) findViewById(R.id.question_12);
        TextView question13 = (TextView) findViewById(R.id.question_13);
        TextView question14 = (TextView) findViewById(R.id.question_14);
        TextView question15 = (TextView) findViewById(R.id.question_15);
        TextView question16 = (TextView) findViewById(R.id.question_16);
        TextView question17 = (TextView) findViewById(R.id.question_17);
        TextView question18 = (TextView) findViewById(R.id.question_18);

        editor.putString("question1" + "_" + EpochUtil.getEpochTime(), question1.getText().toString());
        editor.putString("question2" + "_" + EpochUtil.getEpochTime(), question2.getText().toString());
        editor.putString("question3" + "_" + EpochUtil.getEpochTime(), question3.getText().toString());
        editor.putString("question4" + "_" + EpochUtil.getEpochTime(), question4.getText().toString());
        editor.putString("question5" + "_" + EpochUtil.getEpochTime(), question5.getText().toString());
        editor.putString("question6" + "_" + EpochUtil.getEpochTime(), question6.getText().toString());
        editor.putString("question7" + "_" + EpochUtil.getEpochTime(), question7.getText().toString());
        editor.putString("question8" + "_" + EpochUtil.getEpochTime(), question8.getText().toString());
        editor.putString("question10" + "_" + EpochUtil.getEpochTime(), question9.getText().toString());
        editor.putString("question10" + "_" + EpochUtil.getEpochTime(), question10.getText().toString());
        editor.putString("question11" + "_" + EpochUtil.getEpochTime(), question11.getText().toString());
        editor.putString("question12" + "_" + EpochUtil.getEpochTime(), question12.getText().toString());
        editor.putString("question13" + "_" + EpochUtil.getEpochTime(), question13.getText().toString());
        editor.putString("question14" + "_" + EpochUtil.getEpochTime(), question14.getText().toString());
        editor.putString("question15" + "_" + EpochUtil.getEpochTime(), question15.getText().toString());
        editor.putString("question16" + "_" + EpochUtil.getEpochTime(), question16.getText().toString());
        editor.putString("question17" + "_" + EpochUtil.getEpochTime(), question17.getText().toString());
        editor.putString("question18" + "_" + EpochUtil.getEpochTime(), question18.getText().toString());

        editor.commit();
    }
}


