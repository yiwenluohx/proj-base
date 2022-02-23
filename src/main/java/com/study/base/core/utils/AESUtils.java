package com.study.base.core.utils;

import com.study.base.core.exception.CryptoException;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * ClassName: AESUtils
 * Description: AES工具
 * Author: luohx
 * Date: 2022/2/23 下午4:31
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           AES工具
 */
public class AESUtils {

    /**
     * AES加密
     *
     * @param content
     * @return
     * @throws CryptoException
     */
    public static String encrypt(byte[] keyBytes, String content) {
        try {
            Cipher cipher = buildCipher(keyBytes, Cipher.ENCRYPT_MODE);
            byte[] encryptBytes = cipher.doFinal(content.getBytes());
            return Base64.getEncoder().encodeToString(encryptBytes);
        } catch (Exception e) {
            throw new CryptoException("AES加密异常", e);
        }
    }

    /**
     * AES解密
     *
     * @param content
     * @return
     * @throws CryptoException
     */
    public static String decrypt(byte[] keyBytes, String content) {
        try {
            Cipher cipher = buildCipher(keyBytes, Cipher.DECRYPT_MODE);
            return new String(cipher.doFinal(Base64.getDecoder().decode(content.getBytes())));
        } catch (Exception e) {
            throw new CryptoException("AES解密异常", e);
        }
    }

    private static Cipher buildCipher(byte[] keyBytes, int opmode) throws Exception {
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        byte[] iv = new byte[12];
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(opmode, key, new GCMParameterSpec(128, iv));
        return cipher;
    }

}