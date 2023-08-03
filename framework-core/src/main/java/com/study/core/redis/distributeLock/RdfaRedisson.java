package com.study.core.redis.distributeLock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2023/7/20 上午11:00
 * @menu
 */
public class RdfaRedisson extends Redisson {
    protected RdfaRedisson(Config config) {
        super(config);
    }

    public static RedissonClient create(Config config) {
        RdfaRedisson redisson = new RdfaRedisson(config);
        if (config.isReferenceEnabled()) {
            redisson.enableRedissonReferenceSupport();
        }
        return redisson;
    }

    @Override
    public RLock getLock(String name) {
        return new RdfaRedissonLock(connectionManager.getCommandExecutor(), name);
    }
}
