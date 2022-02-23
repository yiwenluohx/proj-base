package com.study.base.generator.task;

import com.study.base.core.exception.ServiceException;
import com.study.base.generator.core.AbstractTask;
import com.study.base.generator.core.ApplicationContext;
import com.study.base.generator.entity.CodeConfig;
import com.study.base.generator.entity.EntityClass;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MapperTask
 * Description:
 * @Author: luohx
 * Date: 2022/2/23 上午10:42
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
@Slf4j
public class MapperTask extends AbstractTask {

    private static String IMPL_FLT_NAME = "mapper.ftl";

    @Override
    protected boolean doInternal(ApplicationContext context) {
        CodeConfig config = (CodeConfig) context.getAttribute("config");
        if (config.getType().equals(CodeConfig.TypeEnum.PO)) {
            return true;
        }
//        log.info("mapper task start");
        List<EntityClass> entitys = (List<EntityClass>) context.getAttribute("entitys");
        entitys.forEach(entityClass -> {
            exec(config, entityClass);
        });
//        log.info("mapper task end");
        return true;
    }

    private void exec(CodeConfig config, EntityClass entityClass) {
        String mapperName = entityClass.getTableAlias() + config.getMapperSuffix();

        Map<String, Object> data = new HashMap<>();
        data.put("package", entityClass.getBasePackage() + "." + config.getChildPackageMapper());
        data.put("entityPackage", entityClass.getAllPackage() + "." + entityClass.getClassName());
        data.put("tableName", entityClass.getTableName());
        data.put("author", System.getProperty("user.name"));
        data.put("time", time);
        data.put("mapperName", mapperName);
        data.put("entityName", entityClass.getClassName());
        if (config.getType().equals(CodeConfig.TypeEnum.MYBATIS_PLUS)) {
            IMPL_FLT_NAME = "mybatisPlusMapper.ftl";
        } else if (config.getType().equals(CodeConfig.TypeEnum.MYBATIS_PLUS_EXT)) {
            IMPL_FLT_NAME = "mybatisPlusMapper-Ext.ftl";
        }

        File file = new File(
                config.getProjectPath() +
                        config.getSaveBasePath() +
                        config.getPackagePathMapper() + mapperName + ".java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            freemarker.template.Configuration cfg = getConfiguration();
            cfg.getTemplate(IMPL_FLT_NAME).process(data,
                    new FileWriter(file));
        } catch (Exception e) {
            log.error("{}", e);
            throw new ServiceException("XML文件生成报错");
        }
        log.info("{}.java 生成成功", mapperName);
    }
}