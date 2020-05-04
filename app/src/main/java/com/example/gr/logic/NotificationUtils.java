package com.example.gr.logic;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

import com.example.gr.MainActivity;
import com.example.gr.R;

import static com.example.gr.App.CHANNEL_ID_HIGH;
import static com.example.gr.App.CHANNEL_ID_LOW;
import static com.example.gr.App.NOTIFICATION_ID;
import static com.example.gr.App.NOTIFICATION_LAST;
import static com.example.gr.App.notificationIdHigh;
import static com.example.gr.App.notificationIdLow;

/**
 * Class that contains notification functions.
 *
 * @author Santeri Silvennoinen (@silsanteri)
 * @version 1.0 04/2020
 */

public class NotificationUtils {

    // VARIABLES
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Notification notification;
    private boolean notificationLast;

    /**
     * Constructor for all initial settings.
     * Creates AlarmManager and PendingIntent variables.
     *
     * @param context
     */
    public NotificationUtils(Context context) {
        // CREATES NOTIFICATION
        createNotification(context);
        // GETS THE LAST NOTIFICATIONTYPE
        this.notificationLast = SharedPrefsUtils.returnGameModeStatus(context);
        // INITIATES ALARMMANAGER
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    }

    /**
     * Starts repeating notifications.
     */
    public void startNotifications(Context context) {
        // GETS NOTIFICATION DELAY VARIABLE FROM SHAREDPREFS
        int notificationDelay = MathUtils.minutesToMilliseconds(SharedPrefsUtils.returnNotificationFrequency(context));
        long notificationFirst = System.currentTimeMillis() + notificationDelay;
        // CREATES NOTIFICATION
        createNotification(context);
        // STARTS REPEATING NOTIFICATIONS
        this.alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                notificationFirst,
                notificationDelay,
                this.pendingIntent);
    }

    /**
     * Stops repeating notifications.
     */
    public void stopNotifications() {
        // STOP REPEATING NOTIFICATIONS
        this.alarmManager.cancel(this.pendingIntent);
    }

    /**
     * Returns a raw notification.
     * Checks user settings and customizes the notification accordingly.
     *
     * @param context
     * @return Notification
     */
    public Notification createRawNotification(Context context) {
        //GETS NOTIFICATION SOUND BOOLEAN FROM SHAREDPREFS
        boolean notificationSound = SharedPrefsUtils.returnGameModeStatus(context);
        // CREATES TEMPORARY INTENT FOR PENDING INTENT
        Intent tempIntent = new Intent(context, MainActivity.class);
        if (notificationSound) {
            // CREATES PENDING INTENT FOR ACTIVITY
            PendingIntent activity = PendingIntent.getActivity(context, notificationIdHigh, tempIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            // IF NOTIFICATION SOUND SETTING IS ON CREATES A NOTIFICATION WITH SOUNDS
            // USES HIGH PRIORITY TO BE ABLE TO USE SOUND
            Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID_HIGH)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(context.getResources().getString(R.string.app_name))
                    .setContentText(context.getResources().getString(R.string.notification_content))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentIntent(activity)
                    .build();
            // RETURNS NOTIFICATION
            this.notificationLast = true;
            return notification;
        } else {
            // CREATES PENDING INTENT FOR ACTIVITY
            PendingIntent activity = PendingIntent.getActivity(context, notificationIdLow, tempIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            // IF NOTIFICATION SOUND SETTING IS OFF CREATES A NOTIFICATION WITHOUT SOUNDS
            // USES LOW PRIORITY TO MUTE SOUND
            Notification notificationMuted = new NotificationCompat.Builder(context, CHANNEL_ID_LOW)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(context.getResources().getString(R.string.app_name))
                    .setContentText(context.getResources().getString(R.string.notification_content))
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .setSound(null)
                    .setContentIntent(activity)
                    .build();
            // RETURNS NOTIFICATION
            this.notificationLast = false;
            return notificationMuted;
        }
    }

    /**
     * Creates a full notification.
     *
     * @param context
     */
    public void createNotification(Context context) {
        // GETS RAW NOTIFICATION
        this.notification = createRawNotification(context);
        // CREATES INTENT FOR THE PENDINGINTENT
        Intent notificationIntent = new Intent(context, NotificationHelper.class);
        if (this.notificationLast) {
            // IF LAST NOTIFICATION IS NOT MUTED CREATES THE NOTIFICATION WITH HIGH IMPORTANCE
            // PUTS EXTRA INFO TO THE INTENT
            notificationIntent.putExtra(NOTIFICATION_ID, notificationIdHigh);
            notificationIntent.putExtra(CHANNEL_ID_HIGH, notification);
            notificationIntent.putExtra(NOTIFICATION_LAST, notificationLast);
            // UPDATES THE PENDINGINTENT WITH APPROPRIATE VALUES
            this.pendingIntent = PendingIntent.getBroadcast(
                    context,
                    notificationIdHigh,
                    notificationIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        } else {
            // IF LAST NOTIFICATION IS NOT MUTED CREATES THE NOTIFICATION WITH LOW IMPORTANCE
            // PUTS EXTRA INFO TO THE INTENT
            notificationIntent.putExtra(NOTIFICATION_ID, notificationIdLow);
            notificationIntent.putExtra(CHANNEL_ID_LOW, notification);
            notificationIntent.putExtra(NOTIFICATION_LAST, notificationLast);
            // UPDATES THE PENDINGINTENT WITH APPROPRIATE VALUES
            this.pendingIntent = PendingIntent.getBroadcast(
                    context,
                    notificationIdLow,
                    notificationIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        }
    }
}
