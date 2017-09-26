package com.gary.spiders.game;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.Random;

/**
 * Created by Gary on 18/09/2017.
 */

public class GameResourceLoader {

    private Context context;

    public GameResourceLoader(Context context){
        this.context = context;
    }

    public int getImageResourceId(int userLevel){
        Category category = getCategory(userLevel);

        // this will retrieve an array of images within a specific category (e.g. CARTOON_LOW)
        int categoryResourceArray = this.context.getResources().getIdentifier(category.toString(), "id", this.context.getPackageName());

        // get a random image within that array of images for that specific category
        TypedArray imgs = this.context.getResources().obtainTypedArray(categoryResourceArray);
        int imageId = imgs.getResourceId(new Random().nextInt(imgs.length()), -1);
        return imageId;
    }

    private static Category getCategory(int userLevel){
        Category gameLevel = null;

        if(userLevel <= 1){
            gameLevel = Category.LINGUISTIC_LOW;
        }
        else if(userLevel > 1 && userLevel <= 3){
            return Category.LINGUISTIC_MED;
        }
        else if(userLevel > 3 && userLevel <= 5){
            return Category.LINGUISTIC_HIGH;
        }
        else if(userLevel > 5 && userLevel <= 7){
            return Category.CARTOON_LOW;
        }
        else if(userLevel > 7 && userLevel <= 11){
            return Category.CARTOON_MED;
        }
        else if(userLevel > 11 && userLevel <= 15){
            return Category.CARTOON_HIGH;
        }
        else if(userLevel > 15 && userLevel <= 18){
            return Category.DRAWINGS_BW_LOW;
        }
        else if(userLevel > 18 && userLevel <= 24){
            return Category.DRAWINGS_BW_MED;
        }
        else if(userLevel > 24 && userLevel <= 30){
            return Category.DRAWINGS_BW_HIGH;
        }
        else if(userLevel > 30 && userLevel <= 33){
            return Category.DRAWING_COL_LOW;
        }
        else if(userLevel > 33 && userLevel <= 39){
            return Category.DRAWING_COL_MED;
        }
        else if(userLevel > 39 && userLevel <= 45){
            return Category.DRAWING_COL_HIGH;
        }
        else if(userLevel > 45 && userLevel <= 48){
            return Category.PHOTOS_SMALL_LOW;
        }
        else if(userLevel > 48 && userLevel <= 54){
            return Category.PHOTOS_SMALL_MED;
        }
        else if(userLevel > 54 && userLevel <= 60){
            return Category.PHOTOS_SMALL_HIGH;
        }
        else if(userLevel > 60 && userLevel <= 63){
            return Category.PHOTOS_BIG_LOW;
        }
        else if(userLevel > 63 && userLevel <= 69){
            return Category.PHOTOS_BIG_MED;
        }
        else if(userLevel > 69 && userLevel <= 75){
            return Category.PHOTOS_BIG_HIGH;
        }
        else if(userLevel > 75 && userLevel <= 82){
            return Category.VIDEOS_LOW;
        }
        else if(userLevel > 82 && userLevel <= 90){
            return Category.VIDEOS_MED;
        }
        else if(userLevel > 90 && userLevel <= 100){
            return Category.VIDEOS_HIGH;
        }

        return gameLevel;
    }

    public enum Category {
        LINGUISTIC_LOW, LINGUISTIC_MED, LINGUISTIC_HIGH, CARTOON_LOW, CARTOON_MED, CARTOON_HIGH,
        DRAWINGS_BW_LOW, DRAWINGS_BW_MED, DRAWINGS_BW_HIGH, DRAWING_COL_LOW, DRAWING_COL_MED, DRAWING_COL_HIGH,
        PHOTOS_SMALL_LOW, PHOTOS_SMALL_MED, PHOTOS_SMALL_HIGH, PHOTOS_BIG_LOW, PHOTOS_BIG_MED, PHOTOS_BIG_HIGH,
        VIDEOS_LOW, VIDEOS_MED, VIDEOS_HIGH
    }
}
