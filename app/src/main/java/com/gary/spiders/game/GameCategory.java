package com.gary.spiders.game;

/**
 * Created by Gary on 04/10/2017.
 */

public enum GameCategory {
    LINGUISTIC_LOW, LINGUISTIC_MED, LINGUISTIC_HIGH, CARTOON_LOW, CARTOON_MED, CARTOON_HIGH,
    DRAWINGS_BW_LOW, DRAWINGS_BW_MED, DRAWINGS_BW_HIGH, DRAWING_COL_LOW, DRAWING_COL_MED, DRAWING_COL_HIGH,
    PHOTOS_SMALL_LOW, PHOTOS_SMALL_MED, PHOTOS_SMALL_HIGH, PHOTOS_BIG_LOW, PHOTOS_BIG_MED, PHOTOS_BIG_HIGH,
    VIDEOS_LOW, VIDEOS_MED, VIDEOS_HIGH, NON_SPIDER_IMAGES;

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

    public boolean isPhotosSmall(){ return this.toString().contains("PHOTOS_SMALL"); }

    public boolean isPhotosBig(){
        return this.toString().contains("PHOTOS_BIG");
    }

    public boolean isVideo(){
        return this.toString().contains("VIDEO");
    }

    public static GameCategory getCategory(int userLevel){
        GameCategory gameLevel = null;

        if(userLevel <= 1){
            gameLevel = GameCategory.LINGUISTIC_LOW;
        }
        else if(userLevel > 1 && userLevel <= 3){
            gameLevel = GameCategory.LINGUISTIC_MED;
        }
        else if(userLevel > 3 && userLevel <= 5){
            gameLevel = GameCategory.LINGUISTIC_HIGH;
        }
        else if(userLevel > 5 && userLevel <= 7){
            gameLevel = GameCategory.CARTOON_LOW;
        }
        else if(userLevel > 7 && userLevel <= 11){
            gameLevel = GameCategory.CARTOON_MED;
        }
        else if(userLevel > 11 && userLevel <= 15){
            gameLevel = GameCategory.CARTOON_HIGH;
        }
        else if(userLevel > 15 && userLevel <= 18){
            gameLevel = GameCategory.DRAWINGS_BW_LOW;
        }
        else if(userLevel > 18 && userLevel <= 24){
            gameLevel = GameCategory.DRAWINGS_BW_MED;
        }
        else if(userLevel > 24 && userLevel <= 30){
            gameLevel = GameCategory.DRAWINGS_BW_HIGH;
        }
        else if(userLevel > 30 && userLevel <= 33){
            gameLevel = GameCategory.DRAWING_COL_LOW;
        }
        else if(userLevel > 33 && userLevel <= 39){
            gameLevel = GameCategory.DRAWING_COL_MED;
        }
        else if(userLevel > 39 && userLevel <= 45){
            gameLevel = GameCategory.DRAWING_COL_HIGH;
        }
        else if(userLevel > 45 && userLevel <= 48){
            gameLevel = GameCategory.PHOTOS_SMALL_LOW;
        }
        else if(userLevel > 48 && userLevel <= 54){
            gameLevel = GameCategory.PHOTOS_SMALL_MED;
        }
        else if(userLevel > 54 && userLevel <= 60){
            gameLevel = GameCategory.PHOTOS_SMALL_HIGH;
        }
        else if(userLevel > 60 && userLevel <= 63){
            gameLevel = GameCategory.PHOTOS_BIG_LOW;
        }
        else if(userLevel > 63 && userLevel <= 69){
            gameLevel = GameCategory.PHOTOS_BIG_MED;
        }
        else if(userLevel > 69 && userLevel <= 75){
            gameLevel = GameCategory.PHOTOS_BIG_HIGH;
        }
        else if(userLevel > 75 && userLevel <= 82){
            gameLevel = GameCategory.VIDEOS_LOW;
        }
        else if(userLevel > 82 && userLevel <= 90){
            gameLevel = GameCategory.VIDEOS_MED;
        }
        else if(userLevel > 90 && userLevel <= 100){
            gameLevel = GameCategory.VIDEOS_HIGH;
        }

        return gameLevel;
    }

    public static GameCategory[] getInitialAssessmentCategories(){
        return new GameCategory[]{LINGUISTIC_HIGH, CARTOON_HIGH, DRAWINGS_BW_HIGH,
                DRAWING_COL_HIGH, PHOTOS_SMALL_HIGH, PHOTOS_BIG_HIGH, VIDEOS_HIGH };
    }

    public int getBeginnerLevelForCategory(){
        switch(this) {
            case LINGUISTIC_HIGH:return 0;
            case CARTOON_HIGH:return 6;
            case DRAWINGS_BW_HIGH: return 16;
            case DRAWING_COL_HIGH:return 31;
            case PHOTOS_SMALL_HIGH:return 46;
            case PHOTOS_BIG_HIGH:return 61;
            case VIDEOS_HIGH:return 76;
            default:return 0;
        }
    }
}
