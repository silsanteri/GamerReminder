package com.example.gr.logic;

import java.util.List;

/**
 * Class that contains math functions.
 *
 * @author Ruslan (@dievskiy), Santeri Silvennoinen (@silsanteri), Tapi (@DXGGX)
 * @version 1.0 04/2020
 */

public class MathUtils {

    /**
     * Returns average.
     *
     * @param allValues list of values
     * @return int average
     */
    public int getAverage(List<Integer> allValues) {
        int average = 0, size = 0;
        for (int el : allValues) {
            // skip days if it's empty
            if (el != 0) {
                average += el;
                size++;
            }
        }
        if (size != 0) {
            return average / size;
        } else return 0;
    }

    /**
     * Converts given parameter milliseconds to seconds.
     *
     * @param value initial value of milliseconds
     * @return long seconds
     */
    public static long millisecondsToSeconds(long value) {
        // 1 SECOND = 1000 MILLISECONDS
        long result = Math.round(value / 1000);
        return result;
    }

    /**
     * Converts given parameter minutes to seconds.
     *
     * @param value initial value of milliseconds
     * @return long seconds
     */
    public static long minutesToSeconds(long value) {
        // 1 MINUTE = 60 SECONDS
        long result = (value * 60);
        return result;
    }

    /**
     * Cconverts given parameter minutes to milliseconds.
     *
     * @param value initial value of milliseconds
     * @return long milliseconds
     */
    public static int minutesToMilliseconds(int value) {
        // 1 MINUTE = 60000 MILLISECONDS
        int result = (value * 60000);
        return result;
    }
}
