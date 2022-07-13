package cn.vimor.toolkit.jedis.service.operations.impl;

import cn.vimor.toolkit.jedis.service.impl.JedisServiceImpl;
import cn.vimor.toolkit.jedis.service.operations.SetOperations;
import cn.vimor.toolkit.jedis.service.operations.abstracts.AbstractBashOperation;
import cn.vimor.toolkit.jedis.util.AbstractAssertUtils;
import cn.vimor.toolkit.jedis.util.StringArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Set;

/**
 * 默认设置操作
 *
 * @author Jani
 * @date 2022/02/28
 */
public class DefaultSetOperations extends AbstractBashOperation implements SetOperations {

    private static final Logger logger = LoggerFactory.getLogger(JedisServiceImpl.class);

    private final JedisServiceImpl jedisService;

    private final String message = "'key' must not be null and empty";

    public DefaultSetOperations(JedisServiceImpl jedisService) {
        this.jedisService = jedisService;
    }

    @Override
    protected void close(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (JedisException e) {
                logger.error("ShardedJedis close fail", e);
            }
        }
    }

    @Override
    public void batchAdd(String key, Set<String> values) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            jedis.sadd(key, StringArrayUtil.toArray(values));
        } catch (JedisException e) {
            logger.error("'sAdd' key fail, key: {}, value: {}", key, values);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
    }

    @Override
    public void add(String key, String setValue) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            jedis.sadd(key, setValue);
        } catch (JedisException e) {
            logger.error("'sAdd' key fail, key: {}, value: {}", key, setValue);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
    }

    @Override
    public Set<String> members(String key) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.smembers(key);
        } catch (JedisException e) {
            logger.error("'sMembers' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Long sCard(String key) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.scard(key);
        } catch (JedisException e) {
            logger.error("'sCard' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Boolean isMember(String key, String member) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.sismember(key, member);
        } catch (JedisException e) {
            logger.error("'sisMember' key fail, key: {} ,member:{}", key, member);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public void sRem(String key, String... member) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            jedis.srem(key, member);
        } catch (JedisException e) {
            logger.error("'sRem' key fail, key: {} ,member:{}", key, member);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
    }

    @Override
    public String sRandMember(String key) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.srandmember(key);
        } catch (JedisException e) {
            logger.error("'sRandMember' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }
}
