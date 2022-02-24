package com.study.batisGenerator.core;

/**
 * ClassName: SimpleApplicationContext
 * Description:
 * @Author: luohx
 * Date: 2022/2/23 上午9:32
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public class SimpleApplicationContext extends ApplicationContext
{
    @Override
    public void setAttribute(String key, Object obj)
    {
        this.ctx.put(key, obj);
    }

    @Override
    public Object getAttribute(String key)
    {
        return this.ctx.get(key);
    }

}