package cn.vimor.toolkit.jedis.service.operations.impl;

import cn.vimor.toolkit.jedis.service.impl.JedisServiceImpl;
import cn.vimor.toolkit.jedis.service.operations.ZsetOperations;
import cn.vimor.toolkit.jedis.service.operations.abstracts.AbstractBashOperation;
import cn.vimor.toolkit.jedis.util.AbstractAssertUtils;
import cn.vimor.toolkit.jedis.util.StringArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.resps.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 默认zset操作
 *
 * @author Jani
 * @date 2022/02/28
 */
public class DefaultZSetOperations extends AbstractBashOperation implements ZsetOperations {

    private static final Logger logger = LoggerFactory.getLogger(JedisServiceImpl.class);

    private final JedisServiceImpl jedisService;

    private final String message = "'key' must not be null and empty";

    public DefaultZSetOperations(JedisServiceImpl jedisService) {
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
    public long zAdd(String key, double score, String member) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zadd(key, score, member);
        } catch (JedisException e) {
            logger.error("'zAdd' key fail, key: {}, score: {}, member: {}", key, score, member);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return 0;
    }

    @Override
    public long zAdd(String key, Map<String, Double> scoreMembers) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zadd(key, scoreMembers);
        } catch (JedisException e) {
            logger.error("'zadd' key fail, key: {}, scoreMembers: {}", key, scoreMembers);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return 0;
    }

    @Override
    public double zIncrement(String key, double score, String member) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            jedis.zincrby(key, score, member);
        } catch (JedisException e) {
            logger.error("'zIncrement' key fail, key: {}, score: {}, member: {}", key, score, member);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return 0;
    }

    @Override
    public List<String> zRange(String key) {
        return zRange(key, 0L, -1L);
    }

    @Override
    public List<String> zRange(String key, Long end) {
        return zRange(key, 0L, end);
    }

    @Override
    public List<String> zRange(String key, Long start, Long end) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zrange(key, start, end);
        } catch (JedisException e) {
            logger.error("'zRange' key fail, key: {},start:{},end:{}", key, start, end);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public List<Tuple> zRangeWithScores(String key, Long start, Long end) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zrangeWithScores(key, start, end);
        } catch (JedisException e) {
            logger.error("'zRangeWithScores' key fail, key: {},start:{},end:{}", key, start, end);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public List<Tuple> zRangeWithScores(String key, double min, double max) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zrangeByScoreWithScores(key, min, max);
        } catch (JedisException e) {
            logger.error("'zRangeWithScores' key fail, key: {},min:{},max:{}", key, min, max);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public List<Tuple> zRangeWithScores(String key, double min, double max, Integer start, Integer end) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zrangeByScoreWithScores(key, min, max, start, end);
        } catch (JedisException e) {
            logger.error("'zRangeWithScores' key fail, key: {},min:{},max:{},start:{},end:{}", key, min, max, start, end);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public List<Tuple> zRevRangeWithScores(String key, double max, double min, Integer start, Integer end) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zrevrangeByScoreWithScores(key, max, min, start, end);
        } catch (JedisException e) {
            logger.error("'zRevRangeWithScores' key fail, key: {},min:{},max:{},start:{},end:{}", key, min, max, start, end);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public void zRem(String key, String value) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            jedis.zrem(key, value);
        } catch (JedisException e) {
            logger.error("'zRem' key fail, key: {},value:{}", key, value);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
    }

    @Override
    public void zRem(String key, Set<String> values) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            jedis.zrem(key, StringArrayUtil.toArray(values));
        } catch (JedisException e) {
            logger.error("'zRem' key fail, key: {},value:{}", key, values);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
    }

    @Override
    public Double zScore(String key, String member) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zscore(key, member);
        } catch (JedisException e) {
            logger.error("'zScore' key fail, key: {},member:{}", key, member);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public List<String> zRangeByScore(String key, double min, double max) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zrangeByScore(key, min, max);
        } catch (JedisException e) {
            logger.error("'zRangeByScore' key fail, key: {},min:{},max:{}", key, min, max);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Long zRemRangeByScore(String key, double min, double max) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zremrangeByScore(key, min, max);
        } catch (JedisException e) {
            logger.error("'zremrangeByScore' key fail, key: {},min:{},max:{}", key, min, max);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }


    @Override
    public List<String> rangeByScore(String key, double min, double max, int offset, int count) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zrangeByScore(key, min, max, offset, count);
        } catch (JedisException e) {
            logger.error("'zRangeByScore' key fail, key: {},min:{},max:{},offset:{},count:{}", key, min, max, offset, count);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Long count(String key, double min, double max) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zlexcount(key, min + "", max + "");
        } catch (JedisException e) {
            logger.error("'count' key fail, key: {},min:{},max:{}", key, min, max);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Long zRank(String key, String member) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zrank(key, member);
        } catch (JedisException e) {
            logger.error("'zRank' key fail, key: {},member:{}", key, member);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }

    @Override
    public Long zRevRank(String key, String member) {
        AbstractAssertUtils.notEmpty(key, message);

        Jedis jedis = null;
        try {
            jedis = jedisService.getJedis();
            return jedis.zrevrank(key, member);
        } catch (JedisException e) {
            logger.error("'zRank' key fail, key: {},member:{}", key, member);
            logger.error(e.getMessage(), e);
        } finally {
            close(jedis);
        }
        return null;
    }
}
