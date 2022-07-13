package cn.vimor.toolkit.jedis.service.operations;

import redis.clients.jedis.resps.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * zset操作
 *
 * @author Jani
 * @date 2022/02/28
 */
public interface ZsetOperations {

    /**
     * 将所有给定member元素及其score值加入到有序集key中。(有序集长度无界)
     * 如果某个member已经是有序集的成员，那么更新这个member的score值，并通过重新插入这个member元素，来保证该member在正确的位置上。
     *
     * @param key    键
     * @param score  元素的分数
     * @param member 元素
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
     */
    long zAdd(String key, double score, String member);

    /**
     * 将"member元素及其score值"加入到有序集key中。(有序集长度无界)<br>
     *
     * @param key          键
     * @param scoreMembers {@literal <元素, 元素的分数>}的映射表
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
     */
    long zAdd(String key, Map<String, Double> scoreMembers);


    /**
     * 分数增量
     *
     * @param key    键
     * @param score  元素的分数
     * @param member 成员
     * @return double
     */
    double zIncrement(String key, double score, String member);

    /**
     * 根据key获取全部
     *
     * @param key 关键
     * @return {@link Set}<{@link String}>
     */
    List<String> zRange(String key);

    /**
     * 根据key获取从0-end的成员
     *
     * @param key 关键
     * @param end 结束
     * @return {@link List}<{@link String}>
     */
    List<String> zRange(String key, Long end);

    /**
     * 获取集合中下标为start-end的成员
     *
     * @param key   关键
     * @param start 开始
     * @param end   结束
     * @return {@link Set}<{@link String}>
     */
    List<String> zRange(String key, Long start, Long end);

    /**
     * 获取集合中下标为start-end的成员及分数
     *
     * @param key   关键
     * @param start 开始
     * @param end   结束
     * @return {@link List}<{@link Tuple}>
     */
    List<Tuple> zRangeWithScores(String key, Long start, Long end);

    /**
     * z分数范围
     *
     * @param key 关键
     * @param min 最小值
     * @param max 马克斯
     * @return {@link List}<{@link Tuple}>
     */
    List<Tuple> zRangeWithScores(String key, double min, double max);


    /**
     * z分数范围
     *
     * @param key   关键
     * @param min   最小值
     * @param max   马克斯
     * @param start 开始
     * @param end   结束
     * @return {@link List}<{@link Tuple}>
     */
    List<Tuple> zRangeWithScores(String key, double min, double max, Integer start, Integer end);


    /**
     * 排名
     *
     * @param key   关键
     * @param min   最小值
     * @param max   马克斯
     * @param start 开始
     * @param end   结束
     * @return {@link List}<{@link Tuple}>
     */
    List<Tuple> zRevRangeWithScores(String key, double max, double min, Integer start, Integer end);

    /**
     * 移除集合中指定的成员
     *
     * @param key   关键
     * @param value 价值
     */
    void zRem(String key, String value);

    /**
     * 移除集合中指定的成员，可以指定多个成员。
     *
     * @param key    关键
     * @param values 值
     */
    void zRem(String key, Set<String> values);


    /**
     * 返回指定成员的分数
     *
     * @param key    key
     * @param member 成员
     * @return {@link Double}
     */
    Double zScore(String key, String member);

    /**
     * 返回分数在[min,max]的成员并按照分数从低到高排序
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return {@link List}<{@link String}>
     */
    List<String> zRangeByScore(String key, double min, double max);

    /**
     * 用于移除有序集中，指定分数（score）区间内的所有成员
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return {@link Long}
     */
    Long zRemRangeByScore(String key, double min, double max);

    /**
     * 返回分数在[min,max]的成员并按照分数从低到高排序。
     * offset，表明从脚标为offset的元素开始并返回count个成员（分页）。
     *
     * @param key    key
     * @param min    最小值
     * @param max    最大值
     * @param offset 脚标为offset的元素
     * @param count  count个成员
     * @return {@link List}<{@link String}>
     */
    List<String> rangeByScore(String key, double min, double max, int offset, int count);

    /**
     * 查询分数内数量
     *
     * @param key 关键
     * @param min 最小值
     * @param max 马克斯
     * @return {@link Long}
     */
    Long count(String key, double min, double max);


    /**
     * 获取成员排名(从小到大）
     *
     * @param key    关键
     * @param member 成员
     * @return {@link Long}
     */
    Long zRank(String key, String member);

    /**
     * 获取成员排名(从大到小）
     *
     * @param key    关键
     * @param member 成员
     * @return {@link Long}
     */
    Long zRevRank(String key, String member);
}
