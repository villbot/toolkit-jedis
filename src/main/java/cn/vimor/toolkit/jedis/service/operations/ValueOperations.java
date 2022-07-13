package cn.vimor.toolkit.jedis.service.operations;

import java.util.List;

/**
 * 值操作
 *
 * @author Jani
 * @date 2022/02/28
 */
public interface ValueOperations {

    /**
     * 返回key所关联的字符串值。
     * 如果key不存在，那么返回特殊值null； 如果key储存的值不是字符串类型，则返回一个错误，因为GET只能用于处理字符串值。
     *
     * @param key 键
     * @return 当key不存在时，返回null；否则，返回key的值。
     */
    String get(String key);


    /**
     * 将字符串值value关联到key。(key永不过期)<br>
     * 如果key已经持有其他值，SET就覆写旧值，无视类型。<br>
     * 对于某个原本带有生存时间(TTL)的key来说，当SET命令成功在这个key上执行时，这个key原有的TTL将被清除。
     *
     * @param key   键
     * @param value 字符串值
     * @return 当设置操作成功时，返回OK；否则，返回空批量回复(null)。因为该方法未使用SET命令的可选参数，所以总是会返回OK，不可能失败。
     */
    String set(String key, String value);

    /**
     * Redis Setbit 命令用于对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
     *
     * @param key    关键
     * @param offset 抵消
     * @param value  价值
     * @return {@link Boolean}
     */
    Boolean setBit(String key, long offset, boolean value);

    /**
     * Redis Getbit 命令用于对 key 所储存的字符串值，获取指定偏移量上的位(bit)。
     *
     * @param key    关键
     * @param offset 抵消
     * @return {@link Boolean}
     */
    Boolean getBit(String key, long offset);

    /**
     * 获取key对应的值的二进制表示中 1 的个数
     *
     * @param key 关键
     * @return {@link Long}
     */
    Long bitCount(String key);

    /**
     * 将字符串值value关联到key，并将key的生存时间设为seconds。<br>
     * 如果key已经存在，SetEX命令将覆写旧值。
     *
     * @param key     键
     * @param seconds 生存时间(秒数)
     * @param value   字符串值
     * @return 当设置操作成功时，返回OK；当seconds参数不合法(<= 0)时，返回{@code null}。
     */
    String setEx(String key, int seconds, String value);


    /**
     * 增量
     *
     * @param key key
     * @return {@link Long}
     */
    Long increment(String key);

    /**
     * 增量
     *
     * @param key   key
     * @param delta δ
     * @return {@link Long}
     */
    Long incrementBy(String key, long delta);

    /**
     * 增量浮动
     *
     * @param key   key
     * @param delta δ
     * @return {@link Double}
     */
    Double incrementByFloat(String key, double delta);

    /**
     * 多得到
     *
     * @param keys 键
     * @return {@link List}<{@link String}>
     */
    List<String> multiGet(List<String> keys);

    /**
     * 时得到
     *
     * @param keys 键
     * @return {@link List}<{@link String}>
     */
    List<String> piplineGet(List<String> keys);
}
