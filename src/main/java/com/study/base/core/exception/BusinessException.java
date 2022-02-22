package com.study.base.core.exception;

import com.study.base.core.enums.ErrorType;

/**
 * ClassName: BusinessException
 * Description:
 * @Author: luohx
 * Date: 2022/2/22 上午11:16
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errorCode;

    public BusinessException(ErrorType type) {
        super(type.message());
        this.errorCode = type.code();
    }

    public BusinessException(ErrorType type, Throwable cause) {
        super(type.message(), cause);
        this.errorCode = type.code();
    }

    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BusinessException(int errorCode, String message) {
        super(message);
        this.errorCode = String.valueOf(errorCode);
    }

    public BusinessException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = String.valueOf(errorCode);
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}