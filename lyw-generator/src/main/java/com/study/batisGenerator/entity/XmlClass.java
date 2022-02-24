package com.study.batisGenerator.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * ClassName: XmlClass
 * Description: 组装xml文件实体类
 * @Author: luohx
 * Date: 2022/2/22 下午5:59
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           组装xml文件实体类
 */
@Data
@Accessors(chain=true)
public class XmlClass {

    /**
     * xml nagespace
     */
    private String xmlNameSpace;
    /**
     * 实体类全路径
     */
    private String entityPath;
    /**
     * table名称
     */
    private String tableName;
    /**
     * 主键列名称
     */
    private String PkName;
    /**
     * 列 -> 字段对应关系
     */
    private List<EntityClass.Field> fields;

}
