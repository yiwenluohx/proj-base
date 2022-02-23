package com.study.base.core.utils;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * ClassName: SecureRandomUtils
 * Description: SecureRandom 工具
 * @Author: luohx
 * Date: 2022/2/23 下午4:50
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0            SecureRandom 工具
 */
public abstract class SecureRandomUtils {

    // 32个字节 256位
    private static final int DEFAULT_KEY_LENGTH = 32;

    /**
     * 生成 32 字节安全随机数
     *
     * @return
     */
    public static String random() {
        return random(DEFAULT_KEY_LENGTH);
    }

    /**
     * 生成指定字节数的安全随机数
     *
     * @param byteSize
     * @return
     */
    public static String random(int byteSize) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[byteSize];
        secureRandom.nextBytes(bytes);
        byte[] base64Bytes = Base64.getEncoder().encode(bytes);
        return new String(base64Bytes);
    }

}