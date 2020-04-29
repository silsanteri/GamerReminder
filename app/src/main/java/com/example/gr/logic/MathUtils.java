package com.example.gr.logic;

import android.util.Pair;

import java.util.List;

/**
 * Class that contains math functions
 *
 * @author Ruslan
 * @version 1/04.2020
 */
public class MathUtils {

    /**
     * Returns average
     *
     * @param allValues list of values
     * @return average
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

}
