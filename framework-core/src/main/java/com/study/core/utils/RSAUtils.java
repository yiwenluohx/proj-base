package com.study.core.utils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * ClassName: RSAUtils
 * Description: RSA加密解密工具
 * @Author: luohx
 * Date: 2022/2/23 下午4:49
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0             RSA加密解密工具
 */
public class RSAUtils {

    private static String ALGORITHM = "RSA";


    public static RSAPublicKey getPublicKey(String publicKeyStr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            // 密钥
            byte[] encodedKey = Base64.getDecoder().decode(publicKeyStr.getBytes());
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(encodedKey);
            PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
            return (RSAPublicKey) publicKey;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密数据
     *
     * @param dataStr
     * @param publicKey
     * @return
     */
    public static String encrypt(String dataStr, PublicKey publicKey) {
        try {
            byte[] data = dataStr.getBytes();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return new String(Base64.getEncoder().encode(cipher.doFinal(data)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey getPrivateKey(String privateKeyStr) {
        try {
            byte[] encodedKey = Base64.getDecoder().decode(privateKeyStr.getBytes());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密数据
     *
     * @param content
     * @param privateKey
     * @return
     */
    public static String decrypt(String content, PrivateKey privateKey) {
        try {
            byte[] data = Base64.getDecoder().decode(content.getBytes());
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(data);
            return new String(output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}