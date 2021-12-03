package com.xxw.platform.module.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式化
 *
 * @author xxw
 * @since 2019/11/18
 */
public class DateFormat {

    /**
     * 获取时间字符串 默认格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date    待转换日期
     * @return
     */
    public static String getDateString(Date date) {
        String formatString="yyyy-MM-dd HH:mm:ss";
        return getDateString(date,formatString);
    }

    /**
     * 获取时间字符串 例如：yyyy-MM-dd
     *
     * @param date           待转换日期
     * @param formatString   日期格式
     * @return
     */
    public static String getDateString(Date date, String formatString) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        return formatter.format(date);
    }

    /**
     * 获取当前时间的字符串 例如：yyyy-MM-dd
     *
     * @param formatString  日期格式
     * @return
     */
    public static String getNowDateString(String formatString) {
        return getDateString(new Date(), formatString);
    }

    /**
     * 获取某个日期的date对象 指定格式：yyyy-MM-dd HH:mm:ss
     *
     * @param dateString   日期字符串
     * @return             日期 不符合规范返回null
     */
    public static Date getDateFromString(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatter.parse(dateString);
            return date;
        } catch (ParseException e) {
            return null;
        }

    }

    /**
     * 获取某个日期的date对象
     *
     * @param dateString   日期
     * @param formatString 日期的格式
     * @return             日期 不符合规范返回null
     */
    public static Date getDateFromString(String dateString, String formatString) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        try {
            Date date = formatter.parse(dateString);
            return date;
        } catch (ParseException e) {
            return null;
        }

    }

    /**
     * 是否是时间字符串
     *
     * @param dateString   日期
     * @param formatString 日期的格式
     * @return             符合返回true
     */
    public static Boolean isDateString(String dateString, String formatString) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        try {
            Date date = formatter.parse(dateString);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 获取时间字符串的时间戳   单位为 毫秒
     *
     * @param dateString     时间字符串
     * @param formatString   字符串格式
     * @return               时间戳   不符合规范返回null
     */
    public static Long getTimeStamp(String dateString, String formatString) {
        Date date = getDateFromString(dateString, formatString);
        if (null != date) {
            return date.getTime();
        }
        return null;
    }

    /**
     * 时间戳转换为字符串
     *
     * @param timeStamp       时间戳
     * @param formatString    时间格式
     * @return
     */
    public static String getDateStr(Long timeStamp, String formatString) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        return formatter.format(new Date(timeStamp));
    }

}
