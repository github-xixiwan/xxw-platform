package com.xxw.platform.module.util.math;

import org.apache.commons.lang3.math.NumberUtils;

import java.security.SecureRandom;
import java.util.SplittableRandom;

/**
 * 数字相关的基础类
 *
 * @author ethan
 * @since 2019/11/18
 */
public class NumberUtil {


    /**
     * 判断数字是否是奇数
     *
     * @param number
     * @return
     */
    public static Boolean isOdd(int number) {
        return (number & 1) == 1;
    }

    /**
     * 取对数
     *
     * @param value
     * @param base
     * @return
     */
    public static double log(double value, double base) {
        return Math.log(value) / Math.log(base);
    }

    /**
     * 得到对数值，向上取整
     *
     * @param value
     * @param base
     * @return
     */
    public static Double ceilforLogVaule(double value, double base) {
        double result = Math.log(value) / Math.log(base);
        return Math.ceil(result);
    }

    /**
     * 将数字转换为Long
     *
     * @param value
     * @param defaultValue 默认值
     * @return
     */
    public static Long toLong(Object value, Long defaultValue) {
        if (null == value) {
            return defaultValue;
        }
        try {
            return Long.valueOf(value.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将数据转换为Long  默认值为0
     *
     * @param value
     * @return
     */
    public static Long toLong(Object value) {
        return toLong(value, 0L);
    }

    /**
     * 将数据转换为Integer
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static Integer toInteger(Object value, Integer defaultValue) {
        if (null == value) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 将数据转换为Integer 默认值为0
     *
     * @param value
     * @return
     */
    public static Integer toInteger(Object value) {
        return toInteger(value, 0);
    }

    /**
     * 是否小于0
     * @param id Long
     * @return
     */
    public static boolean ltZero(Long id) {
        return id == null || id <= 0;
    }

    /**
     * 是否小于0
     * @param id Integer
     * @return
     */
    public static boolean ltZero(Integer id) {
        return id == null || id <= 0;
    }
    /**
     * 是否大于0
     * @param id Long
     * @return
     */
    public static boolean gtZero(Long id) {
        return id != null && id > 0;
    }
    /**
     * 是否大于0
     * @param id Integer
     * @return
     */
    public static boolean gtZero(Integer id) {
        return id != null && id > 0;
    }

    /**
     * 获取某个范围内的随机数，左闭右开区间，返回类型为长整型
     * 注意！
     * @param min  最小值
     * @param max  最大值
     * @return
     */
    public static Long getRandom(Long min, Long max)
    {
        //SecureRandom random = new SecureRandom();
        // 线程安全，但是范围内的取值概率不是平均分布的，并且取不到long的完整范围
        //long temp = random.nextLong();
        //if (temp < 0L) {
        //    temp += Long.MAX_VALUE;
        //}
        //
        //Long s = temp % (max - min + 1) + min;

        if (max <= Integer.MAX_VALUE) {
            return (long)getRandom(min.intValue(), max.intValue());
        }

        // 线程不安全，但是范围内的取值概率是平均分布的
        SplittableRandom random = new SplittableRandom();
        Long s = random.nextLong(max) % (max - min + 1) + min;
        return s;
    }

    /**
     * 获取任意范围内的随机数，左闭右开区间，返回类型为长整型，默认最小取到0
     * @param range
     * @return
     */
    public static Long getRandom(Long range){
        return getRandom(1L,range);
    }


    /**
     * 获取某个范围内的随机数，左闭右开区间，返回类型为整型
     * @param min  最小值
     * @param max  最大值
     * @return
     */
    public static Integer getRandom(Integer min, Integer max)
    {
        SecureRandom random = new SecureRandom();
        Integer s  = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    /**
     * 获取任意范围内的随机数，左闭右开区间，返回类型为整型 ，默认最小取到1
     * @param range
     * @return
     */
    public static Integer getRandom(Integer range){
        return getRandom(1,range);
    }

    /**
     * 判断字符串是否是一个有效的数学数字（包括正负号和小数点）
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        return NumberUtils.isCreatable(str);
    }

    /**
     * 判断字符串是否完全由数字组成，如果如果不是则直接返回false
     *
     * @param str
     * @return
     */
    public static boolean isDigit(String str) {
        return NumberUtils.isDigits(str);
    }

}
