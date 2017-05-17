package com.gary.spiders.activity;

import android.view.View;

/**
 * Created by Gary on 17/05/2017.
 */
interface ISpiderExercise {

    /**
     * Save the rating the user gave the exercise along with the activity name and the current time, so we can track progress
     * for a particular exercise over time.
     *
     * @param view
     */
    void ratingClicked(View view);
}
