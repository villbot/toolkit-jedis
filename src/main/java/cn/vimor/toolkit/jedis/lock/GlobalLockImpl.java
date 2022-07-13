package cn.vimor.toolkit.jedis.lock;

import cn.vimor.toolkit.jedis.util.AbstractAssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.params.SetParams;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁实现
 *
 * @author Jani
 */
@Service
public class GlobalLockImpl implements GlobalLock {

    private static final Logger logger = LoggerFactory.getLogger(GlobalLockImpl.class);
    /**
     * 锁key
     */
    private final static String LOCK_KEY = "vlightv:global:lock:";
    /**
     * threadLocal当前线程持有
     */
    private final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    /**
     * 消息
     */
    private final String message = "'key' must not be null and empty";
    /**
     * 释放锁时间
     */
    @Value("${jedis.lock.px}")
    private Long lockEx;
    /**
     * 默认释放锁的时间
     */
    private long defaultEx = 3000;
    /**
     * jedis连接池
     */
    @Resource
    private volatile JedisPool jedisPool;

    @PostConstruct
    public void init() {
        if (!ObjectUtils.isEmpty(lockEx)) {
            defaultEx = lockEx;
        }
    }

    @Override
    public void lock(String key) {
        lock(key, defaultEx);
    }

    @Override
    public void lock(String key, long time, TimeUnit timeUnit) {
        lock(key, timeUnit.toMillis(time));
    }

    /**
     * 锁
     *
     * @param key             key
     * @param secondsToExpire 秒
     */
    private void lock(String key, long secondsToExpire) {
        AbstractAssertUtils.notEmpty(key, message);
        Jedis jedis = null;
        try {
            jedis = getJedis();
            //获取锁的key
            String lockKey = getLockKey(key);
            //获取锁版本
            String lockVersion = getLockVersion();
            threadLocal.set(lockVersion);
            //自旋
            for (; ; ) {
                //尝试取得锁 nx不存在则设定成功 px失效
                SetParams setParams = new SetParams();
                setParams.nx();
                setParams.px(secondsToExpire);
                String result = jedis.set(lockKey, lockVersion, setParams);
                if (!Objects.isNull(result)) {
                    return;
                }
            }
        } catch (JedisException e) {
            logger.error("'expire' key fail, key: {} , seconds:{}", key, secondsToExpire);
            logger.error(e.getMessage(), e);
        } finally {
            closeJedis(jedis);
        }
    }


    @Override
    public boolean lockNoWait(String key) {
        return lockNoWait(key, defaultEx);
    }

    @Override
    public boolean lockNoWait(String key, long time, TimeUnit timeUnit) {
        return lockNoWait(key, timeUnit.toMillis(time));
    }

    /**
     * 锁没有等待
     *
     * @param key             key
     * @param secondsToExpire 秒到期
     * @return boolean
     */
    private boolean lockNoWait(String key, long secondsToExpire) {
        AbstractAssertUtils.notEmpty(key, message);
        Jedis jedis = null;
        try {
            jedis = getJedis();
            //获取锁的key
            String lockKey = getLockKey(key);
            //获取锁版本
            String lockVersion = getLockVersion();
            threadLocal.set(lockVersion);
            //尝试取得锁 nx不存在则设定成功 px失效
            SetParams setParams = new SetParams();
            setParams.nx();
            setParams.px(secondsToExpire);
            String result = jedis.set(lockKey, lockVersion, setParams);
            if (!Objects.isNull(result)) {
                return true;
            }
        } catch (JedisException e) {
            logger.error("'expire' key fail, key: {} , seconds:{}", key, secondsToExpire);
            logger.error(e.getMessage(), e);
        } finally {
            closeJedis(jedis);
        }
        threadLocal.remove();
        return false;
    }


    @Override
    public void delayLock(String key) {
        delayLock(key, defaultEx);
    }

    @Override
    public void delayLock(String key, long time, TimeUnit timeUnit) {
        delayLock(key, timeUnit.toMillis(time));
    }

    /**
     * 延迟锁定
     *
     * @param key             key
     * @param secondsToExpire 秒到期
     */
    public void delayLock(String key, long secondsToExpire) {
        AbstractAssertUtils.notEmpty(key, message);
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String lockVersion = threadLocal.get();
            String lockKey = getLockKey(key);
            //判断锁是否为当前线程所持有
            if (lockVersion.equals(jedis.get(lockKey))) {
                SetParams setParams = new SetParams();
                setParams.px(secondsToExpire);
                jedis.set(lockKey, lockVersion, setParams);
            }
        } catch (JedisException e) {
            logger.error("'expire' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            closeJedis(jedis);
        }
    }


    @Override
    public Boolean tryLock(String key) {
        AbstractAssertUtils.notEmpty(key, message);
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String lockKey = getLockKey(key);
            return !jedis.exists(lockKey);
        } catch (JedisException e) {
            logger.error("'expire' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    @Override
    public Boolean unLock(String key) {

        AbstractAssertUtils.notEmpty(key, message);
        Jedis jedis = null;
        boolean result = false;
        try {
            jedis = getJedis();
            String lockKey = getLockKey(key);
            String lockVersion = threadLocal.get();
            //判断锁是否为当前线程所持有
            if (!ObjectUtils.isEmpty(lockVersion) && lockVersion.equals(jedis.get(lockKey))) {
                //尝试释放锁
                result = jedis.del(lockKey) > 0;
                //锁释放成功
                threadLocal.remove();
            }
        } catch (JedisException e) {
            logger.error("'expire' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            closeJedis(jedis);
        }
        return result;
    }

    /**
     * 得到锁key
     *
     * @param key key
     * @return {@link String}
     */
    private String getLockKey(String key) {
        return LOCK_KEY + key;
    }

    /**
     * 获取锁版本
     *
     * @return {@link String}
     */
    private String getLockVersion() {
        String uuid = UUID.randomUUID().toString().replaceAll("_", "");
        uuid += Thread.currentThread().getId();
        return uuid;
    }

    /**
     * 得到Jedis
     *
     * @return {@link Jedis}
     */
    public synchronized Jedis getJedis() {
        Jedis jedis = null;
        if (!ObjectUtils.isEmpty(jedisPool)) {
            jedis = jedisPool.getResource();
        }
        return jedis;
    }


    /**
     * 关闭Jedis
     *
     * @param jedis Jedis
     */
    private void closeJedis(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (JedisException e) {
                logger.error("ShardedJedis close fail", e);
            }
        }
    }
}
