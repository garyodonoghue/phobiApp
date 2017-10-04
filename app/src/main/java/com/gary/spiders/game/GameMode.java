package com.gary.spiders.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Gary on 04/10/2017.
 */
public enum GameMode {
    CROSSWORD, JIGSAW, IMAGE_PICKER, ZOOM, FOCUS, PLAY;


    public static GameMode retrieveGameMode(GameCategory category) {
        GameMode gameMode = null;

        Random randomiser = new Random();
        if(category.isLinguistic()){
            List<GameMode> applicableGameModes = new ArrayList<>();
            applicableGameModes.add(GameMode.ZOOM);

            gameMode = applicableGameModes.get(randomiser.nextInt(applicableGameModes.size()));
        }
        else if(category.isCartoon()){
            List<GameMode> applicableGameModes = new ArrayList<>();
            applicableGameModes.add(GameMode.FOCUS);

            gameMode = applicableGameModes.get(randomiser.nextInt(applicableGameModes.size()));
        }
        else if(category.isDrawingsBW()){
            List<GameMode> applicableGameModes = new ArrayList<>();
            applicableGameModes.add(GameMode.FOCUS);

            gameMode = applicableGameModes.get(randomiser.nextInt(applicableGameModes.size()));
        }
        else if(category.isDrawingsColor()){
            List<GameMode> applicableGameModes = new ArrayList<>();
            applicableGameModes.add(GameMode.FOCUS);

            gameMode = applicableGameModes.get(randomiser.nextInt(applicableGameModes.size()));
        }
        else if(category.isPhotosSmall()){
            List<GameMode> applicableGameModes = new ArrayList<>();
            applicableGameModes.add(GameMode.FOCUS);

            gameMode = applicableGameModes.get(randomiser.nextInt(applicableGameModes.size()));
        }
        else if(category.isPhotosBig()){
            List<GameMode> applicableGameModes = new ArrayList<>();
            applicableGameModes.add(GameMode.FOCUS);

            gameMode = applicableGameModes.get(randomiser.nextInt(applicableGameModes.size()));
        }
        else if(category.isVideo()){
            List<GameMode> applicableGameModes = new ArrayList<>();
            applicableGameModes.add(GameMode.PLAY);

            gameMode = applicableGameModes.get(randomiser.nextInt(applicableGameModes.size()));
        }

        return gameMode;
    }

    public Game getGameInstance(){
        switch(this){
            case FOCUS: return new FocusImageGame();
            case ZOOM: return new ZoomTextActivity();
            case JIGSAW: return new JigsawPuzzleGame();
            case IMAGE_PICKER: return new JigsawPuzzleGame(); // TODO
            case PLAY: return new PlayStopVideoGame();
            case CROSSWORD: return new JigsawPuzzleGame(); // TODO

            default: throw new IllegalArgumentException();
        }
    }
}
