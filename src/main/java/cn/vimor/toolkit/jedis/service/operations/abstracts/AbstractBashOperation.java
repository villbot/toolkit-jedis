package cn.vimor.toolkit.jedis.service.operations.abstracts;

import redis.clients.jedis.Jedis;

/**
 * 操作
 *
 * @author Jani
 * @date 2022/02/28
 */
public abstract class AbstractBashOperation {

    /**
     * 关闭
     *
     * @param jedis Jedis
     */
    protected abstract void close(Jedis jedis);
}
