package com.example.gr.logic;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.gr.R;

import static com.example.gr.App.CHANNEL_ID;

/**
 * Class that contains notification functions.
 *
 * @author Santeri Silvennoinen (@silsanteri)
 * @version 1.0 04/2020
 */

public class NotificationUtils {

    /**
     * A function that sends a notification.
     *
     * @param context
     * @param view
     * @param notification_title String notification title.
     * @param notification_text  String notification text.
     */
    public static void sendNotification(Context context, View view, String notification_title, String notification_text) {
        //SOURCE: https://www.youtube.com/playlist?list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(notification_title)
                .setContentText(notification_text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .build();

        notificationManager.notify(1, notification);
    }
}
