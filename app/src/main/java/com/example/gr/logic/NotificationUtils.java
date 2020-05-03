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

import static com.example.gr.App.CHANNEL_ID;
import static com.example.gr.App.NOTIFICATION_ID;

/**
 * Class that contains notification functions.
 *
 * @author Santeri Silvennoinen (@silsanteri)
 * @version 1.0 04/2020
 */

public class NotificationUtils {
    //TODO COMMENTS + JAVADOCS

    /**
     * A function that starts repeating notifications via AlarmManager.
     *
     * @param context
     * @param notificationId
     * @param notificationTitle
     * @param notificationText
     */
    public static void sendNotification(Context context, int notificationId, String notificationTitle, String notificationText) {
        //TODO GET NOTIFICATION TITLE AND TEXT FROM CONTEXT INSTEAD OF AS PARAMETER
        //SOURCE 1: https://stackoverflow.com/questions/36902667/how-to-schedule-notification-in-android
        //SOURCE 2: https://developer.android.com/training/scheduling/alarms

        long notificationDelay = MathUtils.minutesToMilliseconds(SharedPrefsUtils.returnNotificationFrequency(context));
        boolean notificationSound = SharedPrefsUtils.returnNotificationSound(context);

        // CREATES THE NOTIFICATION
        Notification notification = createNotification(context, notificationId, notificationTitle, notificationText, notificationSound);

        // CREATES INTENT AND PUTS NOTIFICATION ID AND NOTIFICATION OBJECT TO EXTRA
        Intent notificationIntent = new Intent(context, NotificationHelper.class);
        notificationIntent.putExtra(NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(CHANNEL_ID, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                notificationId,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        // SETS ON THE REPEATING ALARM
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                notificationDelay,
                pendingIntent);
    }

    /**
     * A function that creates a notification.
     *
     * @param context
     * @param notificationId
     * @param notificationTitle
     * @param notificationText
     * @param notificationSound Boolean - True = Sound ON, False = Sound OFF.
     * @return Notification
     */
    public static Notification createNotification(Context context, int notificationId, String notificationTitle, String notificationText, boolean notificationSound) {
        //TODO FIX SOUND NOT MUTED
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (notificationSound) {
            Notification notificationUnmuted = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setContentIntent(activity)
                    .build();

            return notificationUnmuted;
        } else {
            Notification notificationMuted = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setSound(null)
                    .setContentIntent(activity)
                    .build();

            return notificationMuted;
        }
    }
}
