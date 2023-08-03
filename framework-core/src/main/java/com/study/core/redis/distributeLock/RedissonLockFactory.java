package com.study.core.redis.distributeLock;

import io.netty.channel.nio.NioEventLoopGroup;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import static java.util.stream.Collectors.toList;

/**
 * 分布式锁工厂
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/7/7 下午4:13
 * @menu 分布式锁工厂
 */
public class RedissonLockFactory implements DistributeLockFactory {
    private static Logger logger = LoggerFactory.getLogger(RedissonLockFactory.class);

    private final String lockPath = ":lock:";

    private RedissonConfig redissonConfig;

    private List<RedissonClient> redissionClientList;

    private String lockFullPath;

    public RedissonLockFactory(RedissonConfig redissonConfig,String appId){
        this.redissonConfig = redissonConfig;
        this.redissionClientList  = redissons();
        this.lockFullPath =appId+lockPath;
    }

    private List<RedissonClient> redissons(){
        List<RedissonClient> clients = new ArrayList<>();

        if(RedisClusterType.SINGLE.equals(redissonConfig.getType())){
            clients.add(RdfaRedisson.create(configSingleNode(redissonConfig.getAddress(),redissonConfig.getPassword())));
        }
        else if(RedisClusterType.REPLICATE.equals(redissonConfig.getType())){
            String[] nodes = redissonConfig.getAddress().split(",");
            if(redissonConfig.getPassword()!=null){
                String[] passwords = redissonConfig.getPassword().split(",");
                int index = 0;
                for(String node : nodes){
                    clients.add(RdfaRedisson.create(configSingleNode(node, passwords[index])));
                    index++;
                }
            }else{
                for(String node : nodes){
                    clients.add(RdfaRedisson.create(configSingleNode(node, null)));
                }
            }
        }
        return clients;
    }

    private Config configSingleNode(String address, String password) {
        Config config = new Config();
        config.useSingleServer().setAddress(address)
                .setConnectionMinimumIdleSize(redissonConfig.getConnectionMinimumIdleSize())
                .setConnectionPoolSize(redissonConfig.getConnectionPoolSize())
                .setDatabase(redissonConfig.getDatabase())
//                .setDnsMonitoring(dnsMonitoring)
                .setDnsMonitoringInterval(redissonConfig.getDnsMonitoringInterval())
                .setSubscriptionConnectionMinimumIdleSize(redissonConfig.getSubscriptionConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(redissonConfig.getConnectionPoolSize())
                .setSubscriptionsPerConnection(redissonConfig.getSubscriptionsPerConnection())
                .setClientName(redissonConfig.getClientName())
//                .setFailedAttempts(failedAttempts)
                .setRetryAttempts(redissonConfig.getRetryAttempts())
                .setRetryInterval(redissonConfig.getRetryInterval())
//                .setReconnectionTimeout(reconnectionTimeout)
                .setTimeout(redissonConfig.getTimeout())
                .setConnectTimeout(redissonConfig.getConnectTimeout())
                .setIdleConnectionTimeout(redissonConfig.getIdleConnectionTimeout())
                .setPingTimeout(redissonConfig.getPingTimeout())
                .setPassword(password);
        Codec codec = getCodecInstance();
        config.setCodec(codec);
        config.setThreads(redissonConfig.getThread());
        config.setEventLoopGroup(new NioEventLoopGroup());
        config.setTransportMode(TransportMode.NIO);
        try {
            logger.info("inti the redisson client with config: {}", config.toJSON());
        }
        catch (IOException ex){
            logger.error("parse json error:{}",ex);
        }
        return config;
    }

    private Codec getCodecInstance(){
        try {
            Codec  codec = (Codec) ClassUtils.forName(redissonConfig.getCodec(), ClassUtils.getDefaultClassLoader()).newInstance();
            return codec;
        }
        catch (ClassNotFoundException ex){
            logger.error("codec class not found : {}",redissonConfig.getCodec());
            throw new RuntimeException(ex);
        }
        catch (IllegalAccessException ex){
            logger.error("get codec error : {}",ex);
            throw new RuntimeException(ex);
        }
        catch (InstantiationException ex){
            logger.error("get codec error : {}",ex);
            throw new RuntimeException(ex);
        }
    }

    @PreDestroy
    public void destroy(){
        redissionClientList.stream().forEach(client -> {
            try {
                client.shutdown();
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        });
    }

    @Override
    public Lock getLock(String lockKey) {
        String realLockKey = lockFullPath +lockKey;
        List<RLock> rlocks = redissionClientList.stream()
                .map( client -> { return client.getLock(realLockKey);})
                .collect(toList());

        return new RdfaRedLock(realLockKey,redissonConfig.getUnlockRetry(),rlocks.toArray(new RLock[rlocks.size()]));
    }
}
