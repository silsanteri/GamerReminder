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
 * NotificationHelper
 *
 * @author Santeri Silvennoinen (@silsanteri)
 * @version 1.0 04/2020
 */

public class NotificationHelper extends BroadcastReceiver {
    //TODO COMMENTS + JAVADOCS

    // STATIC FINAL VARIABLES
    public static String TAG = "NotificationHelper.class";

    /**
     * onRecieve @Override
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(final Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        boolean notificationLast = intent.getBooleanExtra(NOTIFICATION_LAST, true);
        Notification notification;
        if (notificationLast) {
            notification = intent.getParcelableExtra(CHANNEL_ID_HIGH);
            Log.d(TAG, "Notification sent! - Sound");
        } else {
            notification = intent.getParcelableExtra(CHANNEL_ID_LOW);
            Log.d(TAG, "Notification sent! - No Sound");
        }
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(notificationId, notification);
    }
}
