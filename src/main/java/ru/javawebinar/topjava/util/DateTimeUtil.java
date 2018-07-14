package ru.javawebinar.topjava.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final LocalDate Max_Date = LocalDate.MAX;
    public static final LocalDate Min_Date = LocalDate.MIN;
    public static final LocalTime Max_Time = LocalTime.MAX;
    public static final LocalTime Min_Time = LocalTime.MIN;

/*    public static boolean isBetweenTime(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetweenDate(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return ld.compareTo(startDate) >=0 && ld.compareTo(endDate) <= 0;
    }*/

    public static <T extends Comparable<T>> boolean isBetweenDateOrTime(T ld, T start, T end) {
        return ld.compareTo(start) >=0 && ld.compareTo(end) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalTime parseLocalTime(String stringTime) {
        return StringUtils.isEmpty(stringTime) ? null : LocalTime.parse(stringTime);
    }

    public static LocalDate parseLocalDate(String stringDate) {
        return StringUtils.isEmpty(stringDate) ? null : LocalDate.parse(stringDate);
    }
}
