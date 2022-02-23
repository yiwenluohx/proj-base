package com.study.base.core.utils;

/**
 * ClassName: CharUtils
 * Description: 字符操作工具类
 * @Author: luohx
 * Date: 2022/2/23 下午4:37
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           字符操作工具类
 */
public abstract class CharUtils {

    /**
     * 按字节数截取 UTF-8 字符串
     *
     * @param str
     * @param byteSize
     * @return
     */
    public static String substringUTF8(String str, int byteSize) {
        if (str == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        int len = 0;
        for (int i = 0; i < str.length();) {
            int codePoint = str.codePointAt(i);
            char[] chars = Character.toChars(codePoint);
            i += chars.length;

            if (chars.length == 1) {
                char c = chars[0];
                if (c < 0x0080) {
                    len += 1;
                }
                else if (c >= 0x0080 && c <= 0x07ff) {
                    len += 2;
                }
                else if (c >= 0x0800 && c <= 0xffff) {
                    len += 3;
                } else {
                    continue;
                }
            } else if (chars.length == 2) {
                len += 4;
            } else {
                continue;
            }
            if (len <= byteSize) {
                result.append(chars);
            } else {
                break;
            }
        }
        return result.toString();
    }

}