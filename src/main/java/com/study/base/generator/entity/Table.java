package com.study.base.generator.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * ClassName: Table
 * Description:
 * @Author: luohx
 * Date: 2022/2/22 下午5:54
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
@Data
@Accessors(chain=true)
public class Table
{
    /**
     * 所属数据库
     */
    private String catalog;
    /**
     * 名称
     */
    private String tableName;
    /**
     * 类型
     */
    private String type;
    /**
     * 备注
     */
    private String remark;
    /**
     * 主键列名称
     */
    private String pkName;
    /**
     * 列集合
     */
    private List<Column> columns;

    @Override
    public String toString()
    {
        return JSON.toJSONString(this);
    }
}
