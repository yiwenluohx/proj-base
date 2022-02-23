package com.study.base.generator.core;

import com.study.base.generator.utils.DateUtils;
import freemarker.template.TemplateExceptionHandler;
import java.io.IOException;
import java.util.Date;

/**
 * ClassName: AbstractTask
 * Description: 任务抽象类
 *
 * @Author: luohx
 * Date: 2022/2/22 下午6:14
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0              任务抽象类
 */
public abstract class AbstractTask implements Task {
    public static final String time = DateUtils.getFormatDate(new Date(), "yyyy年MM月dd日");

    private Task nextTask= null;
    private boolean hasNext = false;

    public freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setClassForTemplateLoading(this.getClass(), "/ftl");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    protected abstract boolean doInternal(ApplicationContext context);

    @Override
    public boolean perform(ApplicationContext context) {
        return doInternal(context);
    }

    @Override
    public boolean hasNext() {
        return this.hasNext;
    }

    @Override
    public void registerNextTask(Task nextTask) {
        this.nextTask = nextTask;
        this.hasNext = !(null == nextTask);
    }

    @Override
    public Task next() {
        return this.nextTask;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }
}
