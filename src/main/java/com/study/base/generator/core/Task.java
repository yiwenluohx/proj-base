package com.study.base.generator.core;

/**
 * ClassName: Task
 * Description: 任务链表
 * @Author: luohx
 * Date: 2022/2/22 下午5:37
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0             任务链表
 */
public interface Task {
    /**
     * 执行
     * @param context
     * @return
     */
    boolean perform(ApplicationContext context);

    /**
     * 是否有下一个
     * @return
     */
    boolean hasNext();

    /**
     * 注册下一个任务
     * @param nextTask
     */
    void registerNextTask(Task nextTask);

    /**
     * 获取下一个任务
     * @return
     */
    Task next();
}
