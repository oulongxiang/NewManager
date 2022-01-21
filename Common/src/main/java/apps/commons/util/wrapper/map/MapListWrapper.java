package apps.commons.util.wrapper.map;


import java.util.List;

/**
 * <pre>
 * 对象功能：Map List构建器
 * 开发人员：曾煜
 * 创建时间：2021/3/20 19:26
 * </pre>
 *
 * @author zengyu
 */

public class MapListWrapper<T> extends AbstractWrapper<T, TypeFunction<T, ?>, MapListWrapper<T>> {

    public MapListWrapper(List<T> data) {
        super(data);
    }

}
