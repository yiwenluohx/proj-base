package com.study.base.core.utils;

import com.study.base.core.enums.KeyPairType;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * ClassName: KeyPairUtils
 * Description: 密钥对工具类
 * @Author: luohx
 * Date: 2022/2/23 下午4:46
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           密钥对工具类
 */
public abstract class KeyPairUtils {

    public static PublicKey getPublicKey(byte[] encodedKey, KeyPairType type) {
        try {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(encodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(type.getAlgorithm());
            PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
            return publicKey;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey getPrivateKey(byte[] encodedKey, KeyPairType type) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(type.getAlgorithm());
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static KeyPair genKeyPair(KeyPairType type) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(type.getAlgorithm());
            SecureRandom secureRandom = new SecureRandom();
            keyPairGenerator.initialize(type.getKeysize(), secureRandom);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 生成密钥对
     *
     * @param type
     * @return
     */
    public static Pair<String, String> getKeyPairBase64(KeyPairType type) {
        KeyPair keyPair = genKeyPair(type);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        byte[] publicKeyBytes = Base64.getEncoder().encode(publicKey.getEncoded());
        byte[] privateKeyBytes = Base64.getEncoder().encode(privateKey.getEncoded());

        // 密钥字符串
        String publicKeyStr = new String(publicKeyBytes);
        String privateKeyStr = new String(privateKeyBytes);

        Pair<String, String> pair = ImmutablePair.of(publicKeyStr, privateKeyStr);
        return pair;
    }

}