package com.gary.spiders.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.gary.spiders.util.EpochUtil;

import java.util.Arrays;

public class MainMenuActivity extends AppCompatActivity {

    int requestCode = 1;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setupUserProfile();
        this.logUserProgress();
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.setupUserProfile();
    }

    private void setupUserProfile() {
        // Load the user's details:
        // name / avatar resource id / level / num points, see User object for fields available
        SharedPreferences preferences = getSharedPreferences("UserDetails", 0);
        MainMenuActivity.user = new User(preferences);

        Button playBtn = (Button) findViewById(R.id.playGame);
        if(Boolean.valueOf(user.isInitialAssessmentCompleted())){
            playBtn.setText("Continue Playing");
        }
        else
        {
            playBtn.setText("Initial Assessment");
        }

        if(!user.getAvatarResource().isEmpty()){
            ImageView userAvatar = (ImageView) findViewById(R.id.user_avatar);
            int resId = getResources().getIdentifier(user.getAvatarResource(), "mipmap", this.getPackageName());
            userAvatar.setImageResource(resId);
        }

        // no need to validate the username input, as it will default to username anyway
        TextView usernameTextView = (TextView) findViewById(R.id.username);
        usernameTextView.setText(user.getName());

        TextView userLevel = (TextView) findViewById(R.id.userLevel);
        userLevel.setText(String.valueOf(user.getLevel()));
    }

    public void playGame(View view) {
        if(!user.isInitialAssessmentCompleted()){
            // LINGUISTIC_HIGH is the first HIGH category
            BaseGame gameType = GameFactory.generateGameFromUserCategory(GameCategory.LINGUISTIC_HIGH, true);
            Intent intent1 = new Intent(MainMenuActivity.this, gameType.getClass());

            // Note these will not be available in the onResult callback directly, these are used to set the flags in the BaseGame class,
            // which will in turn be used by the AlertUtility to set the onResult values.
            intent1.putExtra("category", gameType.category.toString());
            intent1.putExtra("initialAssessment", gameType.initialAssessment);

            MainMenuActivity.this.startActivityForResult(intent1, requestCode);
        }
        else {
            BaseGame newGameType = GameFactory.generateGameFromUserLevel(user.getLevel(), false);
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

    public void chooseGame(View v){
        Intent myIntent = new Intent(MainMenuActivity.this, ChooseGameActivity.class);
        MainMenuActivity.this.startActivity(myIntent);
    }


    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        GameCategory[] categoriesArray = GameCategory.getInitialAssessmentCategories();

        if (this.requestCode == requestCode) {
            if (resultCode == RESULT_OK) {
                boolean levelCompleted = Boolean.valueOf(data.getStringExtra("completed"));
                boolean tryAgain = Boolean.valueOf(data.getStringExtra("tryAgain"));
                String categoryString  = data.getStringExtra("category");
                int bonusPoints = Integer.parseInt(data.getStringExtra("bonusPoints"));
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
                        setInitialAssessmentCompletedFlag();

                        AlertDialog alert = AlertUtility.createInfoAlertDialog(this, "Assessment Completed!",
                                "Thank you for taking the initial assessment. We will now present you levels based on your results");
                        alert.show();
                        alert.setOnDismissListener(new DialogInterface.OnDismissListener(){

                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Intent questionnarire = new Intent(MainMenuActivity.this, QuestionnaireActivity.class);
                                MainMenuActivity.this.startActivity(questionnarire);
                            }
                        });
                    }
                }
                else {
                    if(levelCompleted){
                        // User completed the level and it is not an initial assessment
                        // Need to bump their level by 1
                        // generating a new game based on that new level value
                        final int oldLevel = user.getLevel();
                        final int newLevel = oldLevel + 1 + bonusPoints;
                        updateUserLevel(newLevel);

                        GameCategory oldCategory = GameCategory.getCategory(oldLevel);
                        final GameCategory newCategory = GameCategory.getCategory(newLevel);

                        final BaseGame game = GameFactory.generateGameFromUserCategory(newCategory, false);
                        final Intent intent1 = new Intent(MainMenuActivity.this, game.getClass());
                        // Note these will not be available in the onResult callback directly, these are used to set the flags in the BaseGame class,
                        // which will in turn be used by the AlertUtility to set the onResult values.
                        intent1.putExtra("category", game.category.toString());
                        intent1.putExtra("initialAssessment", game.initialAssessment);

                        // check if they upped a level, present a congrats dialog box
                        if(oldCategory != newCategory){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainMenuActivity.this);

                            alertDialogBuilder.setPositiveButton("Thanks!",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            MainMenuActivity.this.startActivityForResult(intent1, requestCode);
                                        }
                                    });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.setTitle("Congratulations!");
                            alertDialog.setMessage("You've just levelled up to a new category of games! Well done!");
                            alertDialog.setCancelable(false);
                            alertDialog.show();

                            final MediaPlayer mp = MediaPlayer.create(this, R.raw.next_level_success);
                            mp.start();
                        }
                        else{
                            // User completed the level without levelling up to the next category
                            MainMenuActivity.this.startActivityForResult(intent1, requestCode);
                        }
                    }
                    else {
                        // User ran out of time, gave up etc, deduct them a point
                        int userLevel = user.getLevel();
                        if (userLevel > 0) {
                            userLevel = userLevel - 1 + bonusPoints;
                        }
                        user.setLevel(userLevel);

                        // User failed to complete the level (ran out of time),
                        // let them try again with a new level
                        if(tryAgain){
                            BaseGame game = GameFactory.generateGameFromUserLevel(user.getLevel(), false);
                            Intent intent1 = new Intent(MainMenuActivity.this, game.getClass());

                            intent1.putExtra("category", game.category.toString());
                            intent1.putExtra("initialAssessment", game.initialAssessment);

                            MainMenuActivity.this.startActivityForResult(intent1, requestCode);
                        }
                        else {
                            // User gave up - levelCompleted is false,
                            // so just bring them back to the main menu
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
    }

    private void updateUserLevel(int newUserLevel) {
        SharedPreferences userData = getSharedPreferences("UserDetails", 0);
        SharedPreferences.Editor editor = userData.edit();
        editor.putString("level", Integer.toString(newUserLevel));
        editor.commit();
        MainMenuActivity.user.setLevel(newUserLevel);
        Log.d("UpdateUserLevel", "level="+newUserLevel);

        this.logUserProgress();
    }

    // Log the date with the user level, so it can be graphed later
    private void logUserProgress(){
        SharedPreferences userData = getSharedPreferences("Progress", 0);
        SharedPreferences.Editor editor = userData.edit();
        editor.putString(""+EpochUtil.getEpochTime(), Integer.toString(user.getLevel()));
        editor.commit();
    }

    private void getNextInitialAssessmentGame(int requestCode, GameCategory[] categoriesArray, GameCategory category) {
        int nextCategoryIndex = Arrays.asList(categoriesArray).indexOf(category) + 1;

        if(nextCategoryIndex < categoriesArray.length) {
            GameCategory nextCategory = categoriesArray[nextCategoryIndex];


            BaseGame game = GameFactory.generateGameFromUserCategory(nextCategory, true);
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
