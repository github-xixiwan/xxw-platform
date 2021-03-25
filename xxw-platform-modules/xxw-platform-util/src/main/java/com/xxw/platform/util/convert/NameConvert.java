package com.xxw.platform.util.convert;

import com.xxw.platform.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数命名转换
 *
 * @author ethan
 * @since 2019/11/18
 */
public class NameConvert {


    /**
     * 驼峰转下划线
     *
     * @param param   参数名
     * @return    返回值
     */
    public static String camelToUnderline(String param) {
        if (StringUtil.isEmpty(param)) {
            return param;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    sb.append('_');
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param param  参数名
     * @return 返回值
     */
    public static String underlineToCamel(String param) {
        if (StringUtil.isEmpty(param)) {
            return param;
        }
        List<String> oldKeyList = StringUtil.stringToSList(param, "_");
        if (oldKeyList == null || oldKeyList.isEmpty()) {
            return null;
        }
        List<String> operable = new ArrayList<>(oldKeyList);
        StringBuilder sb = new StringBuilder();
        sb.append(operable.get(0));
        operable.remove(0);
        if (!operable.isEmpty()) {
            for (String key : operable) {
                char[] cs = key.toCharArray();
                if (Character.isLowerCase(cs[0])) {
                    cs[0] -= 32;
                }
                sb.append(String.valueOf(cs));
            }
        }
        return sb.toString();
    }

}
