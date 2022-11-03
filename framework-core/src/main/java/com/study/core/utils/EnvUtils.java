package com.study.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 环境变量 （）
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2022/11/3 上午10:06
 * @menu 环境变量
 */
public class EnvUtils {
    /**
     * 获取当前代码环境
     *
     * @return
     */
    public static String getEnv() {
        String env = System.getProperties().getProperty("env");
        if (StringUtils.isNotBlank(env)) {
            env = env + "-local";
        } else {
            env = System.getenv().get("ENV");
        }
        if (StringUtils.isBlank(env)) {
            env = "未指定";
        }
        return env;
    }
}
