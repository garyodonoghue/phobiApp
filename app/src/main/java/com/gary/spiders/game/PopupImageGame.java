package com.gary.spiders.game;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;

import com.gary.spiders.R;
import com.gary.spiders.util.AlertUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopupImageGame extends Game {

    private boolean started = false;
    private Handler handler = new Handler();
    List<Integer> allImageResourceIds;

    List<Integer> spiderImageResourceIds = new ArrayList<>();
    List<Integer> nonSpiderImageResourceIds = new ArrayList<>();

    ImageButton popupImageButton;
    Random rand = new Random();

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

        GameResourceLoader resourceLoader = new GameResourceLoader(this);
        int spiderImgsArrayId = resourceLoader.getResourceArray(GameCategory.IMAGE_PICKER_SPIDER_IMAGES);
        int nonSpiderImgsArrayId = resourceLoader.getResourceArray(GameCategory.IMAGE_PICKER_NON_SPIDER_IMAGES);

        TypedArray spiderImages = getResources().obtainTypedArray(spiderImgsArrayId);
        TypedArray nonSpiderImages = getResources().obtainTypedArray(nonSpiderImgsArrayId);

        allImageResourceIds = new ArrayList<>();
        for(int i = 0; i<spiderImages.length(); i++){
            int spiderImageId = spiderImages.getResourceId(i, -1);
            allImageResourceIds.add(spiderImageId);
            spiderImageResourceIds.add(spiderImageId);
        }

        for(int j = 0; j<nonSpiderImages.length(); j++){
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
        handler.postDelayed(runnable, 3000);

        popupImageButton.clearColorFilter();
    }

    public void stop() {
        started = false;
        handler.removeCallbacks(runnable);
    }

    public void imageTapped(View v){
        ImageButton imageButton = (ImageButton) v;
        if(spiderImageResourceIds.contains(imageButton.getTag())){
            imageButton.setColorFilter(Color.argb(100, 0, 255, 0), PorterDuff.Mode.DARKEN);
            allImageResourceIds.remove(imageButton.getTag());

            // all spider images have been removed, the game is completed
            if(allImageResourceIds.size() == nonSpiderImageResourceIds.size()){
                stop();
                AlertDialog successAlert = AlertUtility.createGameCompletedAlert(this, this.category);
                successAlert.show();
            }
        }
        else {
            imageButton.setColorFilter(Color.argb(100, 255, 0, 0), PorterDuff.Mode.DARKEN);
        }
    }
}