package com.example.gr.logic;

public class StringUtils {
    /**
     * Returns user-friendly form of date of type "2020-03-23" to 03.23
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
