package com.gary.spiders.game;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;

import com.gary.spiders.R;
import com.gary.spiders.enums.GameCategory;
import com.gary.spiders.util.AlertUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImagePickerGame extends BaseGame {

    ImageButton topLeft;
    ImageButton topCenter;
    ImageButton topRight;

    ImageButton centerLeft;
    ImageButton centerCenter;
    ImageButton centerRight;

    ImageButton bottomLeft;
    ImageButton bottomCenter;
    ImageButton bottomRight;

    ImageButton bottommostLeft;
    ImageButton bottommostCenter;
    ImageButton bottommostRight;

    int spiderImgsArrayId;
    int nonSpiderImgsArrayId;

    List<ImageButton> spiderButtons = new ArrayList<>();

    int correctSelections;
    int incorrectSelections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);

        GameResourceLoader resourceLoader = new GameResourceLoader(this);
        spiderImgsArrayId = resourceLoader.getResourceArray(GameCategory.IMAGE_PICKER_SPIDER_IMAGES);
        nonSpiderImgsArrayId = resourceLoader.getResourceArray(GameCategory.IMAGE_PICKER_NON_SPIDER_BW_IMAGES);

        TypedArray spiderImages = getResources().obtainTypedArray(spiderImgsArrayId);
        TypedArray nonSpiderImages = getResources().obtainTypedArray(nonSpiderImgsArrayId);

        topLeft = (ImageButton) findViewById(R.id.top_left);
        topCenter = (ImageButton) findViewById(R.id.top_center);
        topRight = (ImageButton) findViewById(R.id.top_right);

        centerLeft = (ImageButton) findViewById(R.id.center_left);
        centerCenter = (ImageButton) findViewById(R.id.center_center);
        centerRight = (ImageButton) findViewById(R.id.center_right);

        bottomLeft = (ImageButton) findViewById(R.id.bottom_left);
        bottomCenter = (ImageButton) findViewById(R.id.bottom_center);
        bottomRight = (ImageButton) findViewById(R.id.bottom_right);

        bottommostLeft = (ImageButton) findViewById(R.id.bottommost_left);
        bottommostCenter = (ImageButton) findViewById(R.id.bottommost_center);
        bottommostRight = (ImageButton) findViewById(R.id.bottommost_right);


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
            imageButton.setColorFilter(Color.argb(100, 255, 255, 150), PorterDuff.Mode.LIGHTEN);
            correctSelections++;
        }
        else{
            incorrectSelections++;
        }

        if(correctSelections == spiderButtons.size()){
            AlertDialog successAlert = AlertUtility.createGameCompletedAlert(this);
            successAlert.show();
        }
    }

    public void giveUp(View v){
        super.giveUp(v);
    }
}
