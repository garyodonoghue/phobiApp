package com.gary.spiders.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Gary on 04/10/2017.
 */
public enum GameMode {
    WORDSEARCH, JIGSAW, IMAGE_PICKER, ZOOM, FOCUS, PLAY;


    public static GameMode retrieveGameMode(GameCategory category) {
        GameMode gameMode = null;

        Random randomiser = new Random();
        if(category.isLinguistic()){
            List<GameMode> applicableGameModes = new ArrayList<>();
            //applicableGameModes.add(GameMode.ZOOM);
            applicableGameModes.add(GameMode.WORDSEARCH);

            gameMode = applicableGameModes.get(randomiser.nextInt(applicableGameModes.size()));
        }
        else if(category.isCartoon()){
            List<GameMode> applicableGameModes = new ArrayList<>();
            //applicableGameModes.add(GameMode.FOCUS);
            //applicableGameModes.add(GameMode.JIGSAW);
            applicableGameModes.add(GameMode.IMAGE_PICKER);

            gameMode = applicableGameModes.get(randomiser.nextInt(applicableGameModes.size()));
        }
        else if(category.isDrawingsBW()){
            List<GameMode> applicableGameModes = new ArrayList<>();
            //applicableGameModes.add(GameMode.FOCUS);
            //applicableGameModes.add(GameMode.JIGSAW);

            applicableGameModes.add(GameMode.IMAGE_PICKER);

            gameMode = applicableGameModes.get(randomiser.nextInt(applicableGameModes.size()));
        }
        else if(category.isDrawingsColor()){
            List<GameMode> applicableGameModes = new ArrayList<>();
            //applicableGameModes.add(GameMode.FOCUS);
            //applicableGameModes.add(GameMode.JIGSAW);

            applicableGameModes.add(GameMode.IMAGE_PICKER);

            gameMode = applicableGameModes.get(randomiser.nextInt(applicableGameModes.size()));
        }
        else if(category.isPhotosSmall()){
            List<GameMode> applicableGameModes = new ArrayList<>();
            //applicableGameModes.add(GameMode.FOCUS);
            //applicableGameModes.add(GameMode.JIGSAW);

            applicableGameModes.add(GameMode.IMAGE_PICKER);

            gameMode = applicableGameModes.get(randomiser.nextInt(applicableGameModes.size()));
        }
        else if(category.isPhotosBig()){
            List<GameMode> applicableGameModes = new ArrayList<>();
            //applicableGameModes.add(GameMode.FOCUS);
            //applicableGameModes.add(GameMode.JIGSAW);

            applicableGameModes.add(GameMode.IMAGE_PICKER);

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
            case IMAGE_PICKER: return new ImagePickerGame();
            case PLAY: return new PlayStopVideoGame();
            case WORDSEARCH: return new WordSearchActivity();

            default: throw new IllegalArgumentException();
        }
    }
}
