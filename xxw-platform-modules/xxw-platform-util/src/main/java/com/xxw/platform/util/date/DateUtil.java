package com.xxw.platform.util.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author ethan
 * @since 2019/11/18
 */
public class DateUtil {

    /**
     * 获取昨天的起始时间  即凌晨0点
     *
     * @return
     */
    public static Date yesterday() {
        return DateTransfer.localDateToDate(LocalDate.now().plus(-1, ChronoUnit.DAYS));
    }

    /**
     * 获取明天的截止时间  即凌晨0点
     *
     * @return
     */
    public static Date tomorrow() {
        return DateTransfer.localDateToDate(LocalDate.now().plus(1, ChronoUnit.DAYS));
    }

    /**
     * 获取相隔n天的日期  即凌晨0点
     *
     * @param offset    偏移量  往后为+ 往前为-
     * @return
     */
    public static Date dayOfOffset(Integer offset) {
        return dayOfOffset(new Date(), offset);
    }

    /**
     * 以天为单位计算指定偏移量的天数  既某个时间点为坐标相应间隔天数的日期  时分秒还是同当前时间
     *
     * @param date       待处理时间
     * @param offset     偏移量  往后为+ 往前为-
     * @return
     */
    public static Date dayOfOffset(Date date, Integer offset) {
        LocalDate localDate = DateTransfer.dateToLocalDate(date);
        return DateTransfer.localDateToDate(localDate.plus(offset, ChronoUnit.DAYS));
    }

    /**
     * 获取某天的下一天  时分秒还是同当前时间
     *
     * @param date   待处理日期
     * @return
     */
    public static Date nextDay(Date date) {
        LocalDate localDate = DateTransfer.longToLocalDate(date.getTime());
        return DateTransfer.localDateToDate(localDate.plus(1, ChronoUnit.DAYS));
    }

    /**
     * 获取某天的前一天  时分秒还是同当前时间
     *
     * @param date  待处理日期
     * @return
     */
    public static Date beforeDay(Date date) {
        LocalDate localDate = DateTransfer.longToLocalDate(date.getTime());
        return DateTransfer.localDateToDate(localDate.minus(1, ChronoUnit.DAYS));
    }

    /**
     * 计算两个日期之间相差的年份数  数值按照date2 - date1 进行计算
     *
     * @param date1    开始时间
     * @param date2    结束时间
     * @return         相差年数
     */
    public static int getDiffYear(Date date1, Date date2) {
        LocalDate localDate1 = DateTransfer.date2LocalDate(date1);
        LocalDate localDate2 = DateTransfer.date2LocalDate(date2);
        return localDate2.getYear() - localDate1.getYear();
    }

    /**
     * 计算两个日期之间相差的月数  月初和月末差一天也是差一月  数值按照date2 - date1 进行计算
     *
     * @param date1 开始时间
     * @param date2 结束时间
     * @return      相差月数
     */
    public static int getDiffMonth(Date date1, Date date2) {
        LocalDate localDate1 = DateTransfer.date2LocalDate(date1);
        LocalDate localDate2 = DateTransfer.date2LocalDate(date2);
        return (localDate2.getYear() - localDate1.getYear()) * 12 + localDate2.getMonthValue() - localDate1.getMonthValue();
    }

    /**
     * 计算两个日期之间相差的天数  数值按照date2 - date1 进行计算
     *
     * @param date1   开始时间
     * @param date2   结束时间
     * @return        相差天数
     */
    public static int getDiffDays(Date date1, Date date2) {
        LocalDate localDate1 = DateTransfer.date2LocalDate(date1);
        LocalDate localDate2 = DateTransfer.date2LocalDate(date2);
        return ((Long)(localDate2.toEpochDay() - localDate1.toEpochDay())).intValue();
    }

    /**
     * 判断时间先后，date1 早于 date2
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return    true 或 false
     */
    public static Boolean judgeDateOrder(Date startDate, Date endDate) {
        return startDate.before(endDate);
    }

    /**
     * 获取当天的剩余秒数
     *
     * @return  剩余秒数
     */
    public static Long remainSecondsOfToday() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        /*System.out.println(midnight);
        System.out.println(LocalDateTime.now());*/
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }

    /**
     * 获取入参当天的最后一秒，即23:59:59
     * 这里精确到秒
     *
     * @param date  具体日期
     * @return      当天最后一秒
     */
    public static Date getDateLastSecond(Date date) {
        LocalDate localDate = DateTransfer.longToLocalDate(date.getTime());
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.MAX).withNano(0);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当天处于当前星期的第几天，从星期一开始，是星期一则返回1
     *
     * @param date
     * @return
     */
    public static Integer getDayOfWeek(Date date) {
        LocalDate localDate = DateTransfer.date2LocalDate(date);
        return localDate.getDayOfWeek().getValue();
    }

    /**
     * 获取指定的时间，以确定的某个时间为基准，移动指定天数，再填入时分秒信息
     *
     * @param date
     * @param offset
     * @param hour
     * @param min
     * @param sec
     * @return
     */
    public static Date getDefiniteDate(Date date, Integer offset, Integer hour, Integer min, Integer sec) {
        LocalDate localDate = DateTransfer.date2LocalDate(date);
        localDate = localDate.plusDays(offset);
        LocalTime localTime = LocalTime.of(hour, min, sec, 0);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        return DateTransfer.localDateTimeToDate(localDateTime);
    }

}
