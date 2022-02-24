package com.study.core.enums;

/**
 * ClassName: ErrorType
 * Description: 业务异常类型
 *
 * @Author: luohx
 * Date: 2022/2/22 上午11:18
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0             业务异常类型
 */
public interface ErrorType {

    /**
     * 错误码
     *
     * @return
     */
    String code();

    /**
     * 错误消息
     *
     * @return
     */
    String message();
}
