package com.study.base.core.ro;

import java.io.Serializable;

/**
 * ClassName: Resp
 * Description:
 * @Author: luohx
 * Date: 2022/2/22 下午5:29
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public class Resp {

    private static final String SUCCESS_CODE = "0";
    private static final String SUCCESS_MSG = "";

    private static final String ERROR_CODE = "-1";
    private static final String ERROR_MSG = "error";

    private static final String DATA_DEFAULT = null;

    public static RdfaResult ok() {
        return Resp.ok(SUCCESS_CODE, SUCCESS_MSG, (Serializable) DATA_DEFAULT);
    }

    public static <T> RdfaResult ok(T data) {
        return RdfaResult.success((Serializable) data);
    }

    public static <T> RdfaResult ok(String code, String message, T data) {
        return RdfaResult.success(code, message, (Serializable) data);
    }

    public static RdfaResult error() {
        return RdfaResult.fail(ERROR_CODE, ERROR_MSG);
    }

    public static RdfaResult error(String message) {
        return RdfaResult.fail(ERROR_CODE, message);
    }

    public static RdfaResult error(String code, String message) {
        return RdfaResult.fail(code, message);
    }

    public static RdfaResult error(int code, String message) {
        return error(String.valueOf(code), message);
    }
}