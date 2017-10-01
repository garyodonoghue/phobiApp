package com.gary.spiders.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Gary on 18/09/2017.
 */

public class GameGenerator {

    public static Game generateGame(int userLevel, boolean initialAssessment){
        Category category = getCategory(userLevel);
        GameType gameType = retrieveGameType(category);
        Game game = null;

        if(GameType.FOCUS == gameType){
            game = new FocusImageGame();
        }
        else if(GameType.ZOOM == gameType){
            game = new ZoomTextActivity();
        }
        else if(GameType.JIGSAW == gameType){
            game = new JigsawPuzzleGame();
        }

        game.category = category;
        game.initialAssessment = initialAssessment;

        return game;
    }

    private static GameType retrieveGameType(Category category) {
        // passing in category here as not all gaem types are applicable to user category, e.g. jigsaw isnt applicable to LINGUISTIC categories,
        // e.g. jigsaw probably doesnt make sense for Linguistic categories, since its just text, but Focusing and Enlarging the text would, so those
        // game types would be applicable

        if(category.toString().contains("LINGUISTIC")){
            List<GameType> applicableGameTypes = new ArrayList<>();
            applicableGameTypes.add(GameType.ZOOM);
            // TODO applicableGameTypes.add(GameType.FOCUS);

            Random randomiser = new Random();
            GameType gameType = applicableGameTypes.get(randomiser.nextInt(applicableGameTypes.size()));

            return gameType;
        }
        return GameType.ZOOM;
    }

    public enum GameType {
        CROSSWORD, JIGSAW, IMAGE_PICKER, ZOOM, FOCUS
    }

    public static Category getCategory(int userLevel){
        Category gameLevel = null;

        if(userLevel <= 1){
            gameLevel = Category.LINGUISTIC_LOW;
        }
        else if(userLevel > 1 && userLevel <= 3){
            gameLevel = Category.LINGUISTIC_MED;
        }
        else if(userLevel > 3 && userLevel <= 5){
            gameLevel = Category.LINGUISTIC_HIGH;
        }
        else if(userLevel > 5 && userLevel <= 7){
            gameLevel = Category.CARTOON_LOW;
        }
        else if(userLevel > 7 && userLevel <= 11){
            gameLevel = Category.CARTOON_MED;
        }
        else if(userLevel > 11 && userLevel <= 15){
            gameLevel = Category.CARTOON_HIGH;
        }
        else if(userLevel > 15 && userLevel <= 18){
            gameLevel = Category.DRAWINGS_BW_LOW;
        }
        else if(userLevel > 18 && userLevel <= 24){
            gameLevel = Category.DRAWINGS_BW_MED;
        }
        else if(userLevel > 24 && userLevel <= 30){
            gameLevel = Category.DRAWINGS_BW_HIGH;
        }
        else if(userLevel > 30 && userLevel <= 33){
            gameLevel = Category.DRAWING_COL_LOW;
        }
        else if(userLevel > 33 && userLevel <= 39){
            gameLevel = Category.DRAWING_COL_MED;
        }
        else if(userLevel > 39 && userLevel <= 45){
            gameLevel = Category.DRAWING_COL_HIGH;
        }
        else if(userLevel > 45 && userLevel <= 48){
            gameLevel = Category.PHOTOS_SMALL_LOW;
        }
        else if(userLevel > 48 && userLevel <= 54){
            gameLevel = Category.PHOTOS_SMALL_MED;
        }
        else if(userLevel > 54 && userLevel <= 60){
            gameLevel = Category.PHOTOS_SMALL_HIGH;
        }
        else if(userLevel > 60 && userLevel <= 63){
            gameLevel = Category.PHOTOS_BIG_LOW;
        }
        else if(userLevel > 63 && userLevel <= 69){
            gameLevel = Category.PHOTOS_BIG_MED;
        }
        else if(userLevel > 69 && userLevel <= 75){
            gameLevel = Category.PHOTOS_BIG_HIGH;
        }
        else if(userLevel > 75 && userLevel <= 82){
            gameLevel = Category.VIDEOS_LOW;
        }
        else if(userLevel > 82 && userLevel <= 90){
            gameLevel = Category.VIDEOS_MED;
        }
        else if(userLevel > 90 && userLevel <= 100){
            gameLevel = Category.VIDEOS_HIGH;
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
