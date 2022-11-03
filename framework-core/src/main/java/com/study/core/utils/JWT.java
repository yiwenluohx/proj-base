package com.study.core.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2022/11/2 下午5:32
 * @menu
 */
public class JWT {
    private static final Logger logger = LoggerFactory.getLogger(JWT.class);

    /**
     * 解析为 SignedJWT
     *
     * @param jwtStr
     * @return
     * @author caowuchao
     * @since 2022年9月25日
     */
    public static SignedJWT parseSignedJWT(String jwtStr) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(jwtStr);
            return signedJWT;
        } catch (ParseException e) {
            throw new IllegalArgumentException("Illegal argument: " + jwtStr, e);
        }
    }

    /**
     * 获取签名头信息
     *
     * @param jwtStr
     * @return
     * @author caowuchao
     * @since 2022年9月25日
     */
    public static JWSHeader getHeader(String jwtStr) {
        return parseSignedJWT(jwtStr).getHeader();
    }

    /**
     * 获取 JWT 声明的数据集合
     *
     * @param jwtStr
     * @return
     * @author caowuchao
     * @since 2022年9月25日
     */
    public static JWTClaimsSet getClaimsSet(String jwtStr) {
        try {
            return parseSignedJWT(jwtStr).getJWTClaimsSet();
        } catch (ParseException e) {
            throw new IllegalArgumentException("Illegal argument: " + jwtStr, e);
        }
    }

    /**
     * 获取可用时间的毫秒数
     *
     * @param jwtStr
     * @return
     * @author caowuchao
     * @since 2022年10月14日
     */
    public static long getAvailableTimeMillis(String jwtStr) {
        JWTClaimsSet claims = getClaimsSet(jwtStr);
        Date date = claims.getExpirationTime();
        if (date == null) {
            return 0L;
        }
        return date.getTime() - System.currentTimeMillis();
    }

    /**
     * 基于哈希运算的消息认证码
     *
     * @author caowuchao
     * @since 2022年9月25日
     * @version 1.0
     */
    public static class HMACSigner {

        private static final JWSHeader JWS_HEADER = new JWSHeader(JWSAlgorithm.HS256);

        public static String sign(byte[] secret, Map<String, Object> claims, LocalDateTime expirationTime) {
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
                    // 签发时间
                    .issueTime(Calendar.getInstance().getTime())
                    // 过期时间
                    .expirationTime(Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant()));

            if (claims != null) {
                for (String key : claims.keySet()) {
                    builder.claim(key, claims.get(key));
                }
            }
            JWTClaimsSet claimsSet = builder.build();

            try {
                JWSSigner jwsSigner = new MACSigner(secret);
                SignedJWT signedJWT = new SignedJWT(JWS_HEADER, claimsSet);
                signedJWT.sign(jwsSigner);
                return signedJWT.serialize();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * 检查签名是否有效
         *
         * @param secret
         * @param jwtStr
         * @return
         * @author caowuchao
         * @since 2022年9月25日
         */
        public static boolean verify(byte[] secret, String jwtStr) {
            try {
                SignedJWT signedJWT = parseSignedJWT(jwtStr);
                JWSVerifier jwsVerifier = new MACVerifier(secret);
                return signedJWT.verify(jwsVerifier);
            } catch (Exception e) {
                logger.error("HAMC 验证异常: {}", jwtStr, e);
            }
            return false;
        }
    }


    /**
     * RSA 签名
     *
     * @author caowuchao
     * @since 2022年9月25日
     * @version 1.0
     */
    public static class RSASigner {

        private static final JWSHeader JWS_HEADER = new JWSHeader(JWSAlgorithm.RS256);

        public static String sign(PrivateKey privateKey, String subjectId, Map<String, Object> claims, LocalDateTime expirationTime) {
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
                    // 面向的用户
                    .subject(subjectId)
                    // 签发时间
                    .issueTime(Calendar.getInstance().getTime())
                    // 过期时间
                    .expirationTime(Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant()))
                    .jwtID(UUID.random());

            if (claims != null) {
                for (String key : claims.keySet()) {
                    builder.claim(key, claims.get(key));
                }
            }
            JWTClaimsSet claimsSet = builder.build();

            try {
                SignedJWT signedJWT = new SignedJWT(JWS_HEADER, claimsSet);
                JWSSigner jwsSigner = new RSASSASigner(privateKey);
                signedJWT.sign(jwsSigner);
                return signedJWT.serialize();
            } catch (JOSEException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * 检查签名是否有效
         *
         * @param publicKey
         * @param jwtStr
         * @return
         * @author caowuchao
         * @since 2022年9月25日
         */
        public static boolean verify(RSAPublicKey publicKey, String jwtStr) {
            try {
                SignedJWT signedJWT = parseSignedJWT(jwtStr);
                JWSVerifier verifier = new RSASSAVerifier(publicKey);
                return signedJWT.verify(verifier);
            } catch (Exception e) {
                logger.error("RSA 验证异常: {}", jwtStr, e);
            }
            return false;
        }
    }
}
