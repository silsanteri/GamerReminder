package com.example.gr.logic;

import android.content.Context;

import com.example.gr.R;

/**
 * Types of possible add items.
 *
 * @author Ruslan (@dievskiy), Tapi (@DXGGX)
 * @version 1.0 04/2020
 */

public enum ItemType {
    FOOD, WATER, EXERCISE;

    public String getAppendix(Context context) {
        switch (this) {
            case WATER:
                return context.getString(R.string.appendix_water);
            case EXERCISE:
                return context.getString(R.string.appendix_exercise);
            case FOOD:
                return context.getString(R.string.appendix_food);
            default:
                return "";
        }
    }

    public String getLocaledName(Context context) {
        switch (this) {
            case EXERCISE:
                return context.getString(R.string.exercise).toLowerCase();
            case FOOD:
                return context.getString(R.string.calories).toLowerCase();
            default:
                return context.getString(R.string.drink).toLowerCase();
        }
    }

    public int getMaxValue() {
        switch (this) {
            case EXERCISE:
                return 1440;
            case FOOD:
                return 10000;
            default:
                return 10000;
        }
    }
}
