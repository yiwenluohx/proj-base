package ${package};

import com.alibaba.fastjson.JSON;
import com.study.core.po.BasePo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
${import}

/**
* ClassName: ${className}
* Description: ${tableName}
* @Author: ${author}
* Date: ${time}
* History:
*<author>    <time>    <version>        <desc>
* ${author}  ${time}     1.0       ${tableName} - ${className}
*/
@Table(name = "${tableName}")
public class ${className} extends BasePo {
    public static final String TABLE_ALIAS = "${tableAlias}";

<#--    {mybatis.version=3.4.6, shardingsphere.version=3.0.0, netty4.version=4.1.25.Final, rocket-mq.version=4.7.1, fastjson.version=1.2.76, commons-codec.version=1.11, rdfa.framework.version=1.7.3-RELEASE, oss.minio.version=8.2.2, spring-boot-starter-amqp.version=2.3.1.RELEASE, testable.version=0.6.7, gson.version=2.8.5, httpclient.version=4.5.3, project.build.sourceEncoding=UTF-8, maven-checkstyle-plugin.version=3.0.0, swagger2.version=2.7.0, lombok.version=1.18.4, logback.version=1.2.3, javax-validation.version=2.0.1.Final, nacos.version=1.3.3, jsqlparser.version=1.2, ffmpeg-platform.version=4.3.2-1.5.5, mockito.version=2.23.4, rdfa.timer.client.version=1.0.0, maven-deploy-plugin.version=2.8.2, xstream.version=1.4.10, maven-install-plugin.version=2.5.2, powermock.version=2.0.0, log4j2.version=2.17.0, java.version=1.8, redisson.version=3.7.3, disruptor.version=3.4.2, sonar.host.url=http://10.39.34.3:9000, oss.version=2.8.3, org.mapstruct.version=1.4.2.Final, maven-shade-plugin.version=3.2.1, maven-plugin-plugin.version=3.6.0, snakeyaml.version=1.23, javacv.version=1.5.5, cn.hutool.version=5.7.3, maven-failsafe-plugin.version=2.22.1, maven-verifier-plugin.version=1.1, maven.compiler.target=8, project.reporting.outputEncoding=UTF-8, maven-jar-plugin.version=3.1.0, pagehelper.version=5.1.8, rdfa.apollo.client.version=1.7.2.RELEASE, commons-pool2.version=2.6.0, nimbus-jose-jwt.version=8.19, spring-cloud.version=Hoxton.SR8, cglib.version=3.3.0, maven-surefire-plugin.version=3.0.0-M5, orika.version=1.5.2, poi-ooxml.version=4.1.2, netty3.version=3.2.5.Final, maven-source-plugin.version=3.0.1, logback-kafka-append.version=0.1.0, dubbo-api-doc.version=2.7.8.3, maven-compiler-plugin.version=3.8.0, maven-plugin-api.version=3.5.0, guava.version=18.0, aspectj.version=1.9.2, mysql.version=8.0.26, maven-dependency-plugin.version=3.1.1, maven-plugin-annotations.version=3.5, hutool.version=5.4.6, pagehelper.springboot.version=1.2.10, httpcore.version=4.4.6, maven-resources-plugin.version=3.1.0, jedis.version=3.1.0, dubbo.version=2.7.13, slf4j.version=1.7.25, seata.springboot.version=1.5.1-release, lettuce.version=5.1.3.RELEASE, commons-pool.version=1.6, ons.version=1.7.9.Final, logstash-logback-encoder.version=5.2, commons-io.version=2.6, maven.compiler.source=8, spring-boot.version=2.3.2.RELEASE, transmittable-thread-local.version=2.12.0, oss.qingstor.version=2.5.1, easyexcel.version=1.1.1, paho.mqttv3.version=1.2.5, servlet-api.version=4.0.1, maven-pmd-plugin.version=3.11.0, commons-dbcp2.version=2.5.0, dubbo.springboot.version=0.2.0, okhttp.version=3.14.2, javax.el.version=3.0.0, javassist.version=3.20.0-GA, java-jwt.version=3.11.0, hibernate-validator.version=6.0.13.Final, hikaricp.version=3.2.0, commons-lang3.version=3.8.1, jackson.version=2.11.1, file.encoding=UTF-8, apollo.client.version=1.7.0, curator.version=2.12.0, spring-cloud-alibaba.version=2.2.6.RELEASE, spring-kafka.version=2.7.3, asm.version=8.0.1, zookeeper.version=3.4.13, druid.version=1.1.16, tomcat.version=9.0.37, junit.version=4.12, maven-clean-plugin.version=3.1.0}-->
<#list fields as field>
    /**
    * ${field.fieldRemark}
    * Code generator automatic generation
    */
    <#if field.primaryKey?string('yes','no') == 'yes'>
        @Id
    </#if>
    @Column(name = "${field.columnName}")
    private ${field.fieldType} ${field.fieldName};
</#list>

<#list fields as field>
    /**
    * Code generator automatic generation
    */
    public ${field.fieldType} get${field.fieldName1}() {
       return ${field.fieldName};
    }

    /**
    * Code generator automatic generation
    */
    public void set${field.fieldName1}(${field.fieldType} ${field.fieldName}) {
       this.${field.fieldName} = ${field.fieldName};
    }
</#list>

    /**
    * Code generator automatic generation
    */
    @Override
    public String toString()
    {
       return JSON.toJSONString(this);
    }

    /**
    * Code generator automatic generation
    */
    @Override
    public String toLog() {
       return JSON.toJSONString(this);
    }
}