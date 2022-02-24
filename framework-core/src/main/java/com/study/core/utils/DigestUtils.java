package com.study.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName: DigestUtils
 * Description: 信息摘要算法工具类
 * @Author: luohx
 * Date: 2022/2/23 下午4:43
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0            信息摘要算法工具类
 */
public abstract class DigestUtils {

    private static final String MD5_ALGORITHM = "MD5";
    private static final String SHA1_ALGORITHM = "SHA-1";

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private static final char[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final int BUFFER_SIZE = 4096;

    /**
     * 获取字符串的MD5消息摘要
     *
     * @param str
     * @return
     */
    public static String md5Hex(final String str) {
        return md5Hex(str.getBytes(UTF_8));
    }

    /**
     * 获取文件的MD5消息摘要
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String md5Hex(final File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            return md5Hex(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取输入流的MD5消息摘要
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static String md5Hex(final InputStream data) throws IOException {
        MessageDigest messageDigest = getMessageDigest(MD5_ALGORITHM);
        final byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = data.read(buffer)) != -1) {
            messageDigest.update(buffer, 0, bytesRead);
        }
        byte[] digest = messageDigest.digest();
        return encodeHexString(digest);
    }

    /**
     * 获取字节数组的MD5消息摘要
     *
     * @param bytes
     * @return
     */
    public static String md5Hex(final byte[] bytes) {
        MessageDigest messageDigest = getMessageDigest(MD5_ALGORITHM);
        byte[] digest = messageDigest.digest(bytes);
        return encodeHexString(digest);
    }

    private static MessageDigest getMessageDigest(final String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String encodeHexString(final byte[] bytes) {
        return new String(encodeHex(bytes));
    }


    /**
     * 将字节数组转换为十六进制字符数组
     *
     * @param data
     * @return
     */
    protected static char[] encodeHex(final byte[] data) {
        final int len = data.length;
        final char[] out = new char[len << 1];
        // 一个字节使用两个十六进制字符表示
        for (int i = 0, j = 0; i < len; i++) {
            out[j++] = HEX_CHARS[(0xF0 & data[i]) >>> 4];
            out[j++] = HEX_CHARS[0x0F & data[i]];
        }
        return out;
    }


    /**
     * 获取字符串的SHA1消息摘要
     *
     * @param str
     * @return
     */
    public static String sha1Hex(final String str) {
        return sha1Hex(str.getBytes(UTF_8));
    }

    /**
     * 获取文件的SHA1消息摘要
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String sha1Hex(final File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            return sha1Hex(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取输入流的SHA1消息摘要
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static String sha1Hex(final InputStream data) throws IOException {
        MessageDigest messageDigest = getMessageDigest(SHA1_ALGORITHM);
        final byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = data.read(buffer)) != -1) {
            messageDigest.update(buffer, 0, bytesRead);
        }
        byte[] digest = messageDigest.digest();
        return encodeHexString(digest);
    }

    /**
     * 获取字节数组的SHA1消息摘要
     *
     * @param bytes
     * @return
     */
    public static String sha1Hex(final byte[] bytes) {
        MessageDigest messageDigest = getMessageDigest(SHA1_ALGORITHM);
        byte[] digest = messageDigest.digest(bytes);
        return encodeHexString(digest);
    }


}