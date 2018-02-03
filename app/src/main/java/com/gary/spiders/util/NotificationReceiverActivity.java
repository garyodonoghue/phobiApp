package com.gary.spiders.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiverActivity extends BroadcastReceiver {

    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED");
                NotificationScheduler.setReminder(context, NotificationReceiverActivity.class);
                return;
            }
        }

        //Trigger the notification
        NotificationScheduler.showNotification(context, NotificationReceiverActivity.class,
                "You have 5 unwatched videos", "Watch them now?");

    }
}
