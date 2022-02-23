package com.study.base.generator.handler;

import com.google.common.collect.Lists;
import com.study.base.generator.entity.EntityClass;
import com.study.base.generator.entity.Table;
import com.study.base.generator.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * ClassName: EntityHandler
 * Description:  实体类处理器
 * @Author: luohx
 * Date: 2022/2/23 上午9:35
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0            实体类处理器
 */
@Slf4j
public class EntityHandler {

    /**
     * 根据column信息填充entity信息
     * @param table
     * @param basePackage
     * @param suffix
     * @return
     */
    public static EntityClass combineInfo(Table table, String basePackage, String suffix){

        EntityClass entityClass = new EntityClass();
        entityClass.setTableName(table.getTableName());
        entityClass.setTableAlias(ConvertUtils.convertTableName2EntityName(table.getTableName()));
        entityClass.setClassName(entityClass.getTableAlias() + suffix);
        entityClass.setPkName(ConvertUtils.convertColumnName2PropName(table.getPkName()));
        entityClass.setBasePackage(basePackage);

        Set<String> imports = new HashSet<>();
        List<EntityClass.Field> fields = Lists.newArrayList();

        val columns = table.getColumns();
        columns.forEach(x->{
            EntityClass.Field field = new EntityClass.Field();

            String columnName = x.getColumnName();
            String columnType = x.getColumnType();

            //生成属性名
            String propName = ConvertUtils.convertColumnName2PropName(columnName);

            field.setColumnName(columnName);
            field.setFieldRemark(x.getRemark());
            field.setPrimaryKey(x.isPrimaryKey());
            field.setFieldName(propName);
            field.setFieldName1(ConvertUtils.convertTableName2EntityName(field.getFieldName()));

            //jdbcType -> javaType ->package
            Map<String,String> map = ConvertUtils.javaTypeMap(columnType);
            map.forEach((k, v) -> {
                field.setFieldType(k);
                if(StringUtils.isNotBlank(v)){
                    imports.add(v);
                }
            });
            fields.add(field);
        });
        entityClass.setImports(imports);
        entityClass.setFields(fields);
        return entityClass;
    }

    /**
     * 转import换行
     * @param imports
     * @return
     */
    public static String fillImport(Set<String> imports){
        StringBuilder sb = new StringBuilder();
        for(String str : imports)
        {
            sb.append("import ").append(str).append(";\r\n");
        }
        return sb.toString();
    }

    /**
     * 填充属性字段
     * @param fields
     * @return
     */
    public static String fillField(List<EntityClass.Field> fields){
        StringBuilder sb = new StringBuilder();
        fields.forEach(x->{
            if(sb.length()>0){
                sb.append("\n    ");
            }
            sb.append("/**").append("\n    ");
            sb.append("* ").append(x.getFieldRemark()).append("\n    ");
            sb.append("*/").append("\n    ");
            if(x.isPrimaryKey()){
                sb.append("@Id\n    ");
            }
            sb.append("@Column(name = \"").append(x.getColumnName()).append("\")").append("\n    ");
            sb.append("private").append(" ").append(x.getFieldType()).append(" ").append(x.getFieldName()).append(";");
        });
        return sb.toString();
    }
}
