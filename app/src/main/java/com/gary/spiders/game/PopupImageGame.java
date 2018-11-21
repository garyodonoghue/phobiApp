package com.gary.spiders.game;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gary.spiders.R;
import com.gary.spiders.enums.GameCategory;
import com.gary.spiders.util.AlertUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopupImageGame extends BaseGame {

    private boolean started = false;
    private Handler handler = new Handler();
    List<Integer> allImageResourceIds;

    List<Integer> spiderImageResourceIds = new ArrayList<>();
    List<Integer> nonSpiderImageResourceIds = new ArrayList<>();

    ImageButton popupImageButton;
    Random rand = new Random();
    MediaPlayer mp;

    int incorrectSelections = 0;
    int bonusPoints = 10;
    int foundImages = 0;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(started) {
                start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_image_game);

        popupImageButton = (ImageButton) findViewById(R.id.popupImageView);
        mp = MediaPlayer.create(getBaseContext(), R.raw.fail2);

        super.presentGameInfoPopup(this, "Tap all images which contain a spider", null);

        final TextView textView = (TextView) findViewById(R.id.popupImageTimer);
        super.setupGameTimer(textView, this, 60000);

        GameResourceLoader resourceLoader = new GameResourceLoader(this);

        if(super.category == null){
            super.category = GameCategory.CARTOON_LOW;
        }
        int spiderImgsArrayId = resourceLoader.getResourceArray(super.category);
        int nonSpiderImgsArrayId = resourceLoader.getNonSpiderResourceArray(super.category);

        TypedArray spiderImages = getResources().obtainTypedArray(spiderImgsArrayId);
        TypedArray nonSpiderImages = getResources().obtainTypedArray(nonSpiderImgsArrayId);

        allImageResourceIds = new ArrayList<>();
        for(int i = 0; i<5; i++){
            int spiderImageId = spiderImages.getResourceId(i, -1);
            allImageResourceIds.add(spiderImageId);
            spiderImageResourceIds.add(spiderImageId);
        }

        for(int j = 0; j< 10; j++){
            int nonSpiderImageId = nonSpiderImages.getResourceId(j, -1);
            allImageResourceIds.add(nonSpiderImageId);
            nonSpiderImageResourceIds.add(nonSpiderImageId);
        }

        start();
    }

    public void start() {
        started = true;
        int resourceId = allImageResourceIds.get(rand.nextInt(allImageResourceIds.size()));
        popupImageButton.setImageResource(resourceId);
        popupImageButton.setTag(resourceId);
        handler.postDelayed(runnable, 1000);

        popupImageButton.clearColorFilter();
    }

    public void stop() {
        started = false;
        handler.removeCallbacks(runnable);
    }

    public void imageTapped(View v){
        ImageButton imageButton = (ImageButton) v;
        if(spiderImageResourceIds.contains(imageButton.getTag())){
            foundImages++;

            imageButton.setColorFilter(Color.argb(100, 0, 255, 0), PorterDuff.Mode.DARKEN);
            allImageResourceIds.remove(imageButton.getTag());

            // remove a corresponding spider image so that the wait isnt too long
            Random random = new Random();
            int nonSpiderImageId = nonSpiderImageResourceIds.get(random.nextInt(nonSpiderImageResourceIds.size()));
            allImageResourceIds.remove(allImageResourceIds.indexOf(nonSpiderImageId));
            nonSpiderImageResourceIds.remove(nonSpiderImageResourceIds.indexOf(nonSpiderImageId));

            // all spider images have been removed, the game is completed
            if(spiderImageResourceIds.size() == foundImages){
                stop();
                super.stopTimer();
                AlertDialog successAlert = AlertUtility.createGameCompletedAlert(this);
                successAlert.show();
            }
        }
        else {
            incorrectSelections++;
            mp.start();
            this.bonusPoints = this.bonusPoints - 1;
            if(this.bonusPoints < 0){
                this.bonusPoints = 0;
            }
            super.setBonusPoints(this.bonusPoints);

            imageButton.setColorFilter(Color.argb(100, 255, 0, 0), PorterDuff.Mode.DARKEN);

            if(incorrectSelections >= 3){
                super.failedDueToIncorrectAttempts(this);
            }

        }
    }

    public void giveUp(View v){
        super.giveUp(v);
    }

}
