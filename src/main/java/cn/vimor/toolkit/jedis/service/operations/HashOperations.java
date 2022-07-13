package cn.vimor.toolkit.jedis.service.operations;

import java.util.Map;
import java.util.Set;

/**
 * 散列操作
 *
 * @author Jani
 * @date 2022/02/28
 */
public interface HashOperations {

    /**
     * 为指定的key设定field/value对（键值对）。
     *
     * @param key 关键
     * @param map 地图
     */
    void hSet(String key, Map<String, String> map);

    /**
     * 为指定的key设定field/value对（键值对）。
     *
     * @param key        key
     * @param filedKey   Map-key
     * @param filedValue 映射值
     */
    void hSet(String key, String filedKey, String filedValue);

    /**
     * 获取key中的全部filed的值
     *
     * @param key key
     * @return {@link Map}<{@link String}, {@link String}>
     */
    Map<String, String> hGetAll(String key);

    /**
     * 获取key中的多个filed的值
     *
     * @param key    key
     * @param keySet key集
     * @return {@link Map}<{@link String}, {@link String}>
     */
    Map<String, String> hGet(String key, Set<String> keySet);

    /**
     * 返回指定的key中的field的值
     *
     * @param key      key
     * @param filedKey map-key
     * @return {@link String}
     */
    String hGet(String key, String filedKey);

    /**
     * 判断指定的key中的filed是否存在
     *
     * @param key      key
     * @param filedKey 提起key
     * @return boolean
     */
    Boolean hExists(String key, String filedKey);

    /**
     * 用于删除哈希表 key 中的一个或多个指定字段，不存在的字段将被忽略。
     *
     * @param key   var1
     * @param field 字段
     * @return long
     */
    long hDel(String key, String... field);
}
