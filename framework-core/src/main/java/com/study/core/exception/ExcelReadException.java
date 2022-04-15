package com.study.core.exception;

/**
 * ClassName: ExcelReadException
 * Description: Excel读取异常
 *
 * @Author: luohx
 * Date: 2022/3/29 下午2:28
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           Excel读取异常
 */
public class ExcelReadException extends Exception {
    private static final long serialVersionUID = 1L;

    public ExcelReadException(String message) {
        super(message);
    }

    public ExcelReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
