package com.xxw.platform.module.util.math;

/**
 * 位运算相关工具类
 *
 * @author ethan
 * @since 2019/11/18
 */
public class BitUtil {


    /**
     * 获取运算数指定位置的值<br> 例如： 0000 1011 获取其第 0 位的值为 1, 第 2 位 的值为 0<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0<=pos<=7)
     * @return 指定位置的值(0 or 1)
     */
    public static byte getBitValue(Long source, int pos) {
        return (byte)((source >> pos) & 1);
    }

    /**
     * 将运算数指定位置的值置为指定值<br> 例: 0000 1011 需要更新为 0000 1111, 即第 2 位的值需要置为 1<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0<=pos<=7)
     * @param value  只能取值为 0, 或 1, 所有大于0的值作为1处理, 所有小于0的值作为0处理
     * @return 运算后的结果数
     */
    public static Long setBitValue(Long source, int pos, Long value) {

        byte mask = (byte)(1 << pos);
        if (value > 0) {
            source |= mask;
        } else {
            source &= (~mask);
        }

        return source;
    }

    /**
     * 将运算数指定位置的值置为指定值<br> 例: 0000 1011 需要更新为 0000 1111, 即第 2 位的值需要置为 1<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0<=pos<=63)
     * @param value  只能取值为 0, 或 1, 所有大于0的值作为1处理, 所有小于0的值作为0处理
     * @return 运算后的结果数
     */
    public static Long setLongBitValue(Long source, int pos, Long value) {

        Long mask = 1L << pos;
        if (value > 0) {
            source |= mask;
        } else {
            source &= (~mask);
        }

        return source;
    }

    /**
     * 将运算数指定位置取反值<br> 例： 0000 1011 指定第 3 位取反, 结果为 0000 0011; 指定第2位取反, 结果为 0000 1111<br>
     *
     * @param source
     * @param pos    指定位置 (0<=pos<=7)
     * @return 运算后的结果数
     */
    public static Long reverseBitValue(Long source, int pos) {
        byte mask = (byte)(1 << pos);
        return (source ^ mask);
    }

    /**
     * 将运算数指定位置取反值<br> 例： 0000 1011 指定第 3 位取反, 结果为 0000 0011; 指定第2位取反, 结果为 0000 1111<br>
     *
     * @param source
     * @param pos    指定位置 (0<=pos<=63)
     * @return 运算后的结果数
     */
    public static Long reverseLongBitValue(Long source, int pos) {
        Long mask = 1L << pos;
        return (source ^ mask);
    }

    /**
     * 检查运算数的指定位置是否为1<br>
     *
     * @param source 需要运算的数
     * @param pos    指定位置 (0<=pos<=7)
     * @return true 表示指定位置值为1, false 表示指定位置值为 0
     */
    public static boolean checkBitValue(Long source, int pos) {

        if (source == null) {
            return false;
        }
        source = (source >>> pos);
        return (source & 1) == 1;
    }

}
