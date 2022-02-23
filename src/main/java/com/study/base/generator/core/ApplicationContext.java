package com.study.base.generator.core;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: ApplicationContext
 * Description: 当前应用程序上下文
 * @Author: luohx
 * Date: 2022/2/22 下午6:10
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           当前应用程序上下文
 */
@Data
public abstract class ApplicationContext
{
    protected Map<String, Object> ctx = new ConcurrentHashMap<>();

    public abstract void setAttribute(String key, Object obj);

    public abstract Object getAttribute(String key);

}
