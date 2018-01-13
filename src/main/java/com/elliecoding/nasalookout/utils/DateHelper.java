package com.elliecoding.nasalookout.utils;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for date handling. Makes heavy use of Joda Time
 */
public class DateHelper {

    /**
     * Returns the starting dates, the first day, of several months in the past from today. To be precise, number of
     * months specified as a parameter will be returned (including this month). I.e. requesting the starting dates of 2
     * months in the past will give you the 01.MM.YYYY and the 01.MM-1.YYYY, where MM equals your current month and YYYY
     * equals the respective year
     *
     * @param numberOfMonths The number of months to be returned in total. The resulting list will have this size
     * @return A list of 01. of several months. Empty collection if zero months were requested. Never null
     */
    public static List<LocalDate> determinePastStartingDates(int numberOfMonths) {
        return determinePastStartingDates(numberOfMonths, LocalDate.now());
    }

    /**
     * Returns the starting dates, the first day, of several months in the past. To be precise, number of months
     * specified as a parameter will be returned (including this month). I.e. requesting the starting dates of 2 months
     * in the past from today will give you the 01.MM.YYYY and the 01.MM-1.YYYY, where MM equals your current month and
     * YYYY equals the respective year
     *
     * @param numberOfMonths The number of months to be returned in total. The resulting list will have this size
     * @param sourceDate     The date from which all counting starts
     * @return A list of 01. of several months. Empty collection if zero months were requested. Never null
     */
    public static List<LocalDate> determinePastStartingDates(int numberOfMonths, LocalDate sourceDate) {
        List<LocalDate> result = new ArrayList<>();

        // Determine the first day of the month that we are currently in
        LocalDate date = sourceDate.withDayOfMonth(1);
        result.add(date);

        // Then get the rest of the months
        for (int count = 0; count < numberOfMonths - 1; count++) {
            date = date.minusMonths(1);
            result.add(date);
        }
        return result;
    }

    /**
     * Returns a list of the dates in the month that is in the date passed as a parameter
     *
     * @param startingDate Any date of the month from which all days are to be retrieved
     * @return A list of all days in the month that was part of the parameter
     */
    public static List<LocalDate> determineDaysOfMonth(LocalDate startingDate) {
        List<LocalDate> result = new ArrayList<>();

        // First we make absolutely sure that this is the first day of the mont
        startingDate = startingDate.withDayOfMonth(1);
        result.add(startingDate);

        int days = startingDate.dayOfMonth().getMaximumValue();
        for (int count = 2; count <= days; count++) {
            result.add(startingDate.withDayOfMonth(count));
        }
        return result;
    }
}
