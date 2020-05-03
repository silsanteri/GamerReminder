package com.example.gr.logic;

/**
 * Types of possible add items.
 *
 * @author Ruslan (@dievskiy), Tapi (@DXGGX)
 * @version 1.0 04/2020
 */

public enum ItemType {
    FOOD, WATER, EXERCISE;

    public String getAppendix() {
        switch (this) {
            case WATER:
                return "ml";
            case EXERCISE:
                return "min";
            case FOOD:
                return "kcal";
            default:
                return "";
        }
    }
}
