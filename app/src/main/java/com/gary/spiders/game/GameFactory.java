package com.gary.spiders.game;

import android.support.annotation.NonNull;

import com.gary.spiders.enums.GameCategory;
import com.gary.spiders.enums.GameMode;

import static com.gary.spiders.enums.GameCategory.getCategory;

/**
 * Created by Gary on 18/09/2017.
 */

public class GameFactory {

    public static BaseGame generateGame(int userLevel, boolean initialAssessment){
        GameCategory category = getCategory(userLevel);
        return getGame(initialAssessment, category);
    }

    @NonNull
    private static BaseGame getGame(boolean initialAssessment, GameCategory category) {
        GameMode gameMode = GameMode.retrieveGameMode(category);
        BaseGame game = getGameInstance(gameMode);
        game.category = category;
        game.initialAssessment = initialAssessment;

        return game;
    }

    private static BaseGame getGameInstance(GameMode gameMode){
        switch(gameMode){
            case FOCUS: return new FocusImageGame();
            case ZOOM: return new ZoomTextActivity();
            case JIGSAW: return new JigsawPuzzleGame();
            case IMAGE_PICKER: return new ImagePickerGame();
            case PLAY: return new PlayStopVideoGame();
            case WORDSEARCH: return new WordSearchActivity();
            case POPUP_IMAGES: return new PopupImageGame();

            default: throw new IllegalArgumentException();
        }
    }

    public static BaseGame generateGame(GameCategory category, boolean initialAssessment){
        return getGame(initialAssessment, category);
    }
}
