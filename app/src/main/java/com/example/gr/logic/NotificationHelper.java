package com.example.gr.logic;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.example.gr.App.CHANNEL_ID;
import static com.example.gr.App.NOTIFICATION_ID;

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

    @Override
    public void onReceive(final Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(CHANNEL_ID);
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(notificationId, notification);
        Log.d(TAG, "Notification sent!");
    }
}
