package com.gary.spiders.util;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.SharedPreferences;

import com.gary.spiders.activity.MainMenuActivity;

/**
 * Created by Gary on 31/10/2018.
 */

public class LifecycleListener extends Activity implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onMoveToForeground() {
        SharedPreferences userData = getSharedPreferences("Progress", 0);
        SharedPreferences.Editor editor = userData.edit();
        editor.putString(""+EpochUtil.getEpochTime(), Integer.toString(MainMenuActivity.user.getLevel()));
        editor.commit();
    }
}
