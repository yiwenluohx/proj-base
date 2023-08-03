package com.study.core.redis.distributeLock;

import io.netty.channel.nio.NioEventLoopGroup;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.TransportMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * redisson配置
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/7/7 下午4:21
 * @menu redisson配置
 */
@Component
@ConfigurationProperties(prefix = "lyw.redisson")
public class RedissonConfig {

    private static Logger logger = LoggerFactory.getLogger(RedissonConfig.class);

    //format: 127.0.0.1:7000,127.0.0.1:7001;
    private String address;

    //single node properties
    private int connectionMinimumIdleSize = 10;
    private int idleConnectionTimeout=10000;
    private int pingTimeout=1000;
    private int connectTimeout=10000;
    private int timeout=3000;
    private int retryAttempts=3;
    private int retryInterval=1500;
    private int reconnectionTimeout=3000;
    private int failedAttempts=3;
    //multi nodes, password split by comma(,)
    private String password = null;
    private int subscriptionsPerConnection=5;
    private String clientName=null;
    private int subscriptionConnectionMinimumIdleSize = 1;
    private int subscriptionConnectionPoolSize = 50;
    private int connectionPoolSize = 64;
    private int database = 0;
    private boolean dnsMonitoring = false;
    private int dnsMonitoringInterval = 5000;

    //master slave properties
    private ReadMode readMode = ReadMode.MASTER;

    //cluster properties
    private int scanInterval = 1000;
    //unlock失败重试次数
    private int unlockRetry = 3;

    private RedisClusterType type = RedisClusterType.REPLICATE;

    private int thread; //当前处理核数量 * 2

    private String codec="org.redisson.codec.JsonJacksonCodec";

    public List<RedissonClient> redissons(){
        List<RedissonClient> clients = new ArrayList<>();

        if(RedisClusterType.SINGLE.equals(type)){
            clients.add(Redisson.create(configSingleNode(address, password)));
        }else if(RedisClusterType.MASTERSLAVE.equals(type)){
            //TODO 3.2 master slave not support dns configure
            clients.add(Redisson.create(configMasterSlave()));
        }else if(RedisClusterType.CLUSTER.equals(type)){
            clients.add(Redisson.create(configCluster()));
        }else if(RedisClusterType.REPLICATE.equals(type)){
            String[] nodes = address.split(",");
            if(password!=null){
                String[] passwords = password.split(",");
                int index = 0;
                for(String node : nodes){
                    clients.add(Redisson.create(configSingleNode(node, passwords[index])));
                    index++;
                }
            }else{
                for(String node : nodes){
                    clients.add(Redisson.create(configSingleNode(node, null)));
                }
            }
        }else if(RedisClusterType.SENTINEL.equals(type)){
            //TODO
        }

        return clients;
    }

    private Config configSingleNode(String address, String password) {
        Config config = new Config();
        config.useSingleServer().setAddress(address)
                .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setConnectionPoolSize(connectionPoolSize)
                .setDatabase(database)
//                .setDnsMonitoring(dnsMonitoring)
                .setDnsMonitoringInterval(dnsMonitoringInterval)
                .setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize)
                .setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize)
                .setSubscriptionsPerConnection(subscriptionsPerConnection)
                .setClientName(clientName)
//                .setFailedAttempts(failedAttempts)
                .setRetryAttempts(retryAttempts)
                .setRetryInterval(retryInterval)
