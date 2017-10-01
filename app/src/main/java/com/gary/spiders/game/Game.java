package com.gary.spiders.game;

/**
 * Created by Gary on 18/09/2017.
 */

public interface Game {

    /**
     *
     * Method used to create a game, for a user at a particular level. The initialAssessment flag is used to add a 'tap out' button,
     * which would only be visible during the initial assessment run. Also when it is the initial assessment,
     * the user score should be set to the appropriate level depending on how far they get through it
     *
     * @param category
     * @param initialAssessment
     */
    public void setupGame(GameGenerator.Category category, boolean initialAssessment);

}