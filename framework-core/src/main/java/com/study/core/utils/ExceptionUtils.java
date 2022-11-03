package com.study.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2022/11/2 下午6:04
 * @menu
 */
@Slf4j
public class ExceptionUtils {
    /**
     * 功能描述: 异常捕获
     *
     * @param:
     * @return:
     * @auther: songdi
     * @date: 2022/1/12 下午2:48
     */
    public static <T> T handler(Supplier<T> supplier) {
        return handler(supplier, "");
    }

    /**
     * 功能描述: 异常捕获
     *
     * @param:
     * @return:
     * @auther: songdi
     * @date: 2022/1/12 下午2:48
     */
    public static <T> T handler(Supplier<T> supplier, String errorMsg) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error(errorMsg, e);
            return null;
        }
    }

    /**
     * 功能描述: 异常捕获
     *
     * @param:
     * @return:
     * @auther: songdi
     * @date: 2022/1/12 下午2:49
     */
    public static <T> T handler(Supplier<T> supplier, Supplier<T> defaultSupplier) {
        return handler(supplier, "", defaultSupplier);
    }

    /**
     * 功能描述: 异常捕获
     *
     * @param:
     * @return:
     * @auther: songdi
     * @date: 2022/1/12 下午2:49
     */
    public static <T> T handler(Supplier<T> supplier, String errorMsg, Supplier<T> defaultSupplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error(errorMsg, e);
            return defaultSupplier.get();
        }
    }
}
