package com.study.core.utils;


import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2022/11/2 下午5:09
 * @menu
 */
public class JWTUtils {

    /**
     * 获取用户ID
     *
     * @param token
     * @return
     * @author caowuchao
     * @since 2021年9月13日
     */
    public static long getSubjectId(String token) {
        JWTClaimsSet claims = JWT.getClaimsSet(token);
        String subject = claims.getSubject();
        return NumberUtils.toLong(subject);
    }
}
