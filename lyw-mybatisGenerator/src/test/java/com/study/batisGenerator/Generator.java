package com.study.batisGenerator;

import com.google.common.collect.Sets;
import com.study.batisGenerator.entity.CodeConfig;
import com.study.batisGenerator.task.XmlTask;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * ClassName: Generator
 * Description:
 * Author: luohx
 * Date: 2022/2/24 上午10:33
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public class Generator {

    private static DataSource getDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/eraser?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowMultiQueries=true");
//        dataSource.setUsername("root");
//        dataSource.setPassword("passw@rd");
//        return dataSource;
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://10.39.75.92:13306/quality?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowMultiQueries=true");
//        dataSource.setUsername("quality_dev");
//        dataSource.setPassword("quality_dev");
//        return dataSource;
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://10.39.75.92:13306/goods?autoReconnect=true&useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8&allowMultiQueries=true");
        dataSource.setUsername("goods_dev");
        dataSource.setPassword("goods_dev");
        return dataSource;
    }

    /**
     * 生成所有表
     */
    public static void startTest() {
        CodeConfig config = new CodeConfig();
        config.setBasePackage("com.study.test")
                .setProjectPath(System.getProperty("user.dir") + "/proj-base/lyw-mybatisGenerator")
                .setType(CodeConfig.TypeEnum.MAPPER);
        new CodeGenerator(getDataSource(), config)
                .start();
    }

    /**
     * 生成entity和xml文件
     */
    public static void xmlTest() {
        CodeConfig config = new CodeConfig();
        config.setBasePackage("cn.ygyg.quality.trace.core.domain")
                .setTableNames(
                        Sets.newHashSet("digital_template_contents")
                )
                .setProjectPath(System.getProperty("user.dir") + "/ygyg-quality-trace/ygyg-quality-trace-mybatisGenerator")
                .setType(CodeConfig.TypeEnum.MAPPER);
        new CodeGenerator(getDataSource(), config)
                .registerTask(XmlTask.class)
                .start();
    }

    /**
     * 生成指定的表
     */
    public static void tableTest() {
        CodeConfig config = new CodeConfig();
        config.setBasePackage("com.study.test")
                .setTableNames(
                        Sets.newHashSet(
                                "goods_sell_spec"
                        )
                )
                .setProjectPath(System.getProperty("user.dir") + "/proj-base/lyw-mybatisGenerator")
                .setType(CodeConfig.TypeEnum.MAPPER);
        new CodeGenerator(getDataSource(), config)
                .start();
    }

    public static void main(String[] args) {
        //生成所有表
        startTest();

        //生成entity和xml文件
//        xmlTest();
//        生成指定的表
//        tableTest();
    }
}