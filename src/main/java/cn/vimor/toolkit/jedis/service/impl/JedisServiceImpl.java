package cn.vimor.toolkit.jedis.service.impl;

import cn.vimor.toolkit.jedis.service.JedisService;
import cn.vimor.toolkit.jedis.service.operations.*;
import cn.vimor.toolkit.jedis.service.operations.impl.*;
import cn.vimor.toolkit.jedis.util.AbstractAssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Jedis服务实现类
 *
 * @author Jani
 * @date 2022/02/28
 */
@Service
@EnableScheduling
public class JedisServiceImpl implements JedisService {

    private static final Logger logger = LoggerFactory.getLogger(JedisServiceImpl.class);
    /**
     * 消息
     */
    private final String message = "'key' must not be null and empty";
    private final ValueOperations valueOps = new DefaultValueOperations(this);
    private final ListOperations listOps = new DefaultListOperations(this);
    private final HashOperations hashOps = new DefaultHashOperations(this);
    private final SetOperations setOps = new DefaultSetOperations(this);
    private final ZsetOperations zSetOps = new DefaultZSetOperations(this);
    /**
     * jedis连接池
     */
    @Resource
    private volatile JedisPool jedisPool;

    @Override
    public ValueOperations opsForValue() {
        return valueOps;
    }

    @Override
    public ListOperations opsForList() {
        return listOps;
    }

    @Override
    public HashOperations opsForHash() {
        return hashOps;
    }

    @Override
    public SetOperations opsForSet() {
        return setOps;
    }

    @Override
    public ZsetOperations opsForZset() {
        return zSetOps;
    }

    @Override
    public long setExpire(String key, Duration duration) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            return jedis.expire(key, duration.getSeconds());
        } catch (JedisException e) {
            logger.error("'expire' key fail, key: {} , duration:{} ", key, duration);
            logger.error(e.getMessage(), e);
        } finally {
            closeJedis(jedis);
        }
        return 0;
    }

    @Override
    public long setExpire(String key, int time, TimeUnit timeUnit) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            return jedis.expire(key, timeUnit.toSeconds(time));
        } catch (JedisException e) {
            logger.error("'expire' key fail, key: {} , time:{}, timeUnit:{} ", key, time, timeUnit);
            logger.error(e.getMessage(), e);
        } finally {
            closeJedis(jedis);
        }
        return 0;
    }

    @Override
    public long getExpire(String key) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            return jedis.ttl(key);
        } catch (JedisException e) {
            logger.error("'ttl' key fail, key: {} ", key);
            logger.error(e.getMessage(), e);
        } finally {
            closeJedis(jedis);
        }
        return 0;
    }

    @Override
    public long del(String key) {

        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            return jedis.del(key);
        } catch (JedisException e) {
            logger.error("'ttl' key fail, key: {} ", key);
            logger.error(e.getMessage(), e);
        } finally {
            closeJedis(jedis);
        }
        return 0;
    }

    @Override
    public boolean existsKey(String key) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.getJedis();
            return jedis.exists(key);
        } catch (JedisException e) {
            logger.error("'ttl' key fail, key: {} ", key);
            logger.error(e.getMessage(), e);
        } finally {
            closeJedis(jedis);
        }
        return false;
    }

    @Override
    public void close() {
        if (!ObjectUtils.isEmpty(jedisPool)) {
            jedisPool.close();
        }
    }

    /**
     * 获取Jedis
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
