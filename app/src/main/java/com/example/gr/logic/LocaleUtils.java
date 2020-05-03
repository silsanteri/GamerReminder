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

        SharedPrefsUtils.saveLanguage(context, language);
    }

    /**
     * Function for loading app language.
     *
     * @param context
     */
    public static void loadLocale(Context context) {
        setLocale(context, SharedPrefsUtils.returnLanguage(context));
    }
}
