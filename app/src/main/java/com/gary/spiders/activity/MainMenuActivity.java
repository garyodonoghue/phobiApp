package com.gary.spiders.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        setupUserProfile();
    }

    private void setupUserProfile() {
        // Load the user's details:
        // name / avatar resource id / level / num points, see User object for fields available
        SharedPreferences preferences = getSharedPreferences("UserDetails", 0);
        MainMenuActivity.user = new User(preferences);

        if(Boolean.valueOf(user.isInitialAssessmentCompleted())){
            Button playBtn = (Button) findViewById(R.id.playGame);
            playBtn.setText("Continue Playing");
            this.initialAssessmentCompleted = true;
        }

        if(user.getAvatarResourceId() != -1){
            ImageView userAvatar = (ImageView) findViewById(R.id.user_avatar);
            userAvatar.setImageResource(user.getAvatarResourceId());
        }

        // no need to validate the username input, as it will default to username anyway
        TextView usernameTextView = (TextView) findViewById(R.id.username);
        usernameTextView.setText(user.getName());
    }

    public void playGame(View view) {

        if(!this.initialAssessmentCompleted){
            // LINGUISTIC_HIGH is the first HIGH category
            BaseGame gameType = GameFactory.generateGame(GameCategory.LINGUISTIC_HIGH, true);
            Intent intent1 = new Intent(MainMenuActivity.this, gameType.getClass());

            // Note these will not be available in the onResult callback directly, these are used to set the flags in the BaseGame class,
            // which will in turn be used by the AlertUtility to set the onResult values.
            intent1.putExtra("category", gameType.category.toString());
            intent1.putExtra("initialAssessment", gameType.initialAssessment);

            MainMenuActivity.this.startActivityForResult(intent1, requestCode);
        }
        else {
            BaseGame newGameType = GameFactory.generateGame(GameCategory.getCategory(user.getLevel()), false);
            Intent intent1 = new Intent(MainMenuActivity.this, newGameType.getClass());
            intent1.putExtra("category", newGameType.category.toString());
            intent1.putExtra("initialAssessment", newGameType.initialAssessment);
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
                        setInitialAssessmentCompletedFlag();

                        AlertDialog alert = AlertUtility.createInfoAlertDialog(this, "Assessment Completed!",
                                "Thank you for taking the initial assessment. We will now present you levels based on your results");
                        alert.show();
                    }
                }
                else {
                    if(levelCompleted){
                        // User completed the level and it is not an initial assessment
                        // Need to award them points and determine what level they are now on,
                        // generating a new game based on that
                        int oldPoints = user.getPoints();
                        int oldLevel = category.getUserLevelFromPoints(oldPoints);

                        int updatedPoints = oldPoints + 1;
                        updateUserPoints(updatedPoints);


                        // get new level based on new number of points
                        int newLevel = category.getUserLevelFromPoints(updatedPoints);
                        updateUserLevel(newLevel);

                        // check if they upped a level, present a congrats dialog box
                        if(oldLevel != newLevel){
                            AlertDialog alert = AlertUtility.createInfoAlertDialog(this, "Congratulations!", "You've just levelled up to level "+newLevel+"! Well done!");
                        }
                    }
                }
            }
        }
    }

    private void setInitialAssessmentCompletedFlag() {
        SharedPreferences userData = getSharedPreferences("UserDetails", 0);
        SharedPreferences.Editor editor = userData.edit();
        editor.putString("initialAssessmentCompleted", "true");
        editor.commit();

        MainMenuActivity.user.setInitialAssessmentCompleted("true");
        Button playBtn = (Button) findViewById(R.id.playGame);
        playBtn.setText("Continue Playing");
        this.initialAssessmentCompleted = true;

        //updateUserLevel();
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
            setInitialAssessmentCompletedFlag();

            AlertDialog alertDialog = AlertUtility.createInfoAlertDialog(this, "Initial Assessment Completed!","You completed all levels" );
            alertDialog.show();
        }
    }
}
