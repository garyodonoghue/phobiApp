package com.gary.spiders.game;

/**
 * Created by Gary on 18/09/2017.
 */

public class GameGenerator {

    public Game generateGame(int userLevel){
        GameType category = retrieveGameType();
        Game game = null;

        if(GameType.FOCUS == category){
            Game focusImageGame = new FocusImageGame();
            focusImageGame.setupGame(userLevel);
        }

        // TODO for the other game categories

        return game;
    }

    private GameType retrieveGameType() {
        return null;
        // TODO Pick random game mode from enum types
    }

    public enum GameType {

        CROSSWORD, JIGSAW, IMAGE_PICKER, ZOOM, FOCUS
    }
}
