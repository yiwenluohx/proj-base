package com.study.core.exception;

/**
 * ClassName: CryptoException
 * Description: 加密解密异常
 * @Author: luohx
 * Date: 2022/2/23 下午4:30
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           加密解密异常
 */
public class CryptoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}