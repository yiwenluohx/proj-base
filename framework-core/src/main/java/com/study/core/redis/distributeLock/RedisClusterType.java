package com.study.core.redis.distributeLock;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2023/7/7 下午4:43
 * @menu
 */
public enum RedisClusterType {
    SINGLE,
    MASTERSLAVE,
    SENTINEL,
    CLUSTER,
    REPLICATE;
}
