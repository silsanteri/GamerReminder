package com.example.gr.logic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class that contains SharedPrefs functions.
 *
 * @author Santeri Silvennoinen (@silsanteri)
 * @version 1.0 04/2020
 */
public class SharedPrefsUtils {
    private static final String SETTINGS = "settings";
    private static final String LANGUAGE = "language";
    private static final String GAMEMODE_ACTIVE = "gamemode_active";
    private static final String NOTIFICATION_SOUND = "notification_sound";
    private static final String NOTIFICATION_FREQUENCY = "notification_frequency";

    /**
     * Saves language to SharedPrefs.
     *
     * @param context
     * @param language
     */
    public static void saveLanguage(Context context, String language) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SETTINGS, context.MODE_PRIVATE).edit();
        editor.putString(LANGUAGE, language);
        editor.apply();
    }

    /**
     * Saves notification sound setting to SharedPrefs.
     *
     * @param context
     */
    public static void saveNotificationSound(Context context, boolean notificationSound) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SETTINGS, context.MODE_PRIVATE).edit();
        editor.putBoolean(NOTIFICATION_SOUND, notificationSound);
        editor.apply();
    }

    /**
     * Saves notification frequency setting to SharedPrefs.
     *
     * @param context
     */
    public static void saveNotificationFrequency(Context context, int notificationFrequency) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SETTINGS, context.MODE_PRIVATE).edit();
        editor.putInt(NOTIFICATION_FREQUENCY, notificationFrequency);
        editor.apply();
    }

    /**
     * Saves Game Mode status to SharedPrefs.
     *
     * @param context
     */
    public static void saveGameModeStatus(Context context, boolean gamemodeActive) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SETTINGS, context.MODE_PRIVATE).edit();
        editor.putBoolean(GAMEMODE_ACTIVE, gamemodeActive);
        editor.apply();
    }

    /**
     * Returns language value from SharedPrefs.
     *
     * @param context
     * @return String
     */
    public static String returnLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SETTINGS, Activity.MODE_PRIVATE);
        return prefs.getString(LANGUAGE, "");
    }

    /**
     * Returns notification sound value from SharedPrefs.
     *
     * @param context
     * @return boolean
     */
    public static boolean returnNotificationSound(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SETTINGS, Activity.MODE_PRIVATE);
        return prefs.getBoolean(NOTIFICATION_SOUND, true);
    }

    /**
     * Returns notification frequency value from SharedPrefs.
     *
     * @param context
     * @return long
     */
    public static long returnNotificationFrequency(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SETTINGS, Activity.MODE_PRIVATE);
        return prefs.getInt(NOTIFICATION_FREQUENCY, 15);
    }

    /**
     * Returns Game Mode status value from SharedPrefs.
     *
     * @param context
     * @return boolean
     */
    public static boolean returnGameModeStatus(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SETTINGS, Activity.MODE_PRIVATE);
        return prefs.getBoolean(GAMEMODE_ACTIVE, false);
    }
}
