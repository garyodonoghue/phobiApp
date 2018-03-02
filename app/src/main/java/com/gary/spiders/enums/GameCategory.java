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

    public static final int LINGUISTIC_LOWEST_LEVEL = 4;
    public static final int LINGUISTIC_MID_LEVEL = 6;
    public static final int LINGUISTIC_HIGHEST_LEVEL = 8;
    public static final int CARTOON_LOWEST_LEVEL = 20;
    public static final int CARTOON_MEDIUM_LEVEL = 25;
    public static final int CARTOON_HIGHEST_LEVEL = 30;
    public static final int DRAWINGS_BW_LOWEST_LEVEL = 40;
    public static final int DRAWINGS_BW_MEDIUM_LEVEL = 50;
    public static final int DRAWINGS_BW_HIGHEST_LEVEL = 70;
    public static final int DRAWING_COL_LOWEST_LEVEL = 90;
    public static final int DRAWING_COL_MEDIUM_LEVEL = 110;
    public static final int DRAWING_COL_HIGHEST_LEVEL = 140;
    public static final int PHOTOS_BW_LOWEST_LEVEL = 170;
    public static final int PHOTOS_BW_MEDIUM_LEVEL = 200;
    public static final int PHOTOS_BW_HIGHEST_LEVEL = 250;
    public static final int PHOTOS_COL_SMALL_LOWEST_LEVEL = 290;
    public static final int PHOTOS_COL_SMALL_MEDIUM_LEVEL = 330;
    public static final int PHOTOS_COL_SMALL_HIGHEST_LEVEL = 360;
    public static final int PHOTOS_COL_BIG_LOWEST_LEVEL = 400;
    public static final int PHOTOS_COL_BIG_MEDIUM_LEVEL = 450;
    public static final int PHOTOS_COL_BIG_HIGHEST_LEVEL = 500;
    public static final int VIDEOS_LOWEST_LEVEL = 530;
    public static final int VIDEOS_MEDIUM_LEVEL = 550;
    public static final int VIDEOS_HIGHEST_LEVEL = 600;

    public boolean isLinguistic(){
        return this.toString().contains("LINGUISTIC");
    }

    public boolean isCartoon(){
        return this.toString().contains("CARTOON");
    }

    public boolean isDrawingsBW(){
        return this.toString().contains("DRAWINGS_BW");
    }

    public boolean isDrawingsColor(){
        return this.toString().contains("DRAWING_COL");
    }

    public boolean isPhotosBW(){
        return this.toString().contains("PHOTOS_BW");
    }

    public boolean isPhotosColSmall(){
        return this.toString().contains("PHOTOS_COL_SMALL");
    }

    public boolean isPhotosColBig(){
        return this.toString().contains("PHOTOS_COL_BIG");
    }

    public boolean isVideo(){
        return this.toString().contains("VIDEO");
    }

    public static GameCategory getCategory(int userLevel){
        Log.d("GameCategory","Getting category for user level="+userLevel);
        GameCategory gameCategory = null;

        if(userLevel <= LINGUISTIC_LOWEST_LEVEL){
            gameCategory = GameCategory.LINGUISTIC_LOW;
        }
        else if(userLevel > LINGUISTIC_LOWEST_LEVEL && userLevel <= LINGUISTIC_MID_LEVEL){
            gameCategory = GameCategory.LINGUISTIC_MED;
        }
        else if(userLevel > LINGUISTIC_MID_LEVEL && userLevel <= LINGUISTIC_HIGHEST_LEVEL){
            gameCategory = GameCategory.LINGUISTIC_HIGH;
        }
        else if(userLevel > LINGUISTIC_HIGHEST_LEVEL && userLevel <= CARTOON_LOWEST_LEVEL){
            gameCategory = GameCategory.CARTOON_LOW;
        }
        else if(userLevel > CARTOON_LOWEST_LEVEL && userLevel <= CARTOON_MEDIUM_LEVEL){
            gameCategory = GameCategory.CARTOON_MED;
        }
        else if(userLevel > CARTOON_MEDIUM_LEVEL && userLevel <= CARTOON_HIGHEST_LEVEL){
            gameCategory = GameCategory.CARTOON_HIGH;
        }
        else if(userLevel > CARTOON_HIGHEST_LEVEL && userLevel <= DRAWINGS_BW_LOWEST_LEVEL){
            gameCategory = GameCategory.DRAWINGS_BW_LOW;
        }
        else if(userLevel > DRAWINGS_BW_LOWEST_LEVEL && userLevel <= DRAWINGS_BW_MEDIUM_LEVEL){
            gameCategory = GameCategory.DRAWINGS_BW_MED;
        }
        else if(userLevel > DRAWINGS_BW_MEDIUM_LEVEL && userLevel <= DRAWINGS_BW_HIGHEST_LEVEL){
            gameCategory = GameCategory.DRAWINGS_BW_HIGH;
        }
        else if(userLevel > DRAWINGS_BW_HIGHEST_LEVEL && userLevel <= DRAWING_COL_LOWEST_LEVEL){
            gameCategory = GameCategory.DRAWING_COL_LOW;
        }
        else if(userLevel > DRAWING_COL_LOWEST_LEVEL && userLevel <= DRAWING_COL_MEDIUM_LEVEL){
            gameCategory = GameCategory.DRAWING_COL_MED;
        }
        else if(userLevel > DRAWING_COL_MEDIUM_LEVEL && userLevel <= DRAWING_COL_HIGHEST_LEVEL){
            gameCategory = GameCategory.DRAWING_COL_HIGH;
        }
        else if(userLevel > DRAWING_COL_HIGHEST_LEVEL && userLevel <= PHOTOS_BW_LOWEST_LEVEL){
            gameCategory = GameCategory.PHOTOS_BW_LOW;
        }
        else if(userLevel > PHOTOS_BW_LOWEST_LEVEL && userLevel <= PHOTOS_BW_MEDIUM_LEVEL){
            gameCategory = GameCategory.PHOTOS_BW_MED;
        }
        else if(userLevel > PHOTOS_BW_MEDIUM_LEVEL && userLevel <= PHOTOS_BW_HIGHEST_LEVEL){
            gameCategory = GameCategory.PHOTOS_BW_HIGH;
        }
        else if(userLevel > PHOTOS_BW_HIGHEST_LEVEL && userLevel <= PHOTOS_COL_SMALL_LOWEST_LEVEL){
            gameCategory = GameCategory.PHOTOS_COL_SMALL_LOW;
        }
        else if(userLevel > PHOTOS_COL_SMALL_LOWEST_LEVEL && userLevel <= PHOTOS_COL_SMALL_MEDIUM_LEVEL){
            gameCategory = GameCategory.PHOTOS_COL_SMALL_MED;
        }
        else if(userLevel > PHOTOS_COL_SMALL_MEDIUM_LEVEL && userLevel <= PHOTOS_COL_SMALL_HIGHEST_LEVEL){
            gameCategory = GameCategory.PHOTOS_COL_SMALL_HIGH;
        }
        else if(userLevel > PHOTOS_COL_SMALL_HIGHEST_LEVEL && userLevel <= PHOTOS_COL_BIG_LOWEST_LEVEL){
            gameCategory = GameCategory.PHOTOS_COL_BIG_LOW;
        }
        else if(userLevel > PHOTOS_COL_BIG_LOWEST_LEVEL && userLevel <= PHOTOS_COL_BIG_MEDIUM_LEVEL){
            gameCategory = GameCategory.PHOTOS_COL_BIG_MED;
        }
        else if(userLevel > PHOTOS_COL_BIG_MEDIUM_LEVEL && userLevel <= PHOTOS_COL_BIG_HIGHEST_LEVEL){
            gameCategory = GameCategory.PHOTOS_COL_BIG_HIGH;
        }
        else if(userLevel > PHOTOS_COL_BIG_HIGHEST_LEVEL && userLevel <= VIDEOS_LOWEST_LEVEL){
            gameCategory = GameCategory.VIDEOS_LOW;
        }
        else if(userLevel > VIDEOS_LOWEST_LEVEL && userLevel <= VIDEOS_MEDIUM_LEVEL){
            gameCategory = GameCategory.VIDEOS_MED;
        }
        else if(userLevel > VIDEOS_MEDIUM_LEVEL && userLevel <= VIDEOS_HIGHEST_LEVEL){
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
            case LINGUISTIC_HIGH:return LINGUISTIC_LOWEST_LEVEL;
            case CARTOON_HIGH:return CARTOON_LOWEST_LEVEL;
            case DRAWINGS_BW_HIGH: return DRAWINGS_BW_LOWEST_LEVEL;
            case DRAWING_COL_HIGH:return DRAWING_COL_LOWEST_LEVEL;
            case PHOTOS_BW_HIGH:return PHOTOS_BW_LOWEST_LEVEL;
            case PHOTOS_COL_SMALL_HIGH:return PHOTOS_COL_SMALL_LOWEST_LEVEL;
            case PHOTOS_COL_BIG_HIGH:return PHOTOS_COL_BIG_LOWEST_LEVEL;
            case VIDEOS_HIGH:return VIDEOS_LOWEST_LEVEL;
            default:return 0;
        }
    }
}
