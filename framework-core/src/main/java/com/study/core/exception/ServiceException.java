package com.study.core.exception;

import com.study.core.enums.ErrorType;
import lombok.Getter;
import org.slf4j.event.Level;

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
@Getter
public class ServiceException extends BusinessException {
    /**
     * 日志打印级别
     */
    private Level logLevel = Level.ERROR;

    /**
     * 扩展信息
     */
    private Object extMsg = null;

    /**
     * 父类构造函数
     *
     * @param type
     */
    public ServiceException(ErrorType type) {
        super(type);
    }

    /**
     * 父类构造函数
     *
     * @param type
     */
    public ServiceException(ErrorType type, Throwable cause) {
        super(type, cause);
    }

    /**
     * 默认Error异常
     *
     * @param message
     */
    public ServiceException(String message) {
        super("-1", message);
    }

    /**
     * 默认异常，并传递是否推送日志
     *
     * @param message  异常信息
     * @param logLevel 异常类型,拦截器控制
     */
    public ServiceException(String message, Level logLevel) {
        this(message);
        this.logLevel = logLevel;
    }

    /**
     * 默认异常，并传递是否推送日志
     *
     * @param message  异常信息
     * @param logLevel 异常类型,拦截器控制
     * @param extMsg   扩展字段
     */
    public ServiceException(String message, Level logLevel, Object extMsg) {
        this(message, logLevel);
        this.extMsg = extMsg;
    }

    public ServiceException(int errorCode, String message) {
        super(String.valueOf(errorCode), message);
    }

    public ServiceException(int errorCode, String message, Level logLevel) {
        this(errorCode, message);
        this.logLevel = logLevel;
    }

    /**
     * 异常信息 errorCode为int类型
     *
     * @param errorCode 异常编码
     * @param message   异常信息
     * @param logLevel  日志打印级别
     * @param extMsg    扩展字段
     */
    public ServiceException(int errorCode, String message, Level logLevel, Object extMsg) {
        this(errorCode, message, logLevel);
        this.extMsg = extMsg;
    }

    public ServiceException(int errorCode, String message, Throwable cause) {
        super(String.valueOf(errorCode), message, cause);
    }

    public ServiceException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ServiceException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

}