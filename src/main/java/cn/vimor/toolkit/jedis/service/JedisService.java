package cn.vimor.toolkit.jedis.service;


import cn.vimor.toolkit.jedis.service.operations.*;

/**
 * Jedis服务
 *
 * @author Jani
 * @date 2022/02/28
 */
public interface JedisService extends BaseJedisService {

    /**
     * String
     *
     * @return {@link ValueOperations}
     */
    ValueOperations opsForValue();


    /**
     * hashmap
     *
     * @return {@link HashOperations}
     */
    HashOperations opsForHash();

    /**
     * list
     *
     * @return {@link ListOperations}
     */
    ListOperations opsForList();

    /**
     * set
     *
     * @return {@link SetOperations}
     */
    SetOperations opsForSet();

    /**
     * zset
     *
     * @return {@link ZsetOperations}
     */
    ZsetOperations opsForZset();
}
