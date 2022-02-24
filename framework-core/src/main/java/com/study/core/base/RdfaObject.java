package com.study.core.base;

import java.io.Serializable;

/**
 * ClassName: RdfaObject
 * Description:
 * @Author: luohx
 * Date: 2022/2/22 下午4:00
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface RdfaObject extends Serializable {

    /**
     * to be finalized by application develper according to actual requirement.
     *
     * @return String log
     */
    String toLog();
}