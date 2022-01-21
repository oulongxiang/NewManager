package apps.commons.util.tool_util;


import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author YJH
 * @date 2019/12/26 20:01
 */
public class StreamUtil {

    /**
     * 映射List数组
     *
     * @param data 不能为空
     * @param fun
     * @param      <T>
     * @param      <R>
     * @return data为空抛出异常IllegalArgumentException
     */
    public static <T, R> List<R> map(List<T> data, Function<T, R> fun) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        return data.stream().map(fun).collect(Collectors.toList());
    }

    /**
     * 映射Set数组，不允许重复值，有顺序
     *
     * @param data 不能为空
     * @param fun
     * @param      <T>
     * @param      <R>
     * @return data为空抛出异常IllegalArgumentException
     */
    public static <T, R> Set<R> mapSet(List<T> data, Function<T, R> fun) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        return data.stream().map(fun).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * List<Bean>对象转Map<Key,Value>
     *
     * @param data
     * @param keyFun
     * @param valueFun
     * @return
     */
    public static <T, R, E> Map<R, E> map(List<T> data, Function<T, R> keyFun, Function<T, E> valueFun) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        return data.stream().collect(Collectors.toMap(keyFun, valueFun));
    }

    /**
     * 过滤
     *
     * @param data
     * @param pre
     * @param      <T>
     * @return data为空返回data
     */
    public static <T> List<T> filter(List<T> data, Predicate<T> pre) {
        if (CollectionUtils.isEmpty(data)) {
            return data;
        }
        return data.stream().filter(pre).collect(Collectors.toList());
    }

    /**
     * 排序
     *
     * @param data
     * @param comparator
     * @param            <T>
     * @return data为空返回data
     */
    public static <T> List<T> sorted(List<T> data, Comparator<T> comparator) {
        if (CollectionUtils.isEmpty(data)) {
            return data;
        }
        return data.stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * 去重
     *
     * @param data
     * @param      <T>
     * @return data为空返回data
     */
    public static <T> List<T> distinct(List<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            return data;
        }
        return data.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 分组,有顺序
     * @param <T>
     * @param <R>
     * @param data
     * @param fun
     * @return data为空返回空
     */
    public static <T, R> Map<R, List<T>> groupingBy(List<T> data, Function<T, R> fun) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<R, List<T>>();
        }
        return data.stream().collect(Collectors.groupingBy(fun, LinkedHashMap::new, Collectors.toList()));
    }

    /**
     * 判断是否包含匹配元素
     *
     * @param data
     * @param pre
     * @param      <T>
     * @return data为空返回false
     */
    public static <T> boolean anyMatch(List<T> data, Predicate<T> pre) {
        if (CollectionUtils.isEmpty(data)) {
            return false;
        }
        return data.parallelStream().anyMatch(pre);
    }

    /**
     * 将list进行join操作
     *
     * @param data
     * @param join
     * @return 返回join之后的字符串,data为空返回null
     */
    public static String join(List<String> data, String join) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        return data.stream().collect(Collectors.joining(join == null ? "" : join));
    }

}
