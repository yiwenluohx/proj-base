package com.study.base.core.redis.config;

import com.study.base.core.redis.utils.CacheUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;

/**
 * ClassName: RedisAutoConfiguration
 * Description:
 * @Author: luohx
 * Date: 2022/2/22 下午3:07
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
@Configuration
@ConditionalOnClass({RedisOperations.class})
@EnableConfigurationProperties({RedisProperties.class})
public class RedisAutoConfiguration {
    public RedisAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean({CacheUtils.class})
    public CacheUtils CacheUtils() {
        return new CacheUtils();
    }
}