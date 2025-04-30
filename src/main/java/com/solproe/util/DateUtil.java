package com.solproe.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    private static final DateTimeFormatter DEFAULT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parse(String dateStr) {
        return LocalDate.parse(dateStr, DEFAULT_FORMAT);
    }

    public static String format(LocalDate date) {
        return date.format(DEFAULT_FORMAT);
    }

    public static LocalDate addDays(String dateStr, int days) {
        return parse(dateStr).plusDays(days);
    }

    public static LocalDate subtractDays(String dateStr, int days) {
        return parse(dateStr).minusDays(days);
    }

    public static LocalDate addMonths(String dateStr, int months) {
        return parse(dateStr).plusMonths(months);
    }

    public static LocalDate subtractMonths(String dateStr, int months) {
        return parse(dateStr).minusMonths(months);
    }

    public static boolean isBeforeToday(String dateStr) {
        return parse(dateStr).isBefore(LocalDate.now());
    }

    public static boolean isAfterToday(String dateStr) {
        return parse(dateStr).isAfter(LocalDate.now());
    }

    public static boolean isValidDate(String dateStr) {
        try {
            parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
