package com.gary.spiders.game;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import butterknife.BindView;

public class ImagePickerGame extends BaseGame {

    @BindView(R.id.countdownImagePicker) TextView textView;
    @BindView(R.id.top_left) ImageButton topLeft;
    @BindView(R.id.top_center) ImageButton topCenter;
    @BindView(R.id.top_right) ImageButton topRight;
    @BindView(R.id.center_left) ImageButton centerLeft;
    @BindView(R.id.center_center) ImageButton centerCenter;
    @BindView(R.id.center_right) ImageButton centerRight;
    @BindView(R.id.bottom_left) ImageButton bottomLeft;
    @BindView(R.id.bottom_center) ImageButton bottomCenter;
    @BindView(R.id.bottom_right) ImageButton bottomRight;
    @BindView(R.id.bottommost_left) ImageButton bottommostLeft;
    @BindView(R.id.bottommost_center) ImageButton bottommostCenter;
    @BindView(R.id.bottommost_right) ImageButton bottommostRight;

    private int spiderImgsArrayId;
    private int nonSpiderImgsArrayId;
    private List<ImageButton> spiderButtons = new ArrayList<>();
    private int correctSelections;
    private int incorrectSelections;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);

        CountDownTimer timer = super.setupGameTimer(textView, this, 15000);

        mp = MediaPlayer.create(getBaseContext(), R.raw.fail2);

        super.presentGameInfoPopup(this, "For this game you need to pick all " +
                "the images which contain a spider", timer);

        GameResourceLoader resourceLoader = new GameResourceLoader(this);

        if(super.category == null){
            super.category = GameCategory.DRAWING_COL_HIGH;
        }
        spiderImgsArrayId = resourceLoader.getResourceArray(super.category);
        nonSpiderImgsArrayId = resourceLoader.getNonSpiderResourceArray(super.category);

        TypedArray spiderImages = getResources().obtainTypedArray(spiderImgsArrayId);
        TypedArray nonSpiderImages = getResources().obtainTypedArray(nonSpiderImgsArrayId);

        // Pick a random selection of 4 image buttons which will contain images of spiders
        List<ImageButton> imageButtonsList = new ArrayList<>();

        imageButtonsList.add(topLeft);
        imageButtonsList.add(topCenter);
        imageButtonsList.add(topRight);

        imageButtonsList.add(centerLeft);
        imageButtonsList.add(centerCenter);
        imageButtonsList.add(centerRight);

        imageButtonsList.add(bottomLeft);
        imageButtonsList.add(bottomCenter);
        imageButtonsList.add(bottomRight);

        imageButtonsList.add(bottommostLeft);
        imageButtonsList.add(bottommostCenter);
        imageButtonsList.add(bottommostRight);

        Random rand = new Random();
        for(int i=0; i<4; i++) {
            ImageButton randomElement = imageButtonsList.get(rand.nextInt(imageButtonsList.size()));
            spiderButtons.add(randomElement);

            int randomInt = rand.nextInt(spiderImages.length());
            int spiderImgResId = spiderImages.getResourceId(randomInt, -1);

            randomElement.setImageResource(spiderImgResId);
            imageButtonsList.remove(randomElement);
        }

        for(ImageButton imageButton : imageButtonsList){
            int randomInt = rand.nextInt(nonSpiderImages.length());
            int nonSpiderImgResId = nonSpiderImages.getResourceId(randomInt, 1);

            imageButton.setImageResource(nonSpiderImgResId);
        }

        spiderImages.recycle();
        nonSpiderImages.recycle();
    }

    public void tileClicked(View v){
        ImageButton imageButton = (ImageButton) v;
        if(spiderButtons.contains(imageButton)) {
            imageButton.setColorFilter(Color.argb(100, 0, 255, 0), PorterDuff.Mode.DARKEN);
            correctSelections++;
        }
        else{
            mp.start();
            imageButton.setColorFilter(Color.argb(100, 255, 0, 0), PorterDuff.Mode.DARKEN);
            incorrectSelections++;

            if(incorrectSelections >= 3){
                super.failedDueToIncorrectAttempts(this);
            }
        }

        int bonusPoints = correctSelections - incorrectSelections;
        if(bonusPoints < 0){
            bonusPoints = 0;
        }
        this.setBonusPoints(bonusPoints);

        if(correctSelections == spiderButtons.size()){
            super.stopTimer();
            AlertDialog successAlert = AlertUtility.createGameCompletedAlert(this);
            successAlert.show();
        }
    }

    public void giveUp(View v){
        super.giveUp(v);
    }
}
