package com.study.base.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClassName: BearerTokenUtils
 * Description:
 * @Author: luohx
 * Date: 2022/2/23 下午4:34
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public abstract class BearerTokenUtils {

    private static final Logger logger = LoggerFactory.getLogger(BearerTokenUtils.class);

    private static final String TOKEN_PREFIX = "Bearer ";


    /**
     * 获取 token
     *
     * @param authorization
     * @return
     */
    public static String getToken(String authorization) {
        logger.info("authorization: {}", authorization);
        if (StringUtils.isNotBlank(authorization) && authorization.startsWith(TOKEN_PREFIX)) {
            return StringUtils.substring(authorization, TOKEN_PREFIX.length());
        }
        return null;
    }

    /**
     * token 构建 Authorization
     *
     * @param token
     * @return
     */
    public static String buildAuthorization(String token) {
        return TOKEN_PREFIX + token;
    }

}