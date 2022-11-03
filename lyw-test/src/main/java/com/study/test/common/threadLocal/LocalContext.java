package com.study.test.common.threadLocal;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.Optional;

/**
 * 本地上下文
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2022/05/23 12:38
 */
public class LocalContext {

    private static final TransmittableThreadLocal<UserInfo> threadLocal = new TransmittableThreadLocal<UserInfo>();

    /**
     * 设置上下文用户
     *
     * @param eid          企业ID
     * @param userId       用户id
     * @param functionCode functionCode
     * @param uri          uri
     */
    public static void setLocalContext(Long eid, Long userId, String functionCode, String uri) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEid(eid);
        userInfo.setUserId(userId);
        userInfo.setFunctionCode(functionCode);
        userInfo.setUri(uri);
        LocalContext.setUserInfo(userInfo);
    }

    public static void setUserInfo(UserInfo userInfo) {
        threadLocal.set(userInfo);
    }

    public static UserInfo getUserInfo() {
        return threadLocal.get();
    }

    // 清空方法
    public static void clear() {
        threadLocal.remove();
    }

    /**
     * 得到企业id
     *
     * @return {@link Long}
     */
    public static Long getEid() {
        return Optional.ofNullable(LocalContext.getUserInfo()).map(x -> x.getEid()).orElse(null);
    }

    /**
     * 得到用户id
     *
     * @return {@link Long}
     */
    public static Long getUserId() {
        return Optional.ofNullable(LocalContext.getUserInfo()).map(x -> x.getUserId()).orElse(null);
    }

    /**
     * 把functionCode
     *
     * @return {@link String}
     */
    public static String getFunctionCode() {
        return Optional.ofNullable(LocalContext.getUserInfo()).map(x -> x.getFunctionCode()).orElse("");
    }

    /**
     * 获取uri
     *
     * @return {@link String}
     */
    public static String getUri() {
        return Optional.ofNullable(LocalContext.getUserInfo()).map(x -> x.getUri()).orElse("");
    }
}
