package com.xxw.platform.util.string;

import com.xxw.platform.util.date.DateFormat;
import com.xxw.platform.util.exception.UtilException;
import com.xxw.platform.util.math.NumberUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 字符串功能类
 *
 * @author ethan
 * @since 2019/11/18
 */
public class StringUtil {


    /**
     * 字符串是否为空（或者空字符串或者空白符）
     *
     * @param data    待判定的字符串
     * @return        空字符串 true 非空字符串 false
     */
    public static boolean isBlank(String data) {
        return StringUtils.isBlank(data);
    }

    /**
     * 字符串是否不为空（和空字符串和空白符）
     *
     * @param data    待判定的字符串
     * @return        空字符串 false 非空字符串 true
     */
    public static boolean isNotBlank(String data) {
        return !StringUtils.isBlank(data);
    }

    /**
     * 字符串是否为空
     *
     * @param data
     * @return
     */
    public static boolean isEmpty(String data) {
        if (data != null && data.length() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 字符串是否不空
     *
     * @param data
     * @return
     */
    public static boolean isNotEmpty(String data) {
        if (data != null && data.length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 将字符串分割为list
     *
     * @param data
     * @param split
     * @return
     */
    public static List<String> stringToSList(String data, String split) {
        if (isEmpty(data) || isEmpty(split)) { return null; }
        return new ArrayList<>(Arrays.asList(data.split(split)));
    }

    /**
     * 计算字符串的字节长度(字母数字计1，汉字及标点计3)
     * @param str  需要计算的字符串
     * @return     字符串长度
     */
    public static int byteLength(String str) {
        try {
            return str == null ? 0 : str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException e) {
            throw new UtilException("以UTF-8字符集获取指定字符串长度出错!str=" + str);
        }
    }

    /**
     * 字符串参数替换
     *
     * @param msg     原字符串
     * @param param   需要替换的键值对map
     * @return        替换后的字符串
     */
    public static String replacePlaceHolderWithMapValue(String msg, Map<String, Object> param) {
        Pattern placeHolderPattern = compile("\\$\\{(\\w+)\\}");
        Matcher m = placeHolderPattern.matcher(msg);
        while (m.find()) {
            String placeHolder = m.group(0);
            String key = m.group(1);
            String replaceValue = key;
            Object value = param.get(key);
            if (value != null) {
                if (value instanceof String[]) {
                    replaceValue = Arrays.asList((String[])value).toString();
                } else {
                    replaceValue = value.toString();
                }
            }

            msg = msg.replace(placeHolder, replaceValue);
        }

        return msg;
    }

    /**
     * 首字母大写
     *
     * @param str  原字符串
     * @return
     */
    public static String upperCaseFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 字符串拼接
     *
     * @param elements  需要拼接的对象
     * @param <T>       泛型
     * @return          拼装好的字符串
     */
    public static <T> String join(T... elements) {
        return StringUtils.join(elements);
    }

    /**
     * 获取随机字符串值  格式为当前日期按照格式 yyyyMMddHHmmssSSS
     *
     * @return
     */
    public static String getRandNumberString() {
        int min = 10000000;
        int max = 99999999;
        StringBuilder sb = new StringBuilder();
        sb.append(DateFormat.getNowDateString("yyyyMMddHHmmssSSS"));
        sb.append(NumberUtil.getRandom(min,max));
        return sb.toString();
    }

    /**
     * 生成随机字符串
     *
     * @param length   字符串长度
     * @return
     */
    public static String getRandomString(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return toHex(bytes);
    }

    /**
     * 字符串补齐
     *
     * @param value   字符串
     * @param size    位数
     * @param padChar 补齐的字符
     * @return
     */
    public static String leftPad(String value, Integer size, String padChar) {
        return StringUtils.leftPad(value, size, padChar);
    }

    /**
     * 判断是否是纯字母
     *
     * @param value
     * @return
     */
    public static Boolean isAlpha(String value) {
        return StringUtils.isAlpha(value);
    }

    /**
     * 判断是否存在大写字母
     *
     * @param value
     * @return
     */
    public static Boolean containsUpperCase(String value) {
        if (isBlank(value)) {
            return false;
        }
        return value.chars().anyMatch(character -> character >= 'A' && character <= 'Z');
    }

    /**
     * 判断是否存在小写字母
     *
     * @param value
     * @return
     */
    public static Boolean containsLowerCase(String value) {
        if (isBlank(value)) {
            return false;
        }
        return value.chars().anyMatch(character -> character >= 'a' && character <= 'z');
    }

    /**
     * 判断是否全部是大写字母
     *
     * @param value
     * @return
     */
    public static Boolean isAllUpperCase(String value) {
        return StringUtils.isAllUpperCase(value);
    }

    /**
     * 判断是否全部是小写字母
     *
     * @param value
     * @return
     */
    public static Boolean isAllLowerCase(String value) {
        return StringUtils.isAllLowerCase(value);
    }

    /**
     * 转为16进制可见表示
     *
     * @param bytes
     * @return
     */
    private static String toHex(byte[] bytes) {
        final char[] hexDigits = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length);
        for (byte each : bytes) {
            ret.append(hexDigits[each & 0x0f]);
        }
        return ret.toString();
    }

}
