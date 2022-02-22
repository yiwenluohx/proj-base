package com.study.base.core.enums;

/**
 * ClassName: ErrorType
 * Description:
 *
 * @Author: luohx
 * Date: 2022/2/22 上午11:18
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface ErrorType {
    /**
     * code码
     *
     * @return
     */
    String code();

    /**
     * 消息
     *
     * @return
     */
    String message();
}
