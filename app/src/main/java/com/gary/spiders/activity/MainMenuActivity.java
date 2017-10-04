package com.gary.spiders.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gary.spiders.R;
import com.gary.spiders.game.Game;
import com.gary.spiders.game.GameCategory;
import com.gary.spiders.game.GameGenerator;
import com.gary.spiders.util.AlertUtility;

import java.util.Arrays;

import static com.gary.spiders.game.GameCategory.LINGUISTIC_HIGH;

public class MainMenuActivity extends AppCompatActivity {

    int requestCode = 1;

    public void startInitialAssessment(View view) {
        Game linguistic_hard = GameGenerator.generateGame(LINGUISTIC_HIGH, true);
        Intent intent1 = new Intent(MainMenuActivity.this, linguistic_hard.getClass());
        intent1.putExtra("category", linguistic_hard.category.toString());
        intent1.putExtra("initialAssessment", linguistic_hard.initialAssessment);

        MainMenuActivity.this.startActivityForResult(intent1, requestCode);
    }

    public void presentChooseGameScreen(View view) {
        Intent myIntent = new Intent(MainMenuActivity.this, ChooseGameActivity.class);
        MainMenuActivity.this.startActivity(myIntent);
    }

    public void showProgress(View view) {
        Intent myIntent = new Intent(MainMenuActivity.this, MetricsActivity.class);
        MainMenuActivity.this.startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        GameCategory[] categoriesArray = GameCategory.getInitialAssessmentCategories();

        if (this.requestCode == requestCode) {
            if (resultCode == RESULT_OK) {
                boolean levelCompleted = Boolean.valueOf(data.getStringExtra("completed"));
                String categoryString  = data.getStringExtra("category");
                GameCategory category = GameCategory.valueOf(categoryString);

                if(levelCompleted){
                    int nextCategoryIndex = Arrays.asList(categoriesArray).indexOf(category) + 1;
                    if(nextCategoryIndex < categoriesArray.length) {
                        GameCategory nextCategory = categoriesArray[nextCategoryIndex];


                        Game game = GameGenerator.generateGame(nextCategory, true);
                        Intent intent = new Intent(MainMenuActivity.this, game.getClass());
                        intent.putExtra("category", game.category.toString());
                        intent.putExtra("initialAssessment", game.initialAssessment);

                        MainMenuActivity.this.startActivityForResult(intent, requestCode);
                    }

                }
                else{
                    // User did not complete a level - get the lowest level for that category and set that as their new starting level
                    int newUserLevel = category.getBeginnerLevelForCategory();
                    SharedPreferences userData = getSharedPreferences("UserData", 0);
                    SharedPreferences.Editor editor = userData.edit();
                    editor.putString("userLevel", Integer.toString(newUserLevel));
                    editor.commit();
                    
                    AlertDialog alert = AlertUtility.createInitialAssessmentCompletedAlert(this);
                    alert.show();
                }
            }
        }
    }
}
