package com.gary.spiders.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gary.spiders.R;
import com.gary.spiders.enums.GameCategory;
import com.gary.spiders.game.BaseGame;
import com.gary.spiders.game.GameFactory;
import com.gary.spiders.user.User;
import com.gary.spiders.util.AlertUtility;

import java.util.Arrays;

public class MainMenuActivity extends AppCompatActivity {

    int requestCode = 1;
    public boolean initialAssessmentCompleted = false;
    public static User user;

    public void playGame(View view) {

        if(!this.initialAssessmentCompleted){
            BaseGame linguistic_hard = GameFactory.generateGame(GameCategory.CARTOON_HIGH, true);
            Intent intent1 = new Intent(MainMenuActivity.this, linguistic_hard.getClass());
            intent1.putExtra("category", linguistic_hard.category.toString());
            intent1.putExtra("initialAssessment", linguistic_hard.initialAssessment);

            MainMenuActivity.this.startActivityForResult(intent1, requestCode);
        }
        else {
            BaseGame newGame = GameFactory.generateGame(GameCategory.getCategory(user.getLevel()), false);
            Intent intent1 = new Intent(MainMenuActivity.this, newGame.getClass());
            intent1.putExtra("category", newGame.category.toString());
            intent1.putExtra("initialAssessment", newGame.initialAssessment);

            MainMenuActivity.this.startActivityForResult(intent1, requestCode);
        }
    }

    public void showProgress(View view) {
        Intent myIntent = new Intent(MainMenuActivity.this, MetricsActivity.class);
        MainMenuActivity.this.startActivity(myIntent);
    }

    public void settingsClicked(View view) {
        Intent myIntent = new Intent(MainMenuActivity.this, SettingsActivity.class);
        MainMenuActivity.this.startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Load the user's details:
        // name / avatar resource id / level / num points, see User object for fields available
        SharedPreferences preferences = getSharedPreferences("UserDetails", 0);
        MainMenuActivity.user = new User(preferences);

        Button playBtn = (Button) findViewById(R.id.playGame);
        if(user.isInitialAssessmentCompleted()){
            playBtn.setText("Continue Playing");
            this.initialAssessmentCompleted = true;
        }
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


                        BaseGame game = GameFactory.generateGame(nextCategory, true);
                        Intent intent = new Intent(MainMenuActivity.this, game.getClass());
                        intent.putExtra("category", game.category.toString());
                        intent.putExtra("initialAssessment", game.initialAssessment);

                        MainMenuActivity.this.startActivityForResult(intent, requestCode);
                    }

                }
                else{
                    // User did not complete a level - get the lowest level for that category and set that as their new starting level
                    int newUserLevel = category.getBeginnerLevelForCategory();
                    SharedPreferences userData = getSharedPreferences("UserDetails", 0);
                    SharedPreferences.Editor editor = userData.edit();
                    editor.putString("level", Integer.toString(newUserLevel));
                    editor.commit();

                    AlertDialog alert = AlertUtility.createInitialAssessmentCompletedAlert(this);
                    alert.show();
                }
            }
        }
    }
}
