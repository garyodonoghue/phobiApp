package com.gary.spiders.activity;

import android.content.DialogInterface;
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

    public void playGame(View view) {

        if(!this.initialAssessmentCompleted){
            // LINGUISTIC_HIGH is the first HIGH category
            BaseGame firstGame = GameFactory.generateGame(GameCategory.LINGUISTIC_HIGH, true);
            Intent intent1 = new Intent(MainMenuActivity.this, firstGame.getClass());
            intent1.putExtra("category", firstGame.category.toString());
            intent1.putExtra("initialAssessment", firstGame.initialAssessment);

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        GameCategory[] categoriesArray = GameCategory.getInitialAssessmentCategories();

        if (this.requestCode == requestCode) {
            if (resultCode == RESULT_OK) {
                boolean levelCompleted = Boolean.valueOf(data.getStringExtra("completed"));
                String categoryString  = data.getStringExtra("category");

                // TODO are these values coming from the intents set up above or set from the alert dialog utility??
                // Looks like they're coming from the Alert utility - need to confirm
                boolean initialAssessment  = Boolean.valueOf(data.getStringExtra("initialAssessment"));

                GameCategory category = GameCategory.valueOf(categoryString);

                if(initialAssessment){
                    if(levelCompleted){
                        getNextInitialAssessmentGame(requestCode, categoriesArray, category);
                    }
                    else{
                        // User did not complete a level - get the lowest level for that category and set that as their new starting level
                        int userLevel = category.getBeginnerLevelForCategory();
                        updateUserLevel(userLevel);
                        updateUserPoints(category.getNumPointsForLevel(userLevel));

                        AlertDialog alert = AlertUtility.createInitialAssessmentCompletedAlert(this);
                        alert.show();
                    }
                }
                else {
                    if(levelCompleted){
                        // User completed the level and it is not an initial assessment
                        // Need to award them points and determine what level they are now on,
                        // generating a new game based on that
                        int userPoints = user.getPoints();
                        int updatedPoints = userPoints + 1;
                        updateUserPoints(updatedPoints);

                        // get new level based on new number of points
                        int userLevel = category.getUserLevelFromPoints(updatedPoints);
                        updateUserLevel(userLevel);
                    }
                }
            }
        }
    }

    // TODO Should I get rid of storing/updating level, since this can be worked out based on the number of points a user has
    private void updateUserLevel(int newUserLevel) {
        SharedPreferences userData = getSharedPreferences("UserDetails", 0);
        SharedPreferences.Editor editor = userData.edit();
        editor.putString("level", Integer.toString(newUserLevel));
        editor.commit();
        MainMenuActivity.user.setLevel(newUserLevel);
    }

    private void updateUserPoints(int numPoints) {
        SharedPreferences userData = getSharedPreferences("UserDetails", 0);
        SharedPreferences.Editor editor = userData.edit();
        editor.putString("points", Integer.toString(numPoints));
        editor.commit();
        MainMenuActivity.user.setPoints(numPoints);
    }

    private void getNextInitialAssessmentGame(int requestCode, GameCategory[] categoriesArray, GameCategory category) {
        int nextCategoryIndex = Arrays.asList(categoriesArray).indexOf(category) + 1;

        if(nextCategoryIndex < categoriesArray.length) {
            GameCategory nextCategory = categoriesArray[nextCategoryIndex];


            BaseGame game = GameFactory.generateGame(nextCategory, true);
            Intent intent = new Intent(MainMenuActivity.this, game.getClass());
            intent.putExtra("category", game.category.toString());
            intent.putExtra("initialAssessment", game.initialAssessment);

            MainMenuActivity.this.startActivityForResult(intent, requestCode);
        }
        else{
            // TODO initial assessment completed, and user finished all categories, what should we do in this scenario?
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setTitle("Initial Assessment Completed!");
            alertDialog.setMessage("You completed all levels");

            alertDialog.show();
        }
    }
}
