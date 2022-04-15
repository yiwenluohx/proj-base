package com.study.core.exception;

/**
 * ClassName: ExcelWriteException
 * Description: Excel写入异常
 *
 * @Author: luohx
 * Date: 2022/3/29 下午2:26
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           Excel写入异常
 */
public class ExcelWriteException extends Exception {
    private static final long serialVersionUID = 1L;

    public ExcelWriteException(String message) {
        super(message);
    }

    public ExcelWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
