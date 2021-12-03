package com.xxw.platform.module.util.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期转换
 *
 * @author xxw
 * @since 2019/11/18
 */
public class DateTransfer {

    /**
     * 时间转化为时间戳（毫秒级）
     *
     * @param localDate  待转换对象
     * @return
     */
    public static Long localDateToLong(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * LocalDate 转换为 Date
     *
     * @param localDate  待转换对象
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime 转换为 Date
     *
     * @param localDateTime  待转换对象
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime 转换为时间戳（毫秒级）
     *
     * @param localDateTime  待转换对象
     * @return
     */
    public static Long localDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 时间转换为 LocalDate
     *
     * @param time  待转换时间戳
     * @return
     */
    public static LocalDate longToLocalDate(Long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 时间戳转换为 LocalDateTime
     *
     * @param time  待转换时间戳
     * @return
     */
    public static LocalDateTime longToLocalDateTime(Long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 时间戳转换为Date
     *
     * @param time   待转换时间戳
     * @return
     */
    public static Date longToDate(Long time) {
        return new Date(time);
    }


    /**
     * Date 转换为 LocalDate
     *
     * @param date  待转换时间对象
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        return longToLocalDate(date.getTime());
    }

    /**
     * Date 转换为 LocalDate
     *
     * @param date  待转换时间对象
     * @return
     */
    public static LocalDate date2LocalDate(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime.toLocalDate();
    }

    /**
     * Date 转换为 LocalDateTime
     *
     * @param date   待转换日期对象
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return longToLocalDateTime(date.getTime());
    }

    /**
     * Date 转换为 LocalDateTime
     *
     * @param date   待转换日期对象
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

}
