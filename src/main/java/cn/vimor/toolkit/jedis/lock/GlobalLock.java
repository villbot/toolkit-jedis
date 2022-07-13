package cn.vimor.toolkit.jedis.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author chaolei
 */
public interface GlobalLock {

    /**
     * 加锁
     *
     * @param key 关键
     */
    void lock(String key);

    /**
     * 加锁
     *
     * @param key      关键
     * @param time     时间
     * @param timeUnit 时间单位
     */
    void lock(String key, long time, TimeUnit timeUnit);


    /**
     * 锁没有等待
     *
     * @param key key
     * @return boolean
     */
    boolean lockNoWait(String key);

    /**
     * 锁没有等待
     *
     * @param key      key
     * @param time     时间
     * @param timeUnit 时间单位
     * @return boolean
     */
    boolean lockNoWait(String key, long time, TimeUnit timeUnit);

    /**
     * 延迟锁持有时间
     *
     * @param key 关键
     */
    void delayLock(String key);

    /**
     * 延迟锁持有时间
     *
     * @param key      key
     * @param time     时间
     * @param timeUnit 时间单位
     */
    void delayLock(String key, long time, TimeUnit timeUnit);

    /**
     * 尝试获取锁
     *
     * @param key 关键
     * @return boolean
     */
    Boolean tryLock(String key);

    /**
     * 释放锁
     *
     * @param key 关键
     * @return boolean
     */
    Boolean unLock(String key);
}
