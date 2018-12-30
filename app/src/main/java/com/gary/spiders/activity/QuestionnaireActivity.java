package com.gary.spiders.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.TextView;

import com.gary.spiders.R;
import com.gary.spiders.util.AlertUtility;
import com.gary.spiders.util.EpochUtil;
import com.gary.spiders.util.InputFilterMinMax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * FSQ Questionnaire activity presented to the user, asking them a number of questions (18 in total)
 * which they give numeric answers to (1-7)
 */
public class QuestionnaireActivity extends AppCompatActivity {

    @BindView(R.id.question_1) TextView question1;
    @BindView(R.id.question_2) TextView question2;
    @BindView(R.id.question_3) TextView question3;
    @BindView(R.id.question_4) TextView question4;
    @BindView(R.id.question_5) TextView question5;
    @BindView(R.id.question_6) TextView question6;
    @BindView(R.id.question_7) TextView question7;
    @BindView(R.id.question_8) TextView question8;
    @BindView(R.id.question_9) TextView question9;
    @BindView(R.id.question_10) TextView question10;
    @BindView(R.id.question_11) TextView question11;
    @BindView(R.id.question_12) TextView question12;
    @BindView(R.id.question_13) TextView question13;
    @BindView(R.id.question_14) TextView question14;
    @BindView(R.id.question_15) TextView question15;
    @BindView(R.id.question_16) TextView question16;
    @BindView(R.id.question_17) TextView question17;
    @BindView(R.id.question_18) TextView question18;

    private List<TextView> textViews = new ArrayList<>();
    private SharedPreferences questionnaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        ButterKnife.bind(this);

        textViews.addAll(new ArrayList<TextView>(Arrays.asList(new TextView[]{question1, question2, question3, question4,
                question5, question6, question7, question8, question9, question10,
                question11, question12, question13, question14, question15,
                question16, question17, question18})));

        setMinMaxRanges();
    }

    private void setMinMaxRanges(){
        InputFilter[] inputFilter = new InputFilter[]{new InputFilterMinMax("1", "7")};
        question1.setFilters(inputFilter);
        question2.setFilters(inputFilter);
        question3.setFilters(inputFilter);
        question4.setFilters(inputFilter);
        question5.setFilters(inputFilter);
        question6.setFilters(inputFilter);
        question7.setFilters(inputFilter);
        question8.setFilters(inputFilter);
        question9.setFilters(inputFilter);
        question10.setFilters(inputFilter);
        question11.setFilters(inputFilter);
        question12.setFilters(inputFilter);
        question13.setFilters(inputFilter);
        question14.setFilters(inputFilter);
        question15.setFilters(inputFilter);
        question16.setFilters(inputFilter);
        question17.setFilters(inputFilter);
        question18.setFilters(inputFilter);
    }

    public void submitQuestionnaire(View v){
        questionnaire = getSharedPreferences("Questionnaire", 0);
        SharedPreferences.Editor editor = questionnaire.edit();

        if(this.checkAllValuesAreSet()) {
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



            Intent data = new Intent();
            boolean jumpedToNextCategory = getIntent().getBooleanExtra("initialAssessment", false);
            data.putExtra("jumpedToNextCategory", jumpedToNextCategory);

            String category = getIntent().getStringExtra("category");
            data.putExtra("category", category);

            boolean initialAssessment = getIntent().getBooleanExtra("initialAssessment", false);
            data.putExtra("initialAssessment", initialAssessment);

            // we show the FSQ at the start, half way through the game and at the end.
            // here we know it is the midway point through the game cause the it is not the initial assessment
            if(!initialAssessment){
                SharedPreferences userData = getSharedPreferences("UserDetails", 0);
                SharedPreferences.Editor userDataEditor = userData.edit();
                editor.putString("hasMidwayFSQBeenPresented", "true");
                editor.commit();

                MainMenuActivity.user.setHasMidwayFSQBeenPresented("true");
            }

            Class gameClass = (Class) getIntent().getSerializableExtra("gameClass");
            data.putExtra("gameClass", gameClass);
            setResult(RESULT_OK, data);

            this.finish();
        }
        else{
            AlertDialog alert = AlertUtility.createInfoAlertDialog(this, "Missing value!",
                    "Please ensure to fill out every question in the form");
            alert.show();
        }
    }

    private boolean checkAllValuesAreSet(){
        boolean allSet = true;
        for(TextView textView : textViews){
            if(textView.getText().toString() == null || textView.getText().toString().isEmpty()){
                allSet = false;
                break;
            }
        }
        return allSet;
    }
}


