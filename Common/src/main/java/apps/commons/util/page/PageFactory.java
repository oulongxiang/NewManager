package apps.commons.util.page;

/**
 * @author YJH
 * @date 2019/12/6 22:37
 */

import apps.commons.util.tool_util.HttpKit;
import apps.commons.util.tool_util.ToolUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;



/**
 * 获取分页条件封装分页对象
 *
 * @author ZJL
 * @date 2019年10月25日 下午6:17:45
 */
public class PageFactory<T> {

    /**
     * 每页个数
     */
    private final static String PAGE_SIZE = "pageSize";

    /**
     * 当前页数
     */
    private final static String CURRENT_PAGE = "currentPage";

    /**
     * 排序字段
     */
    private final static String SORT = "sort";

    /**
     * 排序类型 true-升序 false-降序
     */
    private final static String ORDER = "order";

    /**
     * 获取默认的分页排序信息
     *
     * @param <T> 泛型实体类
     * @return 分页信息
     */
    public static <T> Page<T> getDefaultPage() {
        HttpServletRequest request = HttpKit.getRequest();
        int pageSize = 10;
        int current = 1;
        assert request != null;
        if (request.getParameter(PAGE_SIZE) != null && !StrUtil.isBlank(request.getParameter(PAGE_SIZE))) {
            pageSize = Integer.parseInt(request.getParameter(PAGE_SIZE));
        }
        if (request.getParameter(CURRENT_PAGE) != null && !StrUtil.isBlank(request.getParameter(CURRENT_PAGE))) {
            current = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        }
        String sort = request.getParameter(SORT);
        //asc或desc(升序或降序)
        boolean order = Boolean.parseBoolean(request.getParameter(ORDER));
        if (current <= 0) {
            current = 1;
        }
        Page<T> page = new Page<>(current, pageSize);
        if (!ToolUtil.isEmpty(sort)) {
            if (!order) {
                page.setOrders(Collections.singletonList(OrderItem.desc(sort)));
            } else {
                page.setOrders(Collections.singletonList(OrderItem.asc(sort)));
            }
        }
        return page;
    }

}
