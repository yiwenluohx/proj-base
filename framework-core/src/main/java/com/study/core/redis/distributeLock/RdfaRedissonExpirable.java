package com.study.core.redis.distributeLock;

import org.redisson.RedissonObject;
import org.redisson.api.RExpirable;
import org.redisson.api.RFuture;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.client.protocol.RedisCommands;
import org.redisson.command.CommandAsyncExecutor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2023/7/20 上午11:02
 * @menu
 */
public class RdfaRedissonExpirable extends RedissonObject implements RExpirable {

    RdfaRedissonExpirable(CommandAsyncExecutor connectionManager, String name) {
        super(connectionManager, name);
    }

    RdfaRedissonExpirable(Codec codec, CommandAsyncExecutor connectionManager, String name) {
        super(codec, connectionManager, name);
    }

    @Override
    public boolean expire(long timeToLive, TimeUnit timeUnit) {
        return commandExecutor.get(expireAsync(timeToLive, timeUnit));
    }

    @Override
    public RFuture<Boolean> expireAsync(long timeToLive, TimeUnit timeUnit) {
        return commandExecutor.writeAsync(getName(), StringCodec.INSTANCE, RedisCommands.PEXPIRE, getName(), timeUnit.toMillis(timeToLive));
    }

    @Override
    public boolean expireAt(long timestamp) {
        return commandExecutor.get(expireAtAsync(timestamp));
    }

    @Override
    public RFuture<Boolean> expireAtAsync(long timestamp) {
        return commandExecutor.writeAsync(getName(), StringCodec.INSTANCE, RedisCommands.PEXPIREAT, getName(), timestamp);
    }

    @Override
    public boolean expireAt(Date timestamp) {
        return expireAt(timestamp.getTime());
    }

    @Override
    public RFuture<Boolean> expireAtAsync(Date timestamp) {
        return expireAtAsync(timestamp.getTime());
    }

    @Override
    public boolean clearExpire() {
        return commandExecutor.get(clearExpireAsync());
    }

    @Override
    public RFuture<Boolean> clearExpireAsync() {
        return commandExecutor.writeAsync(getName(), StringCodec.INSTANCE, RedisCommands.PERSIST, getName());
    }

    @Override
    public long remainTimeToLive() {
        return commandExecutor.get(remainTimeToLiveAsync());
    }

    @Override
    public RFuture<Long> remainTimeToLiveAsync() {
        return commandExecutor.readAsync(getName(), StringCodec.INSTANCE, RedisCommands.PTTL, getName());
    }
}
