package cn.vimor.toolkit.jedis.service;

import java.io.Closeable;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * /**
 * 开关服务，包括资源关闭功能。
 * <p>
 * 服务继承{@link Closeable}接口，以便使用JDK 7的 <a
 * href="http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html">try-with-resources</a>
 * 语法，同时允许Spring容器能在关闭时释放其持有的资源。
 *
 * @author Jani
 * @date 2022/02/28
 */
public interface BaseJedisService extends Closeable {

    /**
     * 为给定key设置生存时间。
     * 当key过期后(生存时间为0)，它会被自动删除。在Redis中，带有生存时间的key被称为『易失的』(volatile)。
     *
     * @param key      键
     * @param duration 持续时间
     * @return 当超时设置成功时，返回1；当key不存在或者不能为key设置生存时间时，返回0。
     */
    long setExpire(String key, Duration duration);

    /**
     * 为给定key设置生存时间。
     * 当key过期后(生存时间为0)，它会被自动删除。在Redis中，带有生存时间的key被称为『易失的』(volatile)。
     *
     * @param key      key
     * @param time     时间
     * @param timeUnit 时间单位
     * @return long
     */
    long setExpire(String key, int time, TimeUnit timeUnit);

    /**
     * 返回给定key的剩余生存时间(TTL, time to live，以秒为单位)。
     *
     * @param key key
     * @return 当key不存在时，返回-2； 当key存在但没有设置剩余生存时间时，返回-1； 否则，以秒为单位，返回key的剩余生存时间。
     */
    long getExpire(String key);

    /**
     * 删除给定的一个或多个keys。<br>
     * 不存在的key会被忽略。
     *
     * @param key 键
     * @return 被删除key的数量；当没有key被删除时，返回0。
     */
    long del(String key);


    /**
     * 是否存在Key
     *
     * @param key key
     * @return boolean
     */
    boolean existsKey(String key);

    /**
     * 释放服务持有的所有资源。
     * {@inheritDoc}
     */
    @Override
    void close();
}
