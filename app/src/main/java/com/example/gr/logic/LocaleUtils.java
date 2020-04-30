package com.example.gr.logic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Class that contains locale functions.
 *
 * @author Santeri Silvennoinen (@silsanteri)
 * @version 1.0 04/2020
 */

public class LocaleUtils {
    private static final String SETTINGS = "settings";
    private static final String LANGUAGE = "language";

    /**
     * Function for changing and saving app language.
     *
     * @param context
     * @param language
     */
    public static void setLocale(Context context, String language) {
        // SOURCE: https://www.youtube.com/watch?v=zILw5eV9QBQ
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

        // SAVE DATA TO SHAREDPREFS
        SharedPreferences.Editor editor = context.getSharedPreferences(SETTINGS, context.MODE_PRIVATE).edit();
        editor.putString(LANGUAGE, language);
        editor.apply();
    }

    /**
     * Function for loading app language.
     *
     * @param context
     */
    public static void loadLocale(Context context) {
        // SOURCE: https://www.youtube.com/watch?v=zILw5eV9QBQ
        SharedPreferences prefs = context.getSharedPreferences(SETTINGS, Activity.MODE_PRIVATE);
        String language = prefs.getString(LANGUAGE, "");
        setLocale(context, language);
    }
}
