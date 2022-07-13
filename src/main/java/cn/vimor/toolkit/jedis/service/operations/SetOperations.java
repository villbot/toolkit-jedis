package cn.vimor.toolkit.jedis.service.operations;


import java.util.Set;

/**
 * 集合操作
 *
 * @author Jani
 * @date 2022/02/28
 */
public interface SetOperations {

    /**
     * 批量向set中添加数据，如果该key的值已有则不会重复添加.
     *
     * @param key    key
     * @param values 集
     */
    void batchAdd(String key, Set<String> values);

    /**
     * 添加
     *
     * @param key      key
     * @param setValue 设置值
     */
    void add(String key, String setValue);

    /**
     * 获取set中所有的成员
     *
     * @param key key
     * @return {@link Set}<{@link String}>
     */
    Set<String> members(String key);

    /**
     * 获取set中成员的数量
     *
     * @param key key
     * @return {@link Long}
     */
    Long sCard(String key);

    /**
     * 判断参数中指定的成员是否在该set中
     *
     * @param key    key
     * @param member 成员
     * @return {@link Boolean}
     */
    Boolean isMember(String key, String member);

    /**
     * 删除set中指定的成员
     *
     * @param key    key
     * @param member 成员
     */
    void sRem(String key, String... member);

    /**
     * 随机返回set中的一个成员
     *
     * @param key key
     * @return {@link String}
     */
    String sRandMember(String key);
}
