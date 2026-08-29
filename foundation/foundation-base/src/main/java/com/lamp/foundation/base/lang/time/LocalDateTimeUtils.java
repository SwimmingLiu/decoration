/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */

package com.lamp.foundation.base.lang.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hahaha
 */
public class LocalDateTimeUtils {

    public static long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

    public static long ONE_WEEK_IN_MILLIS = 7 * 24 * 60 * 60 * 1000;

    public static long ONE_MONTH_IN_MILLIS = 30L * 24 * 60 * 60 * 1000;

    public static long ONE_YEAR_IN_MILLIS = 365L * 24 * 60 * 60 * 1000;

    public static DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    public static DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ISO_TIME;

    public static long between(ChronoUnit chronoUnit, Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
        return chronoUnit.between(temporal1Inclusive, temporal2Exclusive);
    }

    public static LocalDateTime createLastLocalDateTime() {
        return LocalDateTime.of(LocalDate.now(), createLastLocalTime());
    }

    /**
     * 获得一天的结束时间
     */
    public static LocalTime createLastLocalTime() {
        return LocalTime.of(23, 59, 59);
    }

    public static LocalDateTime createZeroLocalDateTime() {
        return LocalDateTime.of(LocalDate.now(), createZeroLocalTime());
    }

    /**
     * 获得一天的开始时间
     */
    public static LocalTime createZeroLocalTime() {
        return LocalTime.of(0, 0, 0);
    }

    public static List<LocalDate> getMiddleDate(LocalDate begin, LocalDate end) {
        List<LocalDate> localDateList = new ArrayList<>(10);
        long length = end.toEpochDay() - begin.toEpochDay();
        for (long i = length; i >= 0; i--) {
            localDateList.add(end.minusDays(i));
        }
        return localDateList;
    }

    public static List<LocalDateTime> getMiddleDateTime(LocalDateTime begin, LocalDateTime end, ChronoUnit chronoUnit, int interval) {
        long between = between(chronoUnit, begin, end);
        if (between == 0) {
            return Collections.emptyList();
        }
        if (between < 0) {
            between = -between;
        }
        int length = (int) between / interval + 1;
        List<LocalDateTime> localDateTimeList = new ArrayList<>(length);
        LocalDateTime beginDateTime = truncatedTo(begin, chronoUnit);
        localDateTimeList.add(beginDateTime);
        for (long i = 0; i < length; i++) {
            beginDateTime = beginDateTime.plus(interval, chronoUnit);
            localDateTimeList.add(beginDateTime);
        }
        if (between < 0) {
            Collections.reverse(localDateTimeList);
        }
        return localDateTimeList;
    }

    public static boolean isAfter(ChronoLocalDate compare1, ChronoLocalDate compare2) {
        return compare1.isAfter(compare2);
    }

    public static boolean isBefore(ChronoLocalDate compare1, ChronoLocalDate compare2) {
        return compare1.isBefore(compare2);
    }

    public static boolean isEqual(ChronoLocalDate compare1, ChronoLocalDate compare2) {
        return compare1.isEqual(compare2);
    }

    /**
     * 时间取整
     * </p>
     * <pre><
     *   2024-12-3 -> 2024-12-1
     *   2024-12-3 20:10:01 -> 2024-12-1 20:00:00
     * </pre>
     *
     * @param localDateTime 计算的时间
     * @param chronoUnit    时间单位
     * @return 计算后的结果
     */
    public static LocalDateTime truncatedTo(LocalDateTime localDateTime, ChronoUnit chronoUnit) {
        return LocalDateTime.of(truncatedTo(localDateTime.toLocalDate(), chronoUnit), truncatedTo(localDateTime.toLocalTime(), chronoUnit));
    }

    public static LocalDate truncatedTo(LocalDate localDateTime, ChronoUnit chronoUnit) {
        int year = localDateTime.getYear();
        int month = chronicUnitBefore(ChronoUnit.MONTHS, chronoUnit) ? localDateTime.getMonthValue() : 1;
        int dayOfMonth = chronicUnitBefore(ChronoUnit.DAYS, chronoUnit) ? localDateTime.getDayOfMonth() : 1;
        return LocalDate.of(year, month, dayOfMonth);
    }

    public static LocalTime truncatedTo(LocalTime localTime, ChronoUnit chronoUnit) {
        int hour = chronicUnitBefore(ChronoUnit.HOURS, chronoUnit) ? localTime.getHour() : 0;
        int minute = chronicUnitBefore(ChronoUnit.MINUTES, chronoUnit) ? localTime.getMinute() : 0;
        int second = chronicUnitBefore(ChronoUnit.SECONDS, chronoUnit) ? localTime.getSecond() : 0;
        return LocalTime.of(hour, minute, second);
    }

    private static boolean chronicUnitBefore(ChronoUnit chronoUnit, ChronoUnit chronoUnit1) {
        return chronoUnit.getDuration().getSeconds() >= chronoUnit1.getDuration().getSeconds();
    }

}
