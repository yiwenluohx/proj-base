package com.study.batisGenerator.task;

import com.study.core.exception.ServiceException;
import com.study.batisGenerator.core.AbstractTask;
import com.study.batisGenerator.core.ApplicationContext;
import com.study.batisGenerator.entity.CodeConfig;
import com.study.batisGenerator.entity.EntityClass;
import com.study.batisGenerator.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ServiceImplTask
 * Description: service实现类
 * @Author: luohx
 * Date: 2022/2/23 上午10:45
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0            Mapper接口类
 */
@Slf4j
public class ServiceImplTask extends AbstractTask {
    private static String IMPL_FLT_NAME = "serviceImpl.ftl";

    @Override
    protected boolean doInternal(ApplicationContext context) {
//        log.info("serviceImpl task start");
        CodeConfig config = (CodeConfig) context.getAttribute("config");
        List<EntityClass> entitys = (List<EntityClass>) context.getAttribute("entitys");
        entitys.forEach(entityClass -> {
            exec(config, entityClass);
        });
//        log.info("serviceImpl task end");
        return true;
    }

    private void exec(CodeConfig config, EntityClass entityClass) {
        String type = config.getType().toString().toLowerCase();

        String serviceName = entityClass.getTableAlias() + config.getServiceSuffix();
        String mapperName = entityClass.getTableAlias() + config.getMapperSuffix();

        String implName = entityClass.getTableAlias() + config.getServiceImplSuffix();

        Map<String, Object> data = new HashMap<>();
        data.put("package", entityClass.getBasePackage() + "." + config.getChildPackageImpl());
        data.put("entityPackage", entityClass.getAllPackage() + "." + entityClass.getClassName());
        data.put("type", type);
        data.put("servicePackage", entityClass.getBasePackage() + "." + config.getChildPackageService() + "." + serviceName);
        data.put("tableName", entityClass.getTableName());
        data.put("author", System.getProperty("user.name"));
        data.put("time", time);
        data.put("iocName", ConvertUtils.lowerFirst(serviceName));
        data.put("implName", implName);
        data.put("entityName", entityClass.getClassName());
        data.put("serviceName", serviceName);
        if (config.getType().equals(CodeConfig.TypeEnum.MAPPER)) {
            IMPL_FLT_NAME = "serviceXmlImpl.ftl";
            data.put("mapperPackage", entityClass.getBasePackage() + "." + config.getChildPackageMapper() + "." + mapperName);
            data.put("mapperName", mapperName);
        }
        else if (config.getType().equals(CodeConfig.TypeEnum.MYBATIS_PLUS)) {
            IMPL_FLT_NAME = "mybatisPlusServiceImpl.ftl";
            data.put("mapperPackage", entityClass.getBasePackage() + "." + config.getChildPackageMapper() + "." + mapperName);
            data.put("mapperName", mapperName);
        }
        else if (config.getType().equals(CodeConfig.TypeEnum.MYBATIS_PLUS_EXT)) {
            IMPL_FLT_NAME = "mybatisPlusServiceImpl-Ext.ftl";
            data.put("mapperPackage", entityClass.getBasePackage() + "." + config.getChildPackageMapper() + "." + mapperName);
            data.put("mapperName", mapperName);
        }

        File file = new File(
                config.getProjectPath() +
                        config.getSaveBasePath() +
                        config.getPackagePathImpl() + implName + ".java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            freemarker.template.Configuration cfg = getConfiguration();
            cfg.getTemplate(IMPL_FLT_NAME).process(data,
                    new FileWriter(file));
        } catch (Exception e) {
            log.error("{}", e);
            throw new ServiceException(-1, "XML文件生成报错");
        }
        log.info("{}.java 生成成功", implName);
    }
}