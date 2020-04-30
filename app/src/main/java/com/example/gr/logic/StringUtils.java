package com.example.gr.logic;

/**
 * Class that contains text functions.
 *
 * @author Ruslan (@dievskiy), Tapi (@DXGGX)
 * @version 1.0 04/2020
 */

public class StringUtils {

    /**
     * Returns user-friendly form of date of type e.g. "yyyy-MM-dd" to "MM.dd".
     *
     * @param date Initial date
     * @return new date
     */
    public String getNiceDate(String date) {
        String niceDate = date;
        try {
            niceDate = date.substring(5, 7).concat(".").concat(date.substring(8, 10));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return niceDate;
    }
}
