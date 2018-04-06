package org.nefu.softlab.weiboAPI.common.util;

import org.junit.jupiter.api.Test;

import java.security.MessageDigest;

/**
 * Created by Jiaxu_Zou on 2018-4-6
 *
 * Util for MD5
 */
public class MD5Util {

    public static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

    @Test
    public void test() {
        System.out.println(MD5("zjx199628"));
        System.out.println("51D22F9C6B899ACB1CDD12F4BB9700E9");
    }

}
