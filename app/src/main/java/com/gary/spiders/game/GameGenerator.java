package com.gary.spiders.game;

import android.support.annotation.NonNull;

import static com.gary.spiders.game.GameCategory.getCategory;

/**
 * Created by Gary on 18/09/2017.
 */

public class GameGenerator {

    public static Game generateGame(int userLevel, boolean initialAssessment){
        GameCategory category = getCategory(userLevel);
        return getGame(initialAssessment, category);
    }

    @NonNull
    private static Game getGame(boolean initialAssessment, GameCategory category) {
        GameMode gameMode = GameMode.retrieveGameMode(category);
        Game game = gameMode.getGameInstance();
        game.category = category;
        game.initialAssessment = initialAssessment;

        return game;
    }

    public static Game generateGame(GameCategory category, boolean initialAssessment){
        return getGame(initialAssessment, category);
    }
}
