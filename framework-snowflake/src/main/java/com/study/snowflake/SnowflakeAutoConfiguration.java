package com.study.snowflake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: SnowflakeAutoConfiguration
 * Description:
 * @Author: luohx
 * Date: 2022/3/1 下午4:00
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
@Configuration
public class SnowflakeAutoConfiguration {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Value("${server.port}")
    private Integer port;

    private String defaultGroup = "DefaultGroup";

    @Bean
    public SnowflakeGenerator snowflakeGenerator() throws InterruptedException, UnknownHostException {
        long workerId = getWorkerId();
        recordWorkerId(workerId);
        return new SnowflakeGenerator(workerId);
    }

    private long getWorkerId() throws InterruptedException {
        String querySql = SnowflakeWorker.QUERY_SQL;
        Map<String, Object> map = jdbcTemplate.queryForMap(querySql, new Object[] {defaultGroup});
        long nextWorkerId = ((Integer) map.get(SnowflakeWorker.WORKER_ID_FIELD) + 1) & SnowflakeGenerator.MAX_WORKER_ID;

        Long version = (Long) map.get(SnowflakeWorker.DATA_VERSION_FIELD);
        String updateSql = SnowflakeWorker.UPDATE_SQL;
        int result = jdbcTemplate.update(updateSql, new Object[] {nextWorkerId, (version + 1L), defaultGroup, version});

        if (result > 0) {
            return nextWorkerId;
        }
        TimeUnit.SECONDS.sleep(1L);
        return getWorkerId();
    }

    private void recordWorkerId(long workerId) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ip = inetAddress.getHostAddress();
        String hostName = inetAddress.getHostName();
        String inserSql = "INSERT INTO `snowflake_worker_record` (`worker_id`, `host_address`, `host_name`, `server_port`) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(inserSql, new Object[] {workerId, ip, hostName, port});
    }

}