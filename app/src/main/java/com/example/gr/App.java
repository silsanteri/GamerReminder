package com.example.gr;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

/**
 * Class that contains notification scripts.
 *
 * @author Santeri Silvennoinen (@silsanteri)
 * @version 1.0 04/2020
 */

public class App extends Application {
    //SOURCE: https://www.youtube.com/playlist?list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM

    // STATIC FINAL VARIABLES
    public static final String CHANNEL_ID = "notification";
    public static final String NOTIFICATION_ID = "notification_id";

    /**
     * onCreate @Override
     * Creates notification channels.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    /**
     * Creates notification channel.
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // BUILDS THE NEW NOTIFICATION CHANNEL
            NotificationChannel notification = new NotificationChannel(
                    CHANNEL_ID,
                    "Reminder",
                    NotificationManager.IMPORTANCE_HIGH
            );
            // SETS THE NOTIFICATION DESCRIPTION TO "Reminder"
            notification.setDescription("Reminder");
            NotificationManager manager = getSystemService(NotificationManager.class);
            // CREATES NOTIFICATION CHANNEL
            manager.createNotificationChannel(notification);
        }
    }
}
