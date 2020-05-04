package com.example.gr.logic;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.example.gr.App.CHANNEL_ID_HIGH;
import static com.example.gr.App.CHANNEL_ID_LOW;
import static com.example.gr.App.NOTIFICATION_ID;
import static com.example.gr.App.NOTIFICATION_LAST;

/**
 * Class that sends a notification when onRecieve() is called.
 * Extends BroadcastReciever.
 *
 * @author Santeri Silvennoinen (@silsanteri)
 * @version 1.0 04/2020
 */

public class NotificationHelper extends BroadcastReceiver {

    // STATIC FINAL VARIABLES
    public static String TAG = "NotificationHelper.class";

    /**
     * onRecieve @Override
     * Sends a notification when called.
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(final Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // GETS LAST NOTIFICATION VALUE
        boolean notificationLast = intent.getBooleanExtra(NOTIFICATION_LAST, true);
        // CREATES NEW NOTIFICATION
        Notification notification;
        if (notificationLast) {
            // IF LAST NOTIFICATION WAS WITH SOUND, GET CHANNEL_ID_HIGH
            notification = intent.getParcelableExtra(CHANNEL_ID_HIGH);
            Log.d(TAG, "Notification sent! - Sound");
        } else {
            // IF LAST NOTIFICATION WAS WITHOUT SOUND, GET CHANNEL_ID_LOW
            notification = intent.getParcelableExtra(CHANNEL_ID_LOW);
            Log.d(TAG, "Notification sent! - No Sound");
        }
        // GET NOTIFICATION ID
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        // SEND THE NOTIFICATION
        notificationManager.notify(notificationId, notification);
    }
}
