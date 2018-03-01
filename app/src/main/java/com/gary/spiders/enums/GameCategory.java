package com.gary.spiders.enums;

import android.util.Log;

/**
 * Created by Gary on 04/10/2017.
 */

public enum GameCategory {
    LINGUISTIC_LOW, LINGUISTIC_MED, LINGUISTIC_HIGH, CARTOON_LOW, CARTOON_MED, CARTOON_HIGH,
    DRAWINGS_BW_LOW, DRAWINGS_BW_MED, DRAWINGS_BW_HIGH, DRAWING_COL_LOW, DRAWING_COL_MED, DRAWING_COL_HIGH,
    PHOTOS_BW_LOW, PHOTOS_BW_MED, PHOTOS_BW_HIGH, PHOTOS_COL_SMALL_LOW, PHOTOS_COL_SMALL_MED, PHOTOS_COL_SMALL_HIGH,
    PHOTOS_COL_BIG_LOW, PHOTOS_COL_BIG_MED, PHOTOS_COL_BIG_HIGH, VIDEOS_LOW, VIDEOS_MED, VIDEOS_HIGH,
    IMAGE_PICKER_NON_SPIDER_BW_IMAGES, IMAGE_PICKER_SPIDER_BW_IMAGES;

    public boolean isLinguistic(){
        return this.toString().contains("LINGUISTIC");
    }

    public boolean isCartoon(){
        return this.toString().contains("CARTOON");
    }

    public boolean isDrawingsBW(){return this.toString().contains("DRAWINGS_BW");}

    public boolean isDrawingsColor(){
        return this.toString().contains("DRAWING_COL");
    }

    public boolean isPhotosBW(){
        return this.toString().contains("PHOTOS_BW");
    }

    public boolean isPhotosColSmall(){ return this.toString().contains("PHOTOS_COL_SMALL"); }

    public boolean isPhotosColBig(){
        return this.toString().contains("PHOTOS_COL_BIG");
    }

    public boolean isVideo(){
        return this.toString().contains("VIDEO");
    }

    public static GameCategory getCategory(int userLevel){
        Log.d("GameCategory","Getting category for user level="+userLevel);
        GameCategory gameCategory = null;

        if(userLevel <= 1){
            gameCategory = GameCategory.LINGUISTIC_LOW;
        }
        else if(userLevel > 1 && userLevel <= 3){
            gameCategory = GameCategory.LINGUISTIC_MED;
        }
        else if(userLevel > 3 && userLevel <= 5){
            gameCategory = GameCategory.LINGUISTIC_HIGH;
        }
        else if(userLevel > 5 && userLevel <= 7){
            gameCategory = GameCategory.CARTOON_LOW;
        }
        else if(userLevel > 7 && userLevel <= 11){
            gameCategory = GameCategory.CARTOON_MED;
        }
        else if(userLevel > 11 && userLevel <= 15){
            gameCategory = GameCategory.CARTOON_HIGH;
        }
        else if(userLevel > 15 && userLevel <= 18){
            gameCategory = GameCategory.DRAWINGS_BW_LOW;
        }
        else if(userLevel > 18 && userLevel <= 24){
            gameCategory = GameCategory.DRAWINGS_BW_MED;
        }
        else if(userLevel > 30 && userLevel <= 33){
            gameCategory = GameCategory.DRAWINGS_BW_HIGH;
        }
        else if(userLevel > 33 && userLevel <= 39){
            gameCategory = GameCategory.DRAWING_COL_LOW;
        }
        else if(userLevel > 39 && userLevel <= 45){
            gameCategory = GameCategory.DRAWING_COL_MED;
        }
        else if(userLevel > 45 && userLevel <= 48){
            gameCategory = GameCategory.DRAWING_COL_HIGH;
        }
        else if(userLevel > 48 && userLevel <= 54){
            gameCategory = GameCategory.PHOTOS_BW_LOW;
        }
        else if(userLevel > 54 && userLevel <= 60){
            gameCategory = GameCategory.PHOTOS_BW_MED;
        }
        else if(userLevel > 60 && userLevel <= 63){
            gameCategory = GameCategory.PHOTOS_BW_HIGH;
        }
        else if(userLevel > 63 && userLevel <= 69){
            gameCategory = GameCategory.PHOTOS_COL_SMALL_LOW;
        }
        else if(userLevel > 69 && userLevel <= 75){
            gameCategory = GameCategory.PHOTOS_COL_SMALL_MED;
        }
        else if(userLevel > 75 && userLevel <= 82){
            gameCategory = GameCategory.PHOTOS_COL_SMALL_HIGH;
        }
        else if(userLevel > 82 && userLevel <= 90){
            gameCategory = GameCategory.PHOTOS_COL_BIG_LOW;
        }
        else if(userLevel > 90 && userLevel <= 108){
            gameCategory = GameCategory.PHOTOS_COL_BIG_MED;
        }
        else if(userLevel > 108 && userLevel <= 116){
            gameCategory = GameCategory.PHOTOS_COL_BIG_HIGH;
        }
        else if(userLevel > 116 && userLevel <= 124){
            gameCategory = GameCategory.VIDEOS_LOW;
        }
        else if(userLevel > 124 && userLevel <= 132){
            gameCategory = GameCategory.VIDEOS_MED;
        }
        else if(userLevel > 132 && userLevel <= 140){
            gameCategory = GameCategory.VIDEOS_HIGH;
        }

        Log.d("GameCategory","Category="+gameCategory);
        return gameCategory;
    }

    public static GameCategory[] getInitialAssessmentCategories(){
        return new GameCategory[]{LINGUISTIC_HIGH, CARTOON_HIGH, DRAWINGS_BW_HIGH,
                DRAWING_COL_HIGH, PHOTOS_BW_HIGH, PHOTOS_COL_SMALL_HIGH, PHOTOS_COL_BIG_HIGH, VIDEOS_HIGH };
    }

    public int getBeginnerLevelForCategory(){
        switch(this) {
            case LINGUISTIC_HIGH:return 4;
            case CARTOON_HIGH:return 12;
            case DRAWINGS_BW_HIGH: return 31;
            case DRAWING_COL_HIGH:return 46;
            case PHOTOS_BW_HIGH:return 61;
            case PHOTOS_COL_SMALL_HIGH:return 76;
            case PHOTOS_COL_BIG_HIGH:return 109;
            case VIDEOS_HIGH:return 133;
            default:return 0;
        }
    }

    public int getUserLevelFromPoints(int numPoints){
        return (numPoints / 2); // TODO Confirm this will work as expected, i.e. ronud down to the nearest whole number when returned as an in
    }


    public int getNumPointsForLevel(int level){
        return level*10;
    }
}
