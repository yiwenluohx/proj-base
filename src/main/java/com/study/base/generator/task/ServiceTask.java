package com.study.base.generator.task;

import com.study.base.core.exception.ServiceException;
import com.study.base.generator.core.AbstractTask;
import com.study.base.generator.core.ApplicationContext;
import com.study.base.generator.entity.CodeConfig;
import com.study.base.generator.entity.EntityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ServiceTask
 * Description: Service接口
 * @Author: luohx
 * Date: 2022/2/23 上午10:46
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0              Service接口
 */
@Slf4j
public class ServiceTask extends AbstractTask {

    private static String IMPL_FLT_NAME = "service.ftl";

    @Override
    protected boolean doInternal(ApplicationContext context) {
//        log.info("service task start");
        CodeConfig config = (CodeConfig) context.getAttribute("config");
        List<EntityClass> entitys = (List<EntityClass>) context.getAttribute("entitys");
        entitys.forEach(entityClass -> {
            exec(config,entityClass);
        });
//        log.info("service task end");
        return true;
    }

    private void exec(CodeConfig config,EntityClass entityClass){
        String type = config.getType().toString().toLowerCase();
        String serviceName = entityClass.getTableAlias() + config.getServiceSuffix();

        Map<String, Object> data = new HashMap<>();
        data.put("type", type);
        data.put("package", entityClass.getBasePackage() + "." + config.getChildPackageService());
        data.put("author", System.getProperty("user.name"));
        data.put("time", time);
        data.put("tableName", entityClass.getTableName());
        data.put("entityName", entityClass.getClassName());
        data.put("entityPackage",entityClass.getAllPackage() + "." + entityClass.getClassName());
        data.put("serviceName", serviceName);
        if (config.getType().equals(CodeConfig.TypeEnum.MYBATIS_PLUS)) {
            IMPL_FLT_NAME = "mybatisPlusService.ftl";
        } else if (config.getType().equals(CodeConfig.TypeEnum.MYBATIS_PLUS_EXT)) {
            IMPL_FLT_NAME = "mybatisPlusService-Ext.ftl";
        }

        File file = new File(
                config.getProjectPath() +
                        config.getSaveBasePath() +
                        config.getPackagePathService() + serviceName + ".java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            freemarker.template.Configuration cfg = getConfiguration();
            cfg.getTemplate(IMPL_FLT_NAME).process(data,
                    new FileWriter(file));
        } catch (Exception e) {
            log.error("{}",e);
            throw new ServiceException("XML文件生成报错");
        }
        log.info("{}.java 生成成功",serviceName);
    }
}