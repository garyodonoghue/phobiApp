package com.gary.spiders.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Gary on 04/10/2017.
 */
public enum GameType {
    WORDSEARCH, JIGSAW, IMAGE_PICKER, ZOOM, FOCUS, PLAY, POPUP_IMAGES;


    public static GameType retrieveGameMode(GameCategory category) {
        GameType gameType = null;

        Random randomiser = new Random();
        if(category.isLinguistic()){
            List<GameType> applicableGameTypes = new ArrayList<>();
            applicableGameTypes.add(GameType.ZOOM);
            applicableGameTypes.add(GameType.WORDSEARCH);

            gameType = applicableGameTypes.get(randomiser.nextInt(applicableGameTypes.size()));
        }
        else if(category.isCartoon()){
            List<GameType> applicableGameTypes = new ArrayList<>();
            applicableGameTypes.add(GameType.FOCUS);
            applicableGameTypes.add(GameType.JIGSAW);
            applicableGameTypes.add(GameType.IMAGE_PICKER); // TODO For this level we should load the ins/sps coloured images
            applicableGameTypes.add(GameType.POPUP_IMAGES); // TODO For this level we should load the ins/sps coloured images

            gameType = applicableGameTypes.get(randomiser.nextInt(applicableGameTypes.size()));
        }
        else if(category.isDrawingsBW()){
            List<GameType> applicableGameTypes = new ArrayList<>();
            applicableGameTypes.add(GameType.FOCUS);
            applicableGameTypes.add(GameType.JIGSAW);
            applicableGameTypes.add(GameType.IMAGE_PICKER);
            applicableGameTypes.add(GameType.POPUP_IMAGES);

            gameType = applicableGameTypes.get(randomiser.nextInt(applicableGameTypes.size()));
        }
        else if(category.isDrawingsColor()){
            List<GameType> applicableGameTypes = new ArrayList<>();
            applicableGameTypes.add(GameType.FOCUS);
            applicableGameTypes.add(GameType.JIGSAW);
            applicableGameTypes.add(GameType.IMAGE_PICKER); // TODO For this use drawings coloured images already in project
            applicableGameTypes.add(GameType.POPUP_IMAGES); // TODO For this use drawings coloured images already in project

            gameType = applicableGameTypes.get(randomiser.nextInt(applicableGameTypes.size()));
        }
        else if(category.isPhotosBW()){
            List<GameType> applicableGameTypes = new ArrayList<>();
            applicableGameTypes.add(GameType.FOCUS);
            applicableGameTypes.add(GameType.JIGSAW);
            applicableGameTypes.add(GameType.IMAGE_PICKER); // TODO Use photos black/white already in project
            applicableGameTypes.add(GameType.POPUP_IMAGES); // TODO Use photos black/white already in project

            gameType = applicableGameTypes.get(randomiser.nextInt(applicableGameTypes.size()));
        }
        else if(category.isPhotosColSmall()){
            List<GameType> applicableGameTypes = new ArrayList<>();
            applicableGameTypes.add(GameType.FOCUS);
            applicableGameTypes.add(GameType.JIGSAW);

            gameType = applicableGameTypes.get(randomiser.nextInt(applicableGameTypes.size()));
        }
        else if(category.isPhotosColBig()){
            List<GameType> applicableGameTypes = new ArrayList<>();
            applicableGameTypes.add(GameType.FOCUS);
            applicableGameTypes.add(GameType.JIGSAW);

            gameType = applicableGameTypes.get(randomiser.nextInt(applicableGameTypes.size()));
        }
        else if(category.isVideo()){
            List<GameType> applicableGameTypes = new ArrayList<>();
            applicableGameTypes.add(GameType.PLAY);
            gameType = applicableGameTypes.get(randomiser.nextInt(applicableGameTypes.size()));
        }

        return gameType;
    }
}
