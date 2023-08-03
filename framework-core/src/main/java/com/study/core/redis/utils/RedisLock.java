package com.study.core.redis.utils;

import com.study.core.exception.ServiceException;
import com.study.core.redis.distributeLock.DistributeLockFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2023/7/21 上午10:16
 * @menu
 */
@Component
public class RedisLock {

    //日志
    private static final Logger log = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private DistributeLockFactory lock;

    /**
     * 功能描述:  获取锁 不等待
     *
     * @param:
     * @return:gi
     * @auther:
     * @date: 2021/11/26 上午10:42
     */
    public <T> T syncHandle(String lockKey, Supplier<T> handleFun) {
        return syncLockHandle(lockKey, 0, handleFun, "");
    }

    /**
     * 功能描述: 获取锁 不等待
     *
     * @param:
     * @return:
     * @auther:
     * @date: 2021/12/3 上午10:58
     */
    public <T> T syncHandle(String lockKey, Supplier<T> handleFun, String errorMsg) {
        return syncLockHandle(lockKey, 0, handleFun, errorMsg);
    }


    /**
     * 功能描述:  获取锁 等待 waitMilli 单位毫秒
     *
     * @param:
     * @return:
     * @auther:
     * @date: 2021/11/26 上午10:42
     */
    public <T> T syncHandle(String lockKey, long waitMilli, Supplier<T> handleFun, String errorMsg) {
        return syncLockHandle(lockKey, waitMilli, handleFun, errorMsg);
    }

    /**
     * 功能描述:  获取锁 一直等待
     *
     * @param:
     * @return:
     * @auther:
     * @date: 2021/11/26 上午10:42
     */
    public <T> T syncWaitHandle(String lockKey, Supplier<T> handleFun, String errorMsg) {
        return syncLockHandle(lockKey, -1, handleFun, errorMsg);
    }


    /**
     * 功能描述: trylock()和lock()的区别：trylock会立即返回，锁获取成功返回true否则返回false。lock没有返回，会一直阻塞直到锁获取成功
     *
     * @param:
     * @return:
     * @auther:
     * @date: 2022/2/14 下午2:49
     */
    private <T> T syncLockHandle(String lockKey, long waitMilli, Supplier<T> handleFun, String errorMsg) {
        Lock lock = null;
        T result;
        if (StringUtils.isBlank(errorMsg)) {
            errorMsg = "{" + lockKey + "}->get lock error";
        }
        boolean acquired = true;
        try {
            lock = this.lock.getLock(lockKey);
            if (lock == null) {
                throw new ServiceException(errorMsg);
            }

            // 一直阻滞
            if (waitMilli < 0) {
                lock.lock();
            } else if (waitMilli > 0) {
                //等待多少秒
                acquired = lock.tryLock(waitMilli, TimeUnit.MILLISECONDS);
            } else {
                //不等待
                acquired = lock.tryLock();
            }
            if (!acquired) {
                throw new ServiceException(errorMsg);
            }
            result = handleFun.get();
        } catch (ServiceException e1) {
            throw e1;
        } catch (Throwable e) {
            log.error("", e);
            throw new ServiceException(errorMsg);
        } finally {
            if (lock != null && acquired) {
                lock.unlock();
            }
        }
        return result;
    }

}