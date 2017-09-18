package com.gary.spiders.game;

/**
 * Created by Gary on 18/09/2017.
 */

public class GameGenerator {

    public Game generateGame(int userLevel){
        GameCategory category = retrieveGameCategory();
        Game game = null;

        if(GameCategory.FOCUS == category){
            Game focusImageGame = new FocusImageGame();
            focusImageGame.setupGame(userLevel);
        }

        // TODO for the other game categories

        return game;
    }

    private GameCategory retrieveGameCategory() {
        return null;
        // TODO Pick random game mode from enum types
    }

    public enum GameCategory {

        CROSSWORD, JIGSAW, IMAGE_PICKER, ZOOM, FOCUS
    }
}
