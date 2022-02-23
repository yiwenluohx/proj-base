package com.study.base.generator.handler;

import com.study.base.generator.entity.EntityClass;
import com.study.base.generator.entity.XmlClass;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: XmlHandler
 * Description:  xml文件生产
 * @Author: luohx
 * Date: 2022/2/23 上午9:37
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           xml文件生产
 */
@Slf4j
public class XmlHandler {

    /**
     * 组装xml信息
     * @param entityClass
     * @param nameSpace xml namespace
     * @return
     */
    public static XmlClass combineInfo(EntityClass entityClass, String nameSpace)
    {
        XmlClass xmlClass = new XmlClass();
        xmlClass.setXmlNameSpace(nameSpace);
        xmlClass.setEntityPath(entityClass.getAllPackage() + "." + entityClass.getClassName());
        xmlClass.setTableName(entityClass.getTableName());
        xmlClass.setPkName(entityClass.getPkName());
        xmlClass.setFields(entityClass.getFields());
        return xmlClass;
    }
}
