package com.study.base.core.redis.utils;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: CacheUtils
 * Description:
 * @Author: luohx
 * Date: 2022/2/22 下午1:43
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public class CacheUtils {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    private static CacheUtils cacheUtils;

    public CacheUtils() {
    }

    @PostConstruct
    public void init() {
        cacheUtils = this;
        cacheUtils.redisTemplate = this.redisTemplate;
    }

    public static Set<String> keys(String keys) {
        try {
            return cacheUtils.redisTemplate.keys(keys);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static boolean expire(String key, long time) {
        try {
            if (time > 0L) {
                cacheUtils.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }

            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public static long getExpire(String key) {
        return cacheUtils.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public static boolean hasKey(String key) {
        try {
            return cacheUtils.redisTemplate.hasKey(key);
        } catch (Exception var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                cacheUtils.redisTemplate.delete(key[0]);
            } else {
                cacheUtils.redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }

    }

    public static boolean saveNX(String key, String val) {
        return (Boolean)cacheUtils.redisTemplate.execute((RedisCallback<Object>) (connection) -> {
            return connection.setNX(key.getBytes(), val.getBytes());
        });
    }

    public static boolean saveNX(String key, String val, int expire) {
        boolean ret = saveNX(key, val);
        if (ret) {
            cacheUtils.redisTemplate.expire(key, (long)expire, TimeUnit.SECONDS);
        }

        return ret;
    }

    public static long incr(String key) {
        return (Long)cacheUtils.redisTemplate.execute((RedisCallback<Object>) (connection) -> {
            return connection.incr(key.getBytes());
        });
    }

    public static long incr(String key, long delta) {
        if (delta < 0L) {
            throw new RuntimeException("递增因子必须大于0");
        } else {
            return cacheUtils.redisTemplate.opsForValue().increment(key, delta);
        }
    }

    public static long decr(String key, long delta) {
        if (delta < 0L) {
            throw new RuntimeException("递减因子必须大于0");
        } else {
            return cacheUtils.redisTemplate.opsForValue().increment(key, -delta);
        }
    }

    public static Object get(String key) {
        return key == null ? null : cacheUtils.redisTemplate.opsForValue().get(key);
    }

    public static boolean set(String key, Object value) {
        try {
            cacheUtils.redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static boolean set(String key, Object value, long timeout) {
        try {
            if (timeout > 0L) {
                cacheUtils.redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
            } else {
                set(key, value);
            }

            return true;
        } catch (Exception var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public static boolean set(String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            if (timeout > 0L) {
                cacheUtils.redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            } else {
                set(key, value);
            }

            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    public static Integer append(String key, String value) {
        return cacheUtils.redisTemplate.opsForValue().append(key, value);
    }

    public static Object getAndSet(String key, String value) {
        return cacheUtils.redisTemplate.opsForValue().getAndSet(key, value);
    }

    public static Object hget(String key, String item) {
        return cacheUtils.redisTemplate.opsForHash().get(key, item);
    }

    public static Map<Object, Object> hmget(String key) {
        return cacheUtils.redisTemplate.opsForHash().entries(key);
    }

    public static boolean hmset(String key, Map<String, Object> map) {
        try {
            cacheUtils.redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            cacheUtils.redisTemplate.opsForHash().putAll(key, map);
            if (time > 0L) {
                expire(key, time);
            }

            return true;
        } catch (Exception var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public static boolean hset(String key, String item, Object value) {
        try {
            cacheUtils.redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    public static boolean hset(String key, String item, Object value, long time) {
        try {
            cacheUtils.redisTemplate.opsForHash().put(key, item, value);
            if (time > 0L) {
                expire(key, time);
            }

            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    public static void hdel(String key, Object... item) {
        cacheUtils.redisTemplate.opsForHash().delete(key, item);
    }

    public static boolean hHasKey(String key, String item) {
        return cacheUtils.redisTemplate.opsForHash().hasKey(key, item);
    }

    public static double hincr(String key, String item, double by) {
        return cacheUtils.redisTemplate.opsForHash().increment(key, item, by);
    }

    public static double hdecr(String key, String item, double by) {
        return cacheUtils.redisTemplate.opsForHash().increment(key, item, -by);
    }

    public static Set<Object> sGet(String key) {
        try {
            return cacheUtils.redisTemplate.opsForSet().members(key);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static boolean sHasKey(String key, Object value) {
        try {
            return cacheUtils.redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static long sSet(String key, Object... values) {
        try {
            return cacheUtils.redisTemplate.opsForSet().add(key, values);
        } catch (Exception var3) {
            var3.printStackTrace();
            return 0L;
        }
    }

    public static long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = cacheUtils.redisTemplate.opsForSet().add(key, values);
            if (time > 0L) {
                expire(key, time);
            }

            return count;
        } catch (Exception var5) {
            var5.printStackTrace();
            return 0L;
        }
    }

    public static long sGetSetSize(String key) {
        try {
            return cacheUtils.redisTemplate.opsForSet().size(key);
        } catch (Exception var2) {
            var2.printStackTrace();
            return 0L;
        }
    }

    public static long setRemove(String key, Object... values) {
        try {
            Long count = cacheUtils.redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception var3) {
            var3.printStackTrace();
            return 0L;
        }
    }

    public static List<Object> lGet(String key, long start, long end) {
        try {
            return cacheUtils.redisTemplate.opsForList().range(key, start, end);
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static long lGetListSize(String key) {
        try {
            return cacheUtils.redisTemplate.opsForList().size(key);
        } catch (Exception var2) {
            var2.printStackTrace();
            return 0L;
        }
    }

    public static Object lGetIndex(String key, long index) {
        try {
            return cacheUtils.redisTemplate.opsForList().index(key, index);
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static boolean lSet(String key, Object value) {
        try {
            cacheUtils.redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static boolean lSet(String key, Object value, long time) {
        try {
            cacheUtils.redisTemplate.opsForList().rightPush(key, value);
            if (time > 0L) {
                expire(key, time);
            }

            return true;
        } catch (Exception var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public static boolean lSet(String key, List<Object> value) {
        try {
            cacheUtils.redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public static boolean lSet(String key, List<Object> value, long time) {
        try {
            cacheUtils.redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0L) {
                expire(key, time);
            }

            return true;
        } catch (Exception var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public static boolean lUpdateIndex(String key, long index, Object value) {
        try {
            cacheUtils.redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception var5) {
            var5.printStackTrace();
            return false;
        }
    }

    public static long lRemove(String key, long count, Object value) {
        try {
            Long remove = cacheUtils.redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception var5) {
            var5.printStackTrace();
            return 0L;
        }
    }

    public static Boolean lock(String key) {
        return lock(key, TimeUnit.MILLISECONDS, 3000L, 30000L);
    }

    public static Boolean lock(String key, TimeUnit timeUnit, long leaseTime) {
        return lock(key, timeUnit, 3000L, leaseTime);
    }

    public static Boolean lock(String key, TimeUnit timeUnit, long waitTime, long leaseTime) {
        waitTime = timeUnit.toMillis(waitTime);
        String requestId = UUID.randomUUID().toString();
        Long start = System.currentTimeMillis();

        while(true) {
            Boolean ret = cacheUtils.redisTemplate.opsForValue().setIfAbsent(key, requestId, leaseTime, timeUnit);
            if (ret) {
                return true;
            }

            if (waitTime != -1L) {
                long end = System.currentTimeMillis() - start;
                if (end >= waitTime) {
                    return false;
                }
            }
        }
    }

    public static void unlock(String key) {
        if (!StringUtils.isEmpty(key)) {
            cacheUtils.redisTemplate.delete(key);
        }

    }
}