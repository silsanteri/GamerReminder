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
        int average = 0;
        int size = 0;
        for (int el : allValues) {
            // skip days if its empty
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
     * A function that converts given milliseconds to seconds.
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
     * A function that converts given minutes to seconds.
     *
     * @param value initial value of milliseconds
     * @return long seconds
     */
    public static long minutesToSeconds(long value) {
        // 1 MINUTE = 60 SECONDS
        long result = (value * 60);
        return result;
    }
}
