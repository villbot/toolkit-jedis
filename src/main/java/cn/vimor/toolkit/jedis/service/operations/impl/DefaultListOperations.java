package cn.vimor.toolkit.jedis.service.operations.impl;

import cn.vimor.toolkit.jedis.service.impl.JedisServiceImpl;
import cn.vimor.toolkit.jedis.service.operations.ListOperations;
import cn.vimor.toolkit.jedis.service.operations.abstracts.AbstractBashOperation;
import cn.vimor.toolkit.jedis.util.AbstractAssertUtils;
import cn.vimor.toolkit.jedis.util.StringArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * 默认值操作
 *
 * @author Jani
 * @date 2022/02/28
 */
public class DefaultListOperations extends AbstractBashOperation implements ListOperations {

    private static final Logger logger = LoggerFactory.getLogger(JedisServiceImpl.class);

    private final JedisServiceImpl jedisService;

    private final String message = "'key' must not be null and empty";

    public DefaultListOperations(JedisServiceImpl jedisService) {
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
    public long lLen(String key) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.llen(key);
        } catch (JedisException e) {
            logger.error("'lLen' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return 0;
    }

    @Override
    public long lPush(String key, String... values) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.lpush(key, values);
        } catch (JedisException e) {
            logger.error("'lPush' key fail, key: {}, values: {}", key, Arrays.toString(values));
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return 0;
    }

    @Override
    public String rPop(String key) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.rpop(key);
        } catch (JedisException e) {
            logger.error("'rPop' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public List<String> lRange(String key, int start, int stop) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.lrange(key, start, stop);
        } catch (JedisException e) {
            logger.error("'lRange' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return Collections.emptyList();
    }

    @Override
    public String ltrim(String key, int start, int stop) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.ltrim(key, start, stop);
        } catch (JedisException e) {
            logger.error("'ltrim' key fail, key: {}, start: {}, stop: {}", key, start, stop);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public List<String> get(String key) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.lrange(key, 0, -1);
        } catch (JedisException e) {
            logger.error("'get' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public List<String> get(String key, Long endRange) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.lrange(key, 0, endRange);
        } catch (JedisException e) {
            logger.error("'get' key fail, key: {}, end: {}", key, endRange);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public List<String> get(String key, Long startRange, Long endRange) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.lrange(key, startRange, endRange);
        } catch (JedisException e) {
            logger.error("'get' key fail, key: {},start: {} end: {}", key, startRange, endRange);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public String lPop(String key) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.lpop(key);
        } catch (JedisException e) {
            logger.error("'lPop' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public void lRem(String key, String value) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            jedis.lrem(key, 0L, value);
        } catch (JedisException e) {
            logger.error("'lRem' key fail, key: {} , value:{}", key, value);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
    }

    @Override
    public void rPush(String key, String... strings) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            jedis.rpush(key, strings);
        } catch (JedisException e) {
            logger.error("'lRem' key fail, key: {} , value:{}", key, strings);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
    }

    @Override
    public void rPush(String key, List<String> list) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            jedis.rpush(key, StringArrayUtil.toArray(list));
        } catch (JedisException e) {
            logger.error("'lRem' key fail, key: {} , value:{}", key, list);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
    }

}
