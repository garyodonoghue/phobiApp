package com.gary.spiders.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.gary.spiders.R;

public class ReminderScreen extends AppCompatActivity {

    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_screen);

        monday = (CheckBox) findViewById(R.id.checkbox_reminder_monday);
        tuesday = (CheckBox) findViewById(R.id.checkbox_reminder_tuesday);
        wednesday = (CheckBox) findViewById(R.id.checkbox_reminder_wednesday);
        thursday = (CheckBox) findViewById(R.id.checkbox_reminder_thursday);
        friday = (CheckBox) findViewById(R.id.checkbox_reminder_friday);

    }

    public void setReminders(View v){
        // Use AlarmManager with Notifications to schedule a notification to be executed
        // https://stackoverflow.com/questions/23836699/how-to-show-a-notification-even-app-is-not-running


        // TODO Convert the selection into alarms to schedule
        // Taken from - https://github.com/jaisonfdo/RemindMe/blob/master/app/src/main/java/com/droidmentor/remindme/MainActivity.java
        NotificationScheduler.setReminder(this, NotificationReceiverActivity.class);
    }
}
