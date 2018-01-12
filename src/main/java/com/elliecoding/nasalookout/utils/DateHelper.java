package com.elliecoding.nasalookout.utils;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class DateHelper {

    private static List<LocalDate> determineStartingDates(int numberOfMonths) {
        return determineStartingDates(numberOfMonths, LocalDate.now());
    }

    /**
     * Returns the starting dates, the first day, of several months in the past. To be precise, number of months
     * specified as a parameter will be returned (including this month). I.e. requesting the starting dates of 6 months
     * in the past from today will give you the 01.MM.YYYY where MM equals the current month
     *
     * @param numberOfMonths
     * @param sourceDate
     * @return
     */
    private static List<LocalDate> determineStartingDates(int numberOfMonths, LocalDate sourceDate) {
        List<LocalDate> result = new ArrayList<>();


        result.add();
        for (int count = 0; count < numberOfMonths; count++) {
            sourceDate.minusMonths(1).minusDays()
        }
    }
}
