package cn.vimor.toolkit.jedis.util;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 断言跑龙套
 * 断言工具类，协助验证参数。<br>
 * 有助于在运行时的早期就明确地确定编程错误。
 * <p>
 * Assertion utility class that assists in validating arguments.<br>
 * Useful for identifying programmer errors early and clearly at runtime.
 * <p>
 * <font color="red">#ThreadSafe# (线程安全)</font>
 *
 * @author Jani
 * @date 2022/02/28
 * @see org.springframework.util.Assert
 */
public abstract class AbstractAssertUtils {

    /**
     * Assert a boolean expression, throwing {@code IllegalArgumentException} if the test result is {@code false}.
     *
     * <pre>
     * AssertUtils.isTrue(i > 0, "'i' must be greater than 0");
     * </pre>
     *
     * @param expression a boolean expression
     * @param message    the exception message to use if the assertion fails
     * @throws IllegalArgumentException if expression is {@code false}
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    // Empty checks (空检查)
    // -----------------------------------------------------------------------

    /**
     * Assert that a {@link String} is not empty; that is, it must not be {@code null} and not the empty String.
     *
     * <pre>
     * AssertUtils.notEmpty(name, "'name' must not be empty");
     * </pre>
     *
     * @param str     the String to check
     * @param message the exception message to use if the assertion fails
     */
    public static void notEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be {@code null} and must have at least one element.
     *
     * <pre>
     * AssertUtils.notEmpty(collection, "'collection' must have elements");
     * </pre>
     *
     * @param coll    the collection to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(Collection<?> coll, String message) {
        if (CollectionUtils.isEmpty(coll)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that a {@link Map} has entries; that is, it must not be {@code null} and must have at least one entry.
     *
     * <pre>
     * AssertUtils.notEmpty(map, "'map' must have entries");
     * </pre>
     *
     * @param map     the map to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the map is {@code null} or has no entries
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (ObjectUtils.isEmpty(map)) {
            throw new IllegalArgumentException(message);
        }
    }
}
