package cn.vimor.toolkit.jedis.service.operations.impl;

import cn.vimor.toolkit.jedis.service.impl.JedisServiceImpl;
import cn.vimor.toolkit.jedis.service.operations.HashOperations;
import cn.vimor.toolkit.jedis.service.operations.abstracts.AbstractBashOperation;
import cn.vimor.toolkit.jedis.util.AbstractAssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 默认值操作
 *
 * @author Jani
 * @date 2022/02/28
 */
public class DefaultHashOperations extends AbstractBashOperation implements HashOperations {

    private static final Logger logger = LoggerFactory.getLogger(JedisServiceImpl.class);

    private final JedisServiceImpl jedisService;

    private final String message = "'key' must not be null and empty";

    public DefaultHashOperations(JedisServiceImpl jedisService) {
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
    public void hSet(String key, Map<String, String> map) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            Set<String> keySet = map.keySet();
            for (String mapKey : keySet) {
                jedis.hset(key, mapKey, map.get(mapKey));
            }
        } catch (JedisException e) {
            logger.error("'hSet' key fail, key: {} , value:{}", key, map);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
    }

    @Override
    public void hSet(String key, String filedKey, String filedValue) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            jedis.hset(key, filedKey, filedValue);
        } catch (JedisException e) {
            logger.error("'hSet' key fail, key: {} , value:{}", filedKey, filedValue);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
    }

    @Override
    public Map<String, String> hGetAll(String key) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.hgetAll(key);
        } catch (JedisException e) {
            logger.error("'hGetAll' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Map<String, String> hGet(String key, Set<String> keySet) {
        AbstractAssertUtils.notEmpty(key, message);
        Map<String, String> map = null;
        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            Map<String, String> tempMap = jedis.hgetAll(key);
            for (String mapKey : keySet) {
                map = new HashMap<>(8);
                map.put(mapKey, tempMap.get(mapKey));
            }
        } catch (JedisException e) {
            logger.error("'hGet' key fail, key: {} , keySet:{}", key, keySet);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return map;
    }

    @Override
    public String hGet(String key, String filedKey) {
        AbstractAssertUtils.notEmpty(key, message);
        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.hget(key, filedKey);
        } catch (JedisException e) {
            logger.error("'hGet' key fail, key: {} , filedKey:{}", key, filedKey);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Boolean hExists(String key, String filedKey) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.hexists(key, filedKey);
        } catch (JedisException e) {
            logger.error("'hExists' key fail, key: {} , filedKey:{}", key, filedKey);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public long hDel(String key, String... field) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = this.jedisService.getJedis();
            return jedis.hdel(key, field);
        } catch (JedisException e) {
            logger.error("'hExists' key fail, key: {} , field:{}", key, field);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return 0;
    }
}
