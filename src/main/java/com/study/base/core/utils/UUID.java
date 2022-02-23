package com.study.base.core.utils;

/**
 * ClassName: UUID
 * Description: UUID 生成
 * @Author: luohx
 * Date: 2022/2/23 下午4:52
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           UUID 生成
 */
public abstract class UUID {

    public static String random() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

}