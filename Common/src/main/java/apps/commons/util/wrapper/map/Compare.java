package apps.commons.util.wrapper.map;

import java.io.Serializable;

/**
 * <pre>
 * 对象功能：定义功能接口
 * 开发人员：曾煜
 * 创建时间：2021/3/20 19:43
 * </pre>
 *
 * @author zengyu
 */
public interface Compare<Children, R> extends Serializable {

    /**
     * 添加指定属性给map
     *
     * @param column 返回属性值的方法
     * @return 构建后的链
     */
    default Children put(R column) {
        return this.put(column, null);
    }

    /**
     * 添加指定属性给map
     *
     * @param column 返回属性值的方法
     * @param key    map 的key值
     * @return 构建后的链
     */
    Children put(R column, String key);


    /**
     * 移除指定属性
     *
     * @param column 返回属性值的方法
     * @return 构建后的链
     */
    Children remove(R column);
}
