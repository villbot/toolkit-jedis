package cn.vimor.toolkit.jedis.service.operations;

import java.util.List;

/**
 * 列表操作
 *
 * @author Jani
 * @date 2022/02/28
 */
public interface ListOperations {

    /**
     * 返回列表key的长度。
     * 如果key不存在，则key被解释为一个空列表，返回0。
     * 如果key不是列表类型，则返回一个错误。
     *
     * @param key 键
     * @return 列表key的长度。
     */
    long lLen(String key);

    /**
     * 将所有给定value插入到列表key的表头。
     * 如果key不存在，一个空列表会被创建并执行LPush操作。当key存在但不是列表类型时，会返回一个错误。
     *
     * @param key    键
     * @param values 字符串值列表(<key, value1, value2, ... , valueN>)
     * @return 执行LPush命令后，列表的长度。
     */
    long lPush(String key, String... values);

    /**
     * 移除并返回列表key的表尾元素。
     * 时间复杂度: O(1)
     *
     * @param key 键
     * @return 当列表不为空时，返回列表的表尾元素；当key不存在或列表为空时，返回null。
     */
    String rPop(String key);

    /**
     * 返回列表key中指定区间内的元素，区间由偏移量start和stop指定。
     * 下标参数start和stop都以0为基底，也就是说，0表示列表的第一个元素，1表示列表的第二个元素，以此类推。
     * 下标参数start和stop也可以是负数，-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推。
     *
     * @param key   键
     * @param start 起始下标
     * @param stop  结束下标
     * @return 一个列表，包含指定区间内的元素；如果指定区间不包含任何元素，则返回一个空列表。
     */
    List<String> lRange(String key, int start, int stop);

    /**
     * 对一个列表进行修剪，就是说，让列表只保留指定区间内的元素，不在指定区间内的元素都将被删除。<br>
     * 下标参数start和stop都是以0为基底，也就是说，0表示列表的第一个元素，1表示列表的第二个元素，以此类推。
     *
     * @param key   键
     * @param start 起始下标
     * @param stop  结束下标
     * @return 当命令执行成功时，返回OK。
     */
    String ltrim(String key, int start, int stop);

    /**
     * 根据key获取全部集合
     *
     * @param key 关键
     * @return {@link List}<{@link String}>
     */
    List<String> get(String key);

    /**
     * 获取链表中从0到end的元素的值，end可为负数，若为-1则表示链表尾部的元素，-2则表示倒数第二个，依次类推…
     *
     * @param key      关键
     * @param endRange 结束范围
     * @return {@link List}<{@link String}>
     */
    List<String> get(String key, Long endRange);

    /**
     * 获取链表中从start到end的元素的值，start、end可为负数，若为-1则表示链表尾部的元素，-2则表示倒数第二个，依次类推…
     *
     * @param key        关键
     * @param startRange 开始范围
     * @param endRange   结束范围
     * @return {@link List}<{@link String}>
     */
    List<String> get(String key, Long startRange, Long endRange);

    /**
     * 返回并删除指定key的链表中的第一个元素，即头元素。
     *
     * @param key 关键
     * @return {@link String}
     */
    String lPop(String key);

    /**
     * 删除count个值为value的元素，
     * 如果count大于0，从头向尾遍历并删除count个值为value的元素，
     * 如果count小于0，则从尾向头遍历并删除。如果count等于0，则删除链表中所有等于value的元素。
     *
     * @param key   关键
     * @param value 价值
     */
    void lRem(String key, String value);


    /**
     * 在key对应 该list的尾部添加元素
     *
     * @param key  key
     * @param item 项
     */
    void rPush(String key, String... item);

    /**
     * 在key对应 该list的尾部添加元素
     *
     * @param key  key
     * @param list 列表
     */
    void rPush(String key, List<String> list);
}
