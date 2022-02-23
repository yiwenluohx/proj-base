package com.study.base.generator;

import com.study.base.generator.core.Application;
import com.study.base.generator.core.ApplicationContext;
import com.study.base.generator.core.SimpleApplicationContext;
import com.study.base.generator.core.Task;
import com.study.base.generator.entity.CodeConfig;
import com.study.base.generator.task.*;
import lombok.Data;
import lombok.SneakyThrows;

import javax.sql.DataSource;

/**
 * ClassName: CodeGenerator
 * Description: 启动程序
 *
 * @Author: luohx
 * Date: 2022/2/22 下午5:35
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0              启动程序
 */
@Data
public class CodeGenerator {
    private ApplicationContext context = new SimpleApplicationContext();
    private CodeConfig config = new CodeConfig();
    private DataSource dataSource;
    private Application application;
    //是否自定义生成类
    private boolean customDefined = false;

    {
        application = new Application(context, CodeGenerator.class.getSimpleName());
        application
                .registerTask(InitTask.class)
                .registerTask(EntityTask.class);
    }

    public CodeGenerator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CodeGenerator(DataSource dataSource, CodeConfig config) {
        this.dataSource = dataSource;
        this.config = config;
    }

    public void start() {
        context.setAttribute("dataSource", dataSource);
        //设置配置信息
        context.setAttribute("config", config);
        if(!customDefined) {
            application
                    .registerTask(XmlTask.class)
                    .registerTask(MapperTask.class)
                    .registerTask(ServiceTask.class)
                    .registerTask(ServiceImplTask.class);
        }
        application.start();
    }

    @SneakyThrows
    public CodeGenerator registerTask(Class<? extends Task> clazz) {
        customDefined = true;
        application.registerTask(clazz);
        return this;
    }
}
