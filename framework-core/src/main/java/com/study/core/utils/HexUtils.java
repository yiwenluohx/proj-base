package com.study.core.utils;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * ClassName: HexUtils
 * Description: 十六进制工具
 * Author: luohx
 * Date: 2022/2/23 下午4:44
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           十六进制工具
 */
public abstract class HexUtils {

    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /**
     * 转为十六进制字符串
     *
     * @param value
     * @return
     */
    public static String toHexString(int value) {
        return Integer.toHexString(value);
    }

    /**
     * 转为十六进制字符串
     *
     * @param value
     * @return
     */
    public static String toHexString(long value) {
        return Long.toHexString(value);
    }


    /**
     * 字符串转换为十六进制字符串
     *
     * @param data
     * @return
     */
    public static String encodeHexStr(String data) {
        return encodeHexStr(data, StandardCharsets.UTF_8);
    }

    /**
     * 字符串转换为十六进制字符串
     *
     * @param data
     * @param charset
     * @return
     */
    public static String encodeHexStr(String data, Charset charset) {
        return encodeHexStr(data.getBytes(charset));
    }

    private static String encodeHexStr(byte[] data) {
        return new String(encodeHex(data));
    }

    private static char[] encodeHex(byte[] data) {
        final int len = data.length;
        final char[] chars = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            // 高4位 右移补零
            chars[j++] = HEX_CHARS[(data[i] & 0xF0) >>> 4];
            // 低4位
            chars[j++] = HEX_CHARS[data[i] & 0x0F];
        }
        return chars;
    }



    /**
     * 十六进制字符数组转换为字符串
     *
     * @param hexStr
     * @return
     */
    public static String decodeHexStr(String hexStr) {
        return decodeHexStr(hexStr, StandardCharsets.UTF_8);
    }

    /**
     * 十六进制字符数组转换为字符串
     *
     * @param hexStr
     * @param charset
     * @return
     */
    public static String decodeHexStr(String hexStr, Charset charset) {
        return decodeHexStr(hexStr.toCharArray(), charset);
    }


    public static String decodeHexStr(char[] hexData, Charset charset) {
        return new String(decodeHex(hexData), charset);
    }

    public static byte[] decodeHex(char[] hexData) {
        int len = hexData.length;
        byte[] out = new byte[len >> 1];
        for (int i = 0, j = 0; j < len; i++) {
            int f = digit(hexData[j]) << 4;
            j++;
            f = f | digit(hexData[j]);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return out;
    }

    private static int digit(char ch) {
        return Character.digit(ch, 16);
    }

    /**
     * 十六进制字符串转为BigInteger
     *
     * @param hexStr
     * @return
     */
    public static BigInteger toBigInteger(String hexStr) {
        if (hexStr == null) {
            return null;
        }
        return new BigInteger(hexStr, 16);
    }

}