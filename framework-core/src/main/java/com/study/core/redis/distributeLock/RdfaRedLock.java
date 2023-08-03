package com.study.core.redis.distributeLock;

import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2023/7/20 下午1:33
 * @menu
 */
public class RdfaRedLock extends RedissonMultiLock {

    private static Logger logger = LoggerFactory.getLogger(RdfaRedLock.class);

    private AtomicReference<Thread> thread = new AtomicReference<>();

    //凡是对reentrantCount的赋值都必须是在获取分布式锁之后
    private volatile int reentrantCount = 0;

    private String lockKey;

    private int unlockRetryTime=3;


    /**
     * redis计算重入次数不可靠，改为本地计算。上锁及释放锁必须在同一个锁对象中进行
     *
     * @param locks - array of locks
     */
    public RdfaRedLock(String lockKey, int unlockRetryTime, RLock... locks) {
        super(locks);
        this.lockKey = lockKey;
        this.unlockRetryTime = unlockRetryTime;
    }

    public String getLockKey() {
        return lockKey;
    }

    @Override
    protected int failedLocksLimit() {
        return locks.size() - minLocksAmount(locks);
    }

    protected int minLocksAmount(final List<RLock> locks) {
        return locks.size()/2 + 1;
    }

    @Override
    protected long calcLockWaitTime(long remainTime) {
        return Math.max(remainTime / locks.size(), 1);
    }

    /**
     * 重入次数为1时代表本线程占有锁,可以释放分布式锁
     * 重入次数如果>1代表本线程占有锁,将重入次数-1
     * 如果当前线程不是占用线程,则不能做任何操作
     */
    @Override
    public void unlock() {
        if(this.thread==null){
            logger.warn("this lock is not locked lockKey:{}",lockKey);
            return;
        }
        if(Thread.currentThread()!=this.thread.get()) {
            logger.warn("must unlock in lock thread lockKey : {} lock thread : {} current thread:{}",lockKey,this.thread.get().getId(),Thread.currentThread().getId());
            return;
        }
        logger.info("unlock start,lockKey:{}",lockKey);
        if (reentrantCount==1) {
            if(this.unlockInner(locks,0,failedLocksLimit())){
                //此时分布式锁已经unlock,本地不同线程之的lock和unlock可能产生并发,此处使用cas为了避免在已经被其他线程抢占到锁的情况下,误将thread设为null
                this.thread.compareAndSet(Thread.currentThread(),null);
                this.unlockRetryTime = 3;
                //没有必要再设置reetrantCount,以免引起并发问题
                logger.info("unlock success,lockKey:{}",lockKey);
            }
        } else if (reentrantCount>0) {
            reentrantCount--;
            logger.info("reetrantCount decreased,lockKey:{}",lockKey);
        }
    }

    /**
     *
     * @return 锁完全释放返回true,否则返回false,为了兼容低版本
     */
    public boolean tryUnlock(){
        this.unlock();
        return this.thread.get()!=Thread.currentThread(); //本线程释放锁之后要么是null,也可能已经被其他线程抢占
    }

    /**
     * 释放分布式锁,强制删除key,如果释放失败重试,释放成功返回true,否则返回false
     * @param locks
     */
    protected boolean unlockInner(Collection<RLock> locks, long unlockStartTime, int failedLocksLimit) {
        unlockStartTime = unlockStartTime==0?System.currentTimeMillis():unlockStartTime;
        Map<RFuture<Boolean>,RLock> futureRLockMap = new HashMap<>();
        List<RLock> failedLock = new ArrayList();
        for (RLock lock : locks) {
            futureRLockMap.put(lock.forceUnlockAsync(),lock);
        }
        int failedLocks = 0;
        for (RFuture<Boolean> unlockFuture : futureRLockMap.keySet()) {
            try {
                unlockFuture.get();
                logger.info("rlock unlock success:lock:{},key:{}",futureRLockMap.get(unlockFuture),lockKey);
            }
            catch (InterruptedException | CancellationException | ExecutionException e) {
                logger.warn("unlock exception key:{} ex:{}",lockKey,e);
                failedLocks++;
                failedLock.add(futureRLockMap.get(unlockFuture));
            }
        }
        if(failedLocks > failedLocksLimit){
            logger.warn("unlock fail retry key:{},failCount:{}",lockKey,failedLocks);
            if(unlockRetryTime>0) {
                try {
                    if(super.tryLock(4500, -1, TimeUnit.MILLISECONDS)){
                        unlockRetryTime--;
                        unlockInner(failedLock,unlockStartTime,failedLocksLimit-(locks.size()-failedLocks));
                    }
                    else{
                        logger.warn("unlock retry aborted cause trylock fail,this lock may holded by other thread");
                    }
                }
                catch (InterruptedException ex){
                    return false;
                }
            }
            else{
                logger.error("unlock fail after all, key:{}",lockKey);
                return false;
            }
        }
        else {
            logger.info("redission lock unlocked sucess:{}", lockKey);
        }
        return true;
    }

    /**
     * 当前线程不是占用线程,或者没有占用线程,需要抢占分布式锁,如果抢占成功则增加重入次数
     * 当前线程就是占用线程,代表本线程已经占有锁直接增加重入次数
     * 设置thread对象时lock比unlock优先,因为Lock是获取分布式锁之后再设值
     * @param waitTime
     * @param leaseTime
     * @param unit
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException{
        logger.info("tryLock start,lockKey:{}",lockKey);
        if(this.thread.get()!=Thread.currentThread()) {
            boolean acquired = super.tryLock(waitTime, leaseTime, unit);
            if(acquired) {
                //此时已经获得分布式锁,不会有并发,可以放心赋值
                this.thread.set(Thread.currentThread());
                //这里必须是1
                reentrantCount = 1;
            }
            logger.info("tryLock end,lockKey:{},acquired:{}",lockKey,acquired);
            return acquired;
        }
        reentrantCount++;
        logger.info("tryLock end,lockKey:{},reentrantCount decreased",lockKey);
        return true;
    }

}