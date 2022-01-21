package apps.commons.util.tool_util;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

/**
 * 数据分页工具类
 *
 * @author Administrator
 */
public class PageLimitSQLUtil {

    /**
     * 生成分页的 Limit 语句
     *
     * @param page 分页对象
     * @return
     */
    public static <T> String createLimitSQL(IPage<T> page) {
        StringBuffer sb = new StringBuffer();
        if (ToolUtil.isNotEmpty(page)) {
            long size = page.getSize();
            long pages = page.getCurrent();
            sb.append(" limit ");
            sb.append(size * (pages - 1));
            sb.append(",");
            sb.append(size);
        } else {
            sb.append(" limit 0,15");
        }
        return sb.toString();
    }

    /**
     * 活动分页的开始结束数字
     *
     * @param page 分页对象
     * @return
     */
    public static <T> void setPageStartAndEndNumber(IPage<T> page, Map<String, Object> conds) {
        if (ToolUtil.isNotEmpty(page)) {
            long size = page.getSize();
            long pages = page.getCurrent();
            conds.put("start", size * (pages - 1));
            conds.put("end", size);
        } else {
            conds.put("start", 0);
            conds.put("end", 15);
        }
    }

}
