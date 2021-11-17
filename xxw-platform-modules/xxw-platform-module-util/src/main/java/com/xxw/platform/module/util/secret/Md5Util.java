package com.xxw.platform.module.util.secret;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.security.MessageDigest;

public class Md5Util {

    private static final String CHARSET = "UTF-8";

    private static final String ENCODE = "MD5";

    private Md5Util() {
        throw new IllegalStateException("MD5Util class");
    }

    public static String encodeToString(String src) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ENCODE);
            byte[] bs = digest.digest(src.getBytes(CHARSET));
            String hexString = "";
            for (byte b : bs) {
                int temp = b & 255;
                if (temp < 16 && temp >= 0) {
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (Exception e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    public static String encodeToString(byte[] src) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ENCODE);
            byte[] bs = digest.digest(src);
            String hexString = "";
            for (byte b : bs) {
                int temp = b & 255;
                if (temp < 16 && temp >= 0) {
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (Exception e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    public static byte[] encodeToByte(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(ENCODE);
            md5.update(src.getBytes(CHARSET));
            return md5.digest();
        } catch (Exception e) {
            return ExceptionUtils.rethrow(e);
        }
    }

}