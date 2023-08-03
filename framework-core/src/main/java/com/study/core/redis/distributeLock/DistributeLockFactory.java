package com.study.core.redis.distributeLock;

import java.util.concurrent.locks.Lock;

/**
 * 分布式锁工厂
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/7/7 下午4:09
 * @menu 分布式锁工厂
 */
public interface DistributeLockFactory {

    /**
     * 根据key获取分布式锁
     *
     * @param key
     * @return
     */
    Lock getLock(String key);
}
