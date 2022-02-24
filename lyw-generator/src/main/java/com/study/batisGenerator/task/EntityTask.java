package com.study.batisGenerator.task;

import com.google.common.collect.Lists;
import com.study.core.exception.ServiceException;
import com.study.batisGenerator.core.AbstractTask;
import com.study.batisGenerator.core.ApplicationContext;
import com.study.batisGenerator.entity.CodeConfig;
import com.study.batisGenerator.entity.Column;
import com.study.batisGenerator.entity.EntityClass;
import com.study.batisGenerator.entity.Table;
import com.study.batisGenerator.handler.EntityHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: EntityTask
 * Description:
 * @Author: luohx
 * Date: 2022/2/23 上午10:25
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
@Slf4j
public class EntityTask extends AbstractTask {

    private static String IMPL_FLT_NAME = "entity.ftl";

    @Override
    protected boolean doInternal(ApplicationContext context) {
//        log.info("entity task start");
        CodeConfig config = (CodeConfig) context.getAttribute("config");
        List<Table> tables = (List<Table>) context.getAttribute("tables");
        List<EntityClass> entitys = Lists.newArrayList();
        tables.forEach(x -> {
            List<Column> collect = x.getColumns().stream().filter(y -> y.isPrimaryKey()).collect(Collectors.toList());
            log.info("{}表中主键为：{}", x.getTableName(), x.getPkName());
            if (collect.size() == 0)
                throw new ServiceException(String.format("%s表未设置主键", x.getTableName()));
            if (collect.size() > 1) {
                throw new ServiceException(String.format("%s表有多个主键", x.getTableName()));
            }
            EntityClass entityClass = EntityHandler.combineInfo(x, config.getBasePackage(), config.getEntitySuffix());
            entityClass.setAllPackage(entityClass.getBasePackage() + "." + config.getChildPackagePo());
            entitys.add(entityClass);
            exec(config, x, entityClass);
        });
        context.setAttribute("entitys", entitys);
//        log.info("entity task end");
        return true;
    }

    private void exec(CodeConfig config, Table x, EntityClass entityClass) {
        Map<String, Object> data = new HashMap<>();
        data.put("package", entityClass.getAllPackage());
        data.put("tableName", x.getTableName());
        data.put("className", entityClass.getClassName());
        data.put("tableAlias", entityClass.getTableAlias());
        data.put("author", System.getProperty("user.name"));
        data.put("time", time);

        data.put("import", EntityHandler.fillImport(entityClass.getImports()));
        data.put("properties", EntityHandler.fillField(entityClass.getFields()));
        data.put("fields", entityClass.getFields());
        if (config.getType().equals(CodeConfig.TypeEnum.MYBATIS_PLUS) || config.getType().equals(CodeConfig.TypeEnum.MYBATIS_PLUS_EXT)) {
            IMPL_FLT_NAME = "mybatisPlusEntity.ftl";
        }

        File file = new File(
                config.getProjectPath() +
                        config.getSaveBasePath() +
                        config.getPackagePathPo() +
                        entityClass.getClassName() + ".java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            freemarker.template.Configuration cfg = getConfiguration();
            cfg.getTemplate(IMPL_FLT_NAME).process(data,
                    new FileWriter(file));
        } catch (Exception e) {
            log.error("{}", e);
            throw new ServiceException("实体类生成报错");
        }
        log.info("{}.java 生成成功", entityClass.getClassName());
    }

}
