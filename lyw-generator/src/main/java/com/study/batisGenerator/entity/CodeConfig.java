package com.study.batisGenerator.entity;

import com.google.common.collect.Sets;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * ClassName: CodeConfig
 * Description: 代码生成配置类
 *
 * @Author: luohx
 * Date: 2022/2/22 下午6:03
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           代码生成配置类
 */
@Data
@Accessors(chain=true)
public class CodeConfig {
    /**
     * po OR mapper
     */
    private TypeEnum type = TypeEnum.MAPPER;
    /**
     * tableName,多个表的话 逗号分隔
     */
    private Set<String> tableNames = Sets.newHashSet();
    /**
     * 生成代码的主包
     */
    private String basePackage = "com.xxx.example";
    /**
     * 项目在硬盘上的基础路径
     * 建议传递:System.getProperty("user.dir")
     */
    private String projectPath = System.getProperty("user.dir");

    /**
     * 实体类后缀
     */
    private String entitySuffix = "";
    /**
     * Mapper\xml后缀
     */
    private String mapperSuffix = "Mapper";
    /**
     * service后缀
     */
    private String serviceSuffix = "Service";
    /**
     * serviceImpl后缀
     */
    private String serviceImplSuffix = "ServiceImpl";

    /**
     * 生成文件存放父路径
     */
    private String saveBasePath = "/src/test/java";

    /**
     * 生成的po存放路径
     */
    private String packagePathPo = "/generator/entity/";
    /**
     * 生成的po存放路径
     */
    private String packagePathXml = "/generator/xml/";
    /**
     * 生成的service存放路径
     */
    private String packagePathService = "/generator/service/api/";
    /**
     * 生成的serviceImpl存放路径
     */
    private String packagePathImpl = "/generator/service/impl/";
    /**
     * 生成的mapper存放路径
     */
    private String packagePathMapper = "/generator/repository/";

    /**
     * entity package
     * eg：basePackage + child_package_po
     *    com.xxx.example.entity.po
     */
    private String childPackagePo = "entity";
    /**
     * service 接口 package
     */
    private String childPackageService = "service.api";
    /**
     * service 实现类 package
     */
    private String childPackageImpl = "service.impl";
    /**
     * mapper package
     */
    private String childPackageMapper = "repository";

    /**
     * po OR mapper
     */
    public enum TypeEnum{
        PO,
        MAPPER,
        MYBATIS_PLUS,
        MYBATIS_PLUS_EXT
    }
}