//                .setReconnectionTimeout(reconnectionTimeout)
                .setTimeout(timeout)
                .setConnectTimeout(connectTimeout)
                .setIdleConnectionTimeout(idleConnectionTimeout)
                .setPingTimeout(pingTimeout)
                .setPassword(password);
        Codec codec = getCodecInstance();
        config.setCodec(codec);
        config.setThreads(thread);
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

    private Config configMasterSlave(){
        Config config = new Config();
        config.useMasterSlaveServers().setMasterAddress(address)
                .addSlaveAddress(address)
                .setMasterConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setMasterConnectionPoolSize(connectionPoolSize)
                .setSlaveConnectionMinimumIdleSize(connectionMinimumIdleSize)
                .setSlaveConnectionPoolSize(connectionPoolSize)
                .setReadMode(readMode)
                .setDatabase(database)
                .setSubscriptionsPerConnection(subscriptionsPerConnection)
                .setClientName(clientName)
                .setFailedSlaveCheckInterval(failedAttempts)
                .setRetryAttempts(retryAttempts)
                .setRetryInterval(retryInterval)
                .setFailedSlaveCheckInterval(reconnectionTimeout)
                .setTimeout(timeout)
                .setConnectTimeout(connectTimeout)
                .setIdleConnectionTimeout(idleConnectionTimeout)
                .setPingTimeout(pingTimeout)
                .setPassword(password);
        Codec codec = getCodecInstance();
        config.setCodec(codec);
        config.setThreads(thread);
        config.setEventLoopGroup(new NioEventLoopGroup());
        config.setTransportMode(TransportMode.NIO);
        return config;
    }

    private Config configCluster(){
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress(address)
                .setScanInterval(scanInterval)
                .setSubscriptionsPerConnection(subscriptionsPerConnection)
                .setClientName(clientName)
                .setFailedSlaveCheckInterval(failedAttempts)
                .setRetryAttempts(retryAttempts)
                .setRetryInterval(retryInterval)
                .setFailedSlaveCheckInterval(reconnectionTimeout)
                .setTimeout(timeout)
                .setConnectTimeout(connectTimeout)
                .setIdleConnectionTimeout(idleConnectionTimeout)
                .setPingTimeout(pingTimeout)
                .setPassword(password);
        Codec codec = getCodecInstance();
        config.setCodec(codec);
        config.setThreads(thread);
        config.setEventLoopGroup(new NioEventLoopGroup());
        config.setTransportMode(TransportMode.NIO);
        return config;
    }

    private Codec getCodecInstance(){
        try {
            Codec  codec = (Codec) ClassUtils.forName(getCodec(), ClassUtils.getDefaultClassLoader()).newInstance();
            return codec;
        }
        catch (ClassNotFoundException ex){
            logger.error("codec class not found : {}",getCodec());
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getConnectionMinimumIdleSize() {
        return connectionMinimumIdleSize;
    }

    public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
    }

    public int getIdleConnectionTimeout() {
        return idleConnectionTimeout;
    }

    public void setIdleConnectionTimeout(int idleConnectionTimeout) {
        this.idleConnectionTimeout = idleConnectionTimeout;
    }

    public int getPingTimeout() {
        return pingTimeout;
    }

    public void setPingTimeout(int pingTimeout) {
        this.pingTimeout = pingTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getRetryAttempts() {
        return retryAttempts;
    }

    public void setRetryAttempts(int retryAttempts) {
        this.retryAttempts = retryAttempts;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }

    public int getReconnectionTimeout() {
        return reconnectionTimeout;
    }

    public void setReconnectionTimeout(int reconnectionTimeout) {
        this.reconnectionTimeout = reconnectionTimeout;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSubscriptionsPerConnection() {
        return subscriptionsPerConnection;
    }

    public void setSubscriptionsPerConnection(int subscriptionsPerConnection) {
        this.subscriptionsPerConnection = subscriptionsPerConnection;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getSubscriptionConnectionMinimumIdleSize() {
        return subscriptionConnectionMinimumIdleSize;
    }

    public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
        this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
    }

    public int getSubscriptionConnectionPoolSize() {
        return subscriptionConnectionPoolSize;
    }

    public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
        this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
    }

    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public boolean isDnsMonitoring() {
        return dnsMonitoring;
    }

    public void setDnsMonitoring(boolean dnsMonitoring) {
        this.dnsMonitoring = dnsMonitoring;
    }

    public int getDnsMonitoringInterval() {
        return dnsMonitoringInterval;
    }

    public void setDnsMonitoringInterval(int dnsMonitoringInterval) {
        this.dnsMonitoringInterval = dnsMonitoringInterval;
    }

    public int getThread() {
        return thread;
    }

    public void setThread(int thread) {
        this.thread = thread;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public RedisClusterType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = RedisClusterType.valueOf(type.toUpperCase());
    }

    public ReadMode getReadMode() {
        return readMode;
    }

    public void setReadMode(String readMode) {
        this.readMode = ReadMode.valueOf(readMode.toUpperCase());
    }

    public int getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

    public int getUnlockRetry() {
        return unlockRetry;
    }

    public void setUnlockRetry(int unlockRetry) {
        this.unlockRetry = unlockRetry;
    }
}
