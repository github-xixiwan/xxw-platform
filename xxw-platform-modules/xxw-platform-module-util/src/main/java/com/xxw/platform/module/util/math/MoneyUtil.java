package com.xxw.platform.module.util.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 财务相关资金工具类
 *
 * @author xxw
 * @since 2019/11/18
 */
public class MoneyUtil {

    /**
     * 将分转换成元
     *
     * @param cent
     * @return
     */
    public static BigDecimal convertToYuan(Long cent) {
        if (cent == null) {
            return null;
        }
        return new BigDecimal(cent).movePointLeft(2);
    }

    /**
     * 将分转换成元
     *
     * @param cent
     * @return
     */
    public static BigDecimal convertToYuan(String cent) {
        if (cent == null) {
            return null;
        }
        return new BigDecimal(cent).movePointLeft(2);
    }

    /**
     * 将元转换成分
     *
     * @param yuan
     * @return
     */
    public static Long convertToFen(Long yuan) {
        if (yuan == null) {
            return null;
        }
        return yuan * 100L;
    }

    /**
     * 将元转换成分
     *
     * @param yuan
     * @return
     */
    public static Long convertToFen(String yuan) {
        if (yuan == null) {
            return null;
        }
        return NumberUtil.toLong(yuan) * 100L;
    }

    /**
     * 四舍五入展示指定小数位数的数字
     *
     * @param toBeDisplayed
     * @param dimension
     * @param digits
     * @return
     */
    public static String getReadableMoney(Long toBeDisplayed, Long dimension, Integer digits) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("##########.");
        for (int i = 0; i < digits; i++) {
            stringBuilder.append("#");
        }
        DecimalFormat decimalFormat = new DecimalFormat(stringBuilder.toString());
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat.format(new BigDecimal(String.valueOf(toBeDisplayed / (double)dimension)));
    }


}
