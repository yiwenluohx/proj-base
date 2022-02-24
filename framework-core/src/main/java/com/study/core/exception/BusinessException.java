package com.study.core.exception;

import com.study.core.enums.ErrorType;

/**
 * ClassName: BusinessException
 * Description: 定义业务异常
 * @Author: luohx
 * Date: 2022/2/22 上午11:16
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0             定义业务异常
 */
public class BusinessException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 实现ErrorType接口构造
     * 构造方法
     * @param type
     * @since 2021年11月5日
     */
    public BusinessException(ErrorType type) {
        super(type.message());
        this.errorCode = type.code();
    }

    /**
     * 实现ErrorType接口构造
     * 构造方法
     * @param type
     * @param cause
     * @since 2021年11月5日
     */
    public BusinessException(ErrorType type, Throwable cause) {
        super(type.message(), cause);
        this.errorCode = type.code();
    }

    /**
     *
     * 构造方法
     * @param errorCode
     * @param message
     * @since 2021年11月5日
     */
    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     *
     * 构造方法
     * @param errorCode
     * @param message
     * @param cause
     * @since 2021年11月5日
     */
    public BusinessException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * 请使用errorCode为String类型构造方法
     * 构造方法
     * @param errorCode
     * @param message
     * @since 2021年11月5日
     */
    public BusinessException(int errorCode, String message) {
        super(message);
        this.errorCode = String.valueOf(errorCode);
    }

    /**
     * 请使用errorCode为String类型构造方法
     * 构造方法
     * @param errorCode
     * @param message
     * @param cause
     * @since 2021年11月5日
     */
    public BusinessException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = String.valueOf(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}