package com.gary.spiders.game;

import android.content.Context;
import android.content.res.TypedArray;

import com.gary.spiders.enums.GameCategory;

import java.util.Random;

/**
 * Created by Gary on 18/09/2017.
 */

public class GameResourceLoader {

    private Context context;

    TypedArray unscrambleWords;
    static int unscambleImageCounter = 1;

    public GameResourceLoader(Context context){
        this.context = context;
        int categoryResourceArray = this.context.getResources().getIdentifier("UNSCRAMBLE_WORDS", "array", this.context.getPackageName());
        unscrambleWords = this.context.getResources().obtainTypedArray(categoryResourceArray);
    }

    public int getResource(GameCategory category){
        // this will retrieve an array of images within a specific category (e.g. CARTOON_LOW)
        int categoryResourceArray = this.context.getResources().getIdentifier(category.toString(), "array", this.context.getPackageName());
        // get a random resource within that array of resources (images/text/video) for that specific category

        TypedArray resources = this.context.getResources().obtainTypedArray(categoryResourceArray);
        int resourceId = resources.getResourceId(new Random().nextInt(resources.length()), -1);
        return resourceId;
    }

    public int getResourceArray(GameCategory category){
        // this will retrieve an array of images within a specific category (e.g. CARTOON_LOW)
        int categoryResourceArray = this.context.getResources().getIdentifier(category.toString(), "array", this.context.getPackageName());
        return categoryResourceArray;
    }

    public int getNonSpiderResourceArray(GameCategory category){
        // this will retrieve an array of images within a specific category (e.g. CARTOON_LOW)
        int categoryResourceArray = this.context.getResources().getIdentifier("NON_SPIDER_"+category.toString(), "array", this.context.getPackageName());
        return categoryResourceArray;
    }

    public int getUnscrambleGameWord(){
        int resourceId = unscrambleWords.getResources().getIdentifier("UNSCRAMBLE_"+unscambleImageCounter, "array", this.context.getPackageName());
        unscambleImageCounter++;

        return resourceId;
    }
}
