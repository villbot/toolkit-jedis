package cn.vimor.toolkit.jedis.util;

import java.util.List;
import java.util.Set;

/**
 * 字符串数组工具类
 *
 * @author Jani
 * @date 2022/01/17
 */
public class StringArrayUtil {

    public static String[] toArray(List<String> list) {
        String[] strings = new String[list.size()];
        strings = list.toArray(strings);
        return strings;
    }

    public static String[] toArray(Set<String> set) {
        String[] strings = new String[set.size()];
        strings = set.toArray(strings);
        return strings;
    }
}
