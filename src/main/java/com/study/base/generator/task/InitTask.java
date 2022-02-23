package com.study.base.generator.task;


import com.google.common.collect.Lists;
import com.study.base.core.exception.ServiceException;
import com.study.base.generator.core.AbstractTask;
import com.study.base.generator.core.ApplicationContext;
import com.study.base.generator.entity.CodeConfig;
import com.study.base.generator.entity.Column;
import com.study.base.generator.entity.Table;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: InitTask
 * Description: 初始化
 * @Author: luohx
 * Date: 2022/2/23 上午10:41
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0              初始化
 */
@Slf4j
public class InitTask extends AbstractTask {

    private DataSource dataSource;
    private CodeConfig config;

    @Override
    protected boolean doInternal(ApplicationContext context) {
//        log.info("init task start");
        dataSource = (DataSource)context.getAttribute("dataSource");
        config = (CodeConfig)context.getAttribute("config");
        try {
            val map = this.getTablesMap(dataSource);
            List<Table> tables = Lists.newArrayList();
            //是否所有表
            if(config.getTableNames().size()>0){
                config.getTableNames().forEach(x->{
                    Table table = map.get(x.toLowerCase());
                    if(null != table){
                        tables.add(table);
                    }
                });
            }else {
                map.forEach((k,v)->{
                    tables.add(v);
                });
            }
            context.setAttribute("tables",tables);
        } catch (SQLException e) {
            throw new ServiceException("获取表信息失败");
        }
//        log.info("init task end");
        return true;
    }

    private Map<String, Table> getTablesMap(DataSource dataSource) throws SQLException {
        Connection conn = dataSource.getConnection();
        DatabaseMetaData dbMetaData = conn.getMetaData();

        // 获取表的结果集
        ResultSet tableRs = dbMetaData.getTables(conn.getCatalog(), null, "%", new String[]{"TABLE"});
        Map<String, Table> tables = new HashMap<String, Table>();
        while (tableRs.next()) {
            String tableName = tableRs.getString("TABLE_NAME").toLowerCase();
            String tableRemark = tableRs.getString("REMARKS");
            String tableType = tableRs.getString("TABLE_TYPE");
            String catalog = tableRs.getString("TABLE_CAT");
            Table table = new Table()
                    .setCatalog(catalog)
                    .setTableName(tableName)
                    .setType(tableType)
                    .setRemark(tableRemark);
            getPkByTable(dbMetaData, table);
            getColumnsByTable(dbMetaData, table);
            tables.put(tableName,table);
        }
        conn.close();
        return tables;
    }

    /**
     * 设置表主键，多主键逗号分隔
     *
     * @param dbMetaData
     * @param table
     * @throws SQLException
     */
    private void getPkByTable(DatabaseMetaData dbMetaData, Table table) throws SQLException {
        StringBuilder sb = new StringBuilder();
        // 获取主键
        ResultSet rs = dbMetaData.getPrimaryKeys(table.getCatalog(), null, table.getTableName());
        while (rs.next()) {
            if (sb.length() > 0)
                sb.append(",");
            sb.append(rs.getString("COLUMN_NAME").toLowerCase());// 列名
        }
        rs.close();
        table.setPkName(sb.toString());
    }

    private void getColumnsByTable(DatabaseMetaData dbMetaData, Table table) throws SQLException {
        List<Column> columns = Lists.newArrayList();
        // 获取列
        ResultSet rs = dbMetaData.getColumns(null, "%", table.getTableName(), "%");
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME").toLowerCase();
            if (StringUtils.isNotBlank(columnName)) {
                String columnType = rs.getString("TYPE_NAME").toLowerCase().replace("unsigned","").trim();
                String remarks = rs.getString("REMARKS");
                int length = rs.getInt("COLUMN_SIZE");
                // 字段类型精度
                int precision = rs.getInt("DECIMAL_DIGITS");

                Column column = new Column();
                column.setColumnName(columnName)
                        .setColumnType(columnType)
                        .setRemark(remarks)
                        .setLength(length)
                        .setPrecision(precision)
                        .setPrimaryKey(("," + table.getPkName() + ",").contains("," + columnName + ","));
                columns.add(column);
            }
        }
        rs.close();
        table.setColumns(columns);
    }
}
