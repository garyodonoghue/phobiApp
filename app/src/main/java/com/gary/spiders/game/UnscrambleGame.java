package com.gary.spiders.game;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gary.spiders.R;
import com.gary.spiders.enums.GameCategory;
import com.gary.spiders.util.AlertUtility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnscrambleGame extends BaseGame {

    @BindView(R.id.unscrambleTextTimer)
    TextView unscrambleTextTimer;

    @BindView(R.id.guessScrambledWord)
    EditText guessScrambledWord;

    @BindView(R.id.unscrambleTextImage)
    ImageView unscrambleTextImage;

    private int index = 1;
    private TypedArray unscrambleWordsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unscramble_game);

        ButterKnife.bind(this);
        CountDownTimer timer = super.setupGameTimer(unscrambleTextTimer, this, 30000);

        super.presentGameInfoPopup(this, "Click 'Unscramble Image' button to unscramble the word. " +
                "You can also guess the word. Your guess will be 'locked in' for the last two times you click the button and this will be taken as your final answer. " +
                "If you click through enough times to completely unscramble the word, you successfully complete the level" +
                "If you have also guessed the correct word as well you get bonus points!", timer);

        if(super.category == null){
            super.category = GameCategory.LINGUISTIC_HIGH;
        }

        GameResourceLoader resourceLoader = new GameResourceLoader(this);
        int unscrambleWordImages = resourceLoader.getUnscrambleGameWord();
        unscrambleWordsArray = getResources().obtainTypedArray(unscrambleWordImages);

        this.unscrambleTextImage.setImageResource(unscrambleWordsArray.getResourceId(index, 1));
    }

    public void unscrambleWord(View v){
        index++;

        if(index >= unscrambleWordsArray.length() - 1){
            AlertDialog alertDialog = AlertUtility.createGameCompletedAlert(UnscrambleGame.this);
            alertDialog.show();
            stopTimer();
        }
        else {
            // disable guessing the word for the last 2 turns
            if (index >= unscrambleWordsArray.length() - 2) {
                this.guessScrambledWord.setEnabled(false);
            }

            this.unscrambleTextImage.setImageResource(unscrambleWordsArray.getResourceId(index, 1));
        }
    }
}
