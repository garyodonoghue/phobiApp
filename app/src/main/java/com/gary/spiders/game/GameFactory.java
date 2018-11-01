package com.gary.spiders.game;

import com.gary.spiders.enums.GameCategory;
import com.gary.spiders.enums.GameType;

import static com.gary.spiders.enums.GameCategory.getCategory;

/**
 * Created by Gary on 18/09/2017.
 */

public class GameFactory {

    public static BaseGame generateGameFromUserLevel(int userLevel, boolean initialAssessment){
        GameCategory category = getCategory(userLevel);
        return generateGameFromUserCategory(category, initialAssessment);
    }

    public static BaseGame generateGameFromUserCategory(GameCategory category, boolean initialAssessment) {
        GameType gameType = GameType.retrieveGameMode(category);
        BaseGame game = getGameInstance(gameType);
        game.category = category;
        game.initialAssessment = initialAssessment;

        return game;
    }

    public static BaseGame generateSpecificGame(GameType gameType){
        BaseGame game = getGameInstance(gameType);
        game.initialAssessment = false;

        return game;
    }

    public static int getHalfwayLevel(int startingLevel){
        // ignore fractional part for odd numbers, we just need a rough value
        return (GameCategory.VIDEOS_HIGHEST_LEVEL - startingLevel)/2;
    }

    private static BaseGame getGameInstance(GameType gameType){
        switch(gameType){
            case FOCUS_IMAGE: return new FocusImageGame();
            case ZOOM: return new ZoomTextGame();
            case JIGSAW: return new JigsawPuzzleGame();
            case IMAGE_PICKER: return new ImagePickerGame();
            case PLAY: return new PlayStopVideoGame();
            case WORDSEARCH: return new WordSearchGame();
            case POPUP_IMAGES: return new PopupImageGame();
            case FOCUS_TEXT: return new SharpenTextGame();

            default: throw new IllegalArgumentException();
        }
    }
}
