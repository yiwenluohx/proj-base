package com.study.batisGenerator.task;

import com.study.core.exception.ServiceException;
import com.study.batisGenerator.core.AbstractTask;
import com.study.batisGenerator.core.ApplicationContext;
import com.study.batisGenerator.entity.CodeConfig;
import com.study.batisGenerator.entity.EntityClass;
import com.study.batisGenerator.entity.XmlClass;
import com.study.batisGenerator.handler.XmlHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: XmlTask
 * Description: xml文件
 * @Author: luohx
 * Date: 2022/2/23 上午10:47
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0              xml文件
 */
@Slf4j
public class XmlTask extends AbstractTask {

    private static String IMPL_FLT_NAME = "xml.ftl";

    @Override
    protected boolean doInternal(ApplicationContext context) {
//        log.info("xml task start");
        CodeConfig config = (CodeConfig) context.getAttribute("config");
        List<EntityClass> entitys = (List<EntityClass>) context.getAttribute("entitys");
        entitys.forEach(entityClass -> {
            String xmlNameSpace = "";
            //此处路径为po或者mapper
            if(config.getType().equals(CodeConfig.TypeEnum.PO)) {
                xmlNameSpace = entityClass.getAllPackage() + "." +entityClass.getClassName();
            } else {
                String mapperName = entityClass.getTableAlias() + config.getMapperSuffix();
                xmlNameSpace = entityClass.getBasePackage() + "." + config.getChildPackageMapper() + "." + mapperName;
            }
            XmlClass xmlClass = XmlHandler.combineInfo(entityClass,xmlNameSpace);

            exec(config,entityClass, xmlClass);
        });
//        log.info("xml task end");
        return true;
    }

    private void exec(CodeConfig config,EntityClass entityClass, XmlClass xmlClass) {
        String xmlName = entityClass.getTableAlias() + config.getMapperSuffix();

        Map<String, Object> data = new HashMap<>();
        data.put("xmlNameSpace", xmlClass.getXmlNameSpace());
        data.put("entityPath", xmlClass.getEntityPath());
        data.put("tableName", xmlClass.getTableName());
        data.put("pkName", xmlClass.getPkName());
        data.put("fields", xmlClass.getFields());
        if (config.getType().equals(CodeConfig.TypeEnum.MYBATIS_PLUS) || config.getType().equals(CodeConfig.TypeEnum.MYBATIS_PLUS_EXT)) {
            IMPL_FLT_NAME = "mybatisPlusXml.ftl";
        }

        File file = new File(
                config.getProjectPath() +
                        config.getSaveBasePath() +
                        config.getPackagePathXml() + xmlName + ".xml");
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
        log.info("{}.xml 生成成功",xmlName);
    }
}