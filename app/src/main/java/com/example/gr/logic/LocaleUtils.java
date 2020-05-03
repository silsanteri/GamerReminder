package com.example.gr.logic;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Class that contains locale functions.
 *
 * @author Santeri Silvennoinen (@silsanteri)
 * @version 1.0 04/2020
 */

public class LocaleUtils {

    /**
     * Function for changing the UI locale and saving the used locale to SharedPrefs.
     *
     * @param context
     * @param language String language abbreviation.
     */
    public static void setLocale(Context context, String language) {
        // SOURCE: https://www.youtube.com/watch?v=zILw5eV9QBQ
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        // SAVES LANGUAGE TO SHAREDPREFS
        SharedPrefsUtils.saveLanguage(context, language);
    }

    /**
     * Function for loading app language from SharedPrefs.
     *
     * @param context
     */
    public static void loadLocale(Context context) {
        // GETS SAVED LOCALE FROM SHAREDPREFS
        setLocale(context, SharedPrefsUtils.returnLanguage(context));
    }
}
