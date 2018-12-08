package com.gary.spiders.util;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;

import com.gary.spiders.R;

import java.util.Calendar;

import butterknife.BindView;

public class ReminderScreen extends AppCompatActivity {

    @BindView(R.id.hours) NumberPicker hours;
    @BindView(R.id.mins) NumberPicker mins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_screen);

        hours.setMinValue(0);
        hours.setMaxValue(24);
        mins.setMinValue(0);
        mins.setMaxValue(60);
    }

    public void setReminder(View v){
        // Use AlarmManager with Notifications to schedule a notification to be executed
        // https://stackoverflow.com/questions/23836699/how-to-show-a-notification-even-app-is-not-running

        // TODO Convert the selection into alarms to schedule
        // Taken from - https://github.com/jaisonfdo/RemindMe/blob/master/app/src/main/java/com/droidmentor/remindme/MainActivity.java

        Notification notification = getNotification("Time to test yourself");

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get the difference in time between the scheduled alarm from the number picker and the current time
        Calendar calendar = Calendar.getInstance();
        int alarmHoursVal = hours.getValue();
        int alarmMinsVal = mins.getValue();

        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMin = calendar.get(Calendar.MINUTE);

        int hourDiff;
        if(alarmHoursVal < currentHour){
            hourDiff = 24 - currentHour;
            hourDiff = hourDiff + alarmHoursVal;
        }
        else if(alarmHoursVal > currentHour){
            hourDiff = alarmHoursVal - currentHour;
        }
        else {
            hourDiff = 0;
        }

        int minDiff = 0;
        if(alarmMinsVal < currentMin){
            minDiff = 60 - currentMin;
            minDiff = minDiff + alarmMinsVal;
        }
        else if(alarmMinsVal > currentMin){
            minDiff = alarmMinsVal - currentMin;
        }

        long dayInMillis = 86400000;
        long hourInMillis = 3600000;
        long minInMillis = 60000;

        long firstAlarmInMillis = (hourDiff * hourInMillis) + (minDiff * minInMillis);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstAlarmInMillis, dayInMillis, pendingIntent);
    }

    @SuppressLint("NewApi")
    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("PhobiApp Reminder!");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_round);
        return builder.build();
    }
}
