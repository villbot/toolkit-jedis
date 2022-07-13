package cn.vimor.toolkit.jedis.service.operations.impl;

import cn.vimor.toolkit.jedis.service.impl.JedisServiceImpl;
import cn.vimor.toolkit.jedis.service.operations.ValueOperations;
import cn.vimor.toolkit.jedis.service.operations.abstracts.AbstractBashOperation;
import cn.vimor.toolkit.jedis.util.AbstractAssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 默认值操作
 *
 * @author Jani
 * @date 2022/02/28
 */
public class DefaultValueOperations extends AbstractBashOperation implements ValueOperations {

    private static final Logger logger = LoggerFactory.getLogger(JedisServiceImpl.class);

    private final JedisServiceImpl jedisService;

    private final String message = "'key' must not be null and empty";

    public DefaultValueOperations(JedisServiceImpl jedisService) {
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
    public String get(String key) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.get(key);
        } catch (JedisException e) {
            logger.error("'get' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }

        return null;
    }

    @Override
    public String set(String key, String value) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.set(key, value);
        } catch (JedisException e) {
            logger.error("'set' key fail, key: {}, value: {}", key, value);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Boolean setBit(String key, long offset, boolean value) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.setbit(key, offset, value);
        } catch (JedisException e) {
            logger.error("'setBit' key fail, key: {}, offset: {}, value: {}", key, offset, value);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Boolean getBit(String key, long offset) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.getbit(key, offset);
        } catch (JedisException e) {
            logger.error("'getBit' key fail, key: {}, offset: {}", key, offset);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Long bitCount(String key) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.bitcount(key);
        } catch (JedisException e) {
            logger.error("'bitCount' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public String setEx(String key, int seconds, String value) {
        AbstractAssertUtils.notEmpty(key, message);

        if (seconds > 0) {
            Jedis jedis = null;
            try {
                jedis = jedisService.getJedis();
                return jedis.setex(key, seconds, value);
            } catch (JedisException e) {
                logger.error("'setex' key fail, key: {}, seconds: {}, value: {}", key, seconds, value);
                logger.error(e.getMessage(), e);
            } finally {
                close(jedis);
            }
        } // 当seconds参数不合法(<= 0)时，后端会返回一个错误 ("JedisDataException: ERR invalid expire time in setex")，即操作失败
        return null;
    }

    @Override
    public Long increment(String key) {
        AbstractAssertUtils.notEmpty(key, message);
        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.incr(key);
        } catch (JedisException e) {
            logger.error("'increment' key fail, key: {}", key);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Long incrementBy(String key, long delta) {
        AbstractAssertUtils.notEmpty(key, message);
        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.incrBy(key, delta);
        } catch (JedisException e) {
            logger.error("'incrementBy' key fail, key: {}, delta: {}", key, delta);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Double incrementByFloat(String key, double delta) {
        AbstractAssertUtils.notEmpty(key, message);
        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.incrByFloat(key, delta);
        } catch (JedisException e) {
            logger.error("'incrementByFloat' key fail, key: {}, delta: {}", key, delta);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public List<String> multiGet(List<String> keys) {
        AbstractAssertUtils.notEmpty(keys, message);
        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            String[] strings = keys.toArray(new String[0]);
            return jedis.mget(strings);
        } catch (JedisException e) {
            logger.error("'incrementByFloat' key fail, key: {}", keys);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public List<String> piplineGet(List<String> keys) {
        long l = System.currentTimeMillis();
        System.out.println("进入pipline时间" + l);
        AbstractAssertUtils.notEmpty(keys, message);
        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            System.out.println("获取jedis链接时间" + (System.currentTimeMillis() - l));
            l = System.currentTimeMillis();
            Pipeline pipelined = jedis.pipelined();
            for (String key : keys) {
                pipelined.get(key);
            }
            List<Object> list = pipelined.syncAndReturnAll();
            System.out.println("得到管道时间" + (System.currentTimeMillis() - l));
            l = System.currentTimeMillis();
            if (!ObjectUtils.isEmpty(list)) {
                List<String> collect = list.stream().map(String::valueOf).collect(Collectors.toList());
                System.out.println("处理数据后时间" + (System.currentTimeMillis() - l));
                return collect;
            }
        } catch (JedisException e) {
            logger.error("'incrementByFloat' key fail, key: {}", keys);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }
}
