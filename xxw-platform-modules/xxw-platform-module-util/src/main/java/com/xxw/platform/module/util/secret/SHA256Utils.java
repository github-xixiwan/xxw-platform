package com.xxw.platform.module.util.secret;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SHA256Utils {
    private static final String strType = "SHA-256";

    /***
     * 字符串 SHA 加密
     *
     * @param strText
     * @return
     */
    public static byte[] SHA(final String strText) {
        // 是否是有效字符串
        if (strText != null && strText.length() > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                return messageDigest.digest();

            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        } else {
            return null;
        }

    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    public static String bytetoHex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //16进制数值长度为2,长度为1时补0
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static String encryptPassword(String password) {
        byte[] hashBytes = SHA256Utils.SHA(password);
        return Base64.getEncoder().encodeToString(SHA256Utils.bytetoHex(hashBytes).getBytes());
    }

}