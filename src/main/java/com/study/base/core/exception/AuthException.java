package com.study.base.core.exception;

import com.study.base.core.enums.ErrorType;

/**
 * ClassName: AuthException
 * Description:
 * @Author: luohx
 * Date: 2022/2/22 下午3:33
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public class AuthException extends BusinessException {
    public AuthException(ErrorType type) {
        super(type);
    }

    public AuthException(ErrorType type, Throwable cause) {
        super(type, cause);
    }

    public AuthException(String message) {
        super("-1", message);
    }

    public AuthException(String errorCode, String message) {
        super(errorCode, message);
    }

    public AuthException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public AuthException(int errorCode, String message) {
        super(String.valueOf(errorCode), message);
    }

    public AuthException(int errorCode, String message, Throwable cause) {
        super(String.valueOf(errorCode), message, cause);
    }
}