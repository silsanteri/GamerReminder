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
    public static final String CHANNEL_ID_HIGH = "notification_high";
    public static final String CHANNEL_ID_LOW = "notification_low";
    public static final String NOTIFICATION_ID = "notification_id";
    public static final String NOTIFICATION_LAST = "notification_last";
    public static final int notificationIdHigh = 1;
    public static final int notificationIdLow = 2;

    /**
     * onCreate @Override
     * Creates notification channels.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    /**
     * Creates two notification channels.
     * One for notifications with sound and one for notifications without sound.
     */
    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // BUILDS THE NEW NOTIFICATION CHANNEL WITH SOUND
            NotificationChannel notification_high = new NotificationChannel(
                    CHANNEL_ID_HIGH,
                    "Reminder",
                    NotificationManager.IMPORTANCE_HIGH
            );
            // SETS THE NOTIFICATION DESCRIPTION TO "Reminder"
            notification_high.setDescription("Reminder");
            // MAKES NOTIFICATIONMANAGER OBJECT
            NotificationManager manager_high = getSystemService(NotificationManager.class);
            // CREATES NOTIFICATION CHANNEL
            manager_high.createNotificationChannel(notification_high);

            // BUILDS THE NEW NOTIFICATION CHANNEL WITH NO SOUND
            NotificationChannel notification_low = new NotificationChannel(
                    CHANNEL_ID_LOW,
                    "Muted Reminder",
                    NotificationManager.IMPORTANCE_LOW
            );
            // SETS THE NOTIFICATION DESCRIPTION TO "Muted Reminder"
            notification_high.setDescription("Muted Reminder");
            // MAKES NOTIFICATIONMANAGER OBJECT
            NotificationManager manager_low = getSystemService(NotificationManager.class);
            // CREATES NOTIFICATION CHANNEL
            manager_low.createNotificationChannel(notification_low);
        }
    }
}
