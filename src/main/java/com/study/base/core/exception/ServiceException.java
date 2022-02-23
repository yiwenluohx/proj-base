package com.study.base.core.exception;

import com.study.base.core.enums.ErrorType;

/**
 * ClassName: ServiceException
 * Description:
 *
 * @Author: luohx
 * Date: 2022/2/22 上午11:16
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public class ServiceException extends BusinessException {
    public ServiceException(ErrorType type) {
        super(type);
    }

    public ServiceException(ErrorType type, Throwable cause) {
        super(type, cause);
    }

    /**
     * 默认异常
     *
     * @param message
     */
    public ServiceException(String message) {
        super("-1", message);
    }

    public ServiceException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ServiceException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public ServiceException(int errorCode, String message) {
        super(String.valueOf(errorCode), message);
    }

    public ServiceException(int errorCode, String message, Throwable cause) {
        super(String.valueOf(errorCode), message, cause);
    }
}