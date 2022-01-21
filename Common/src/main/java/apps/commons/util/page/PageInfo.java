package apps.commons.util.page;

/**
 * @author YJH
 * @date 2019/12/6 22:35
 */

import apps.commons.tips.Tip;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页结果的封装(for Bootstrap Table)
 *
 * @author YJH
 * @Date 2019年1月22日 下午11:06:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageInfo<T> extends Tip {

    private static final long serialVersionUID = 1L;

    private List<T> data;

    private Map<String,Object> page;

    public PageInfo(Page<T> page) {
        this.code = "200";
        Map<String,Object> pageMap=new HashMap<>();
        pageMap.put("total",page.getTotal());
        pageMap.put("currentPage",page.getCurrent());
        pageMap.put("pageSize",page.getSize());
        this.data = page.getRecords();
        this.success = true;
        this.page=pageMap;
    }

    public PageInfo(IPage<T> page) {
        this.code = "200";
        Map<String,Object> pageMap=new HashMap<>();
        pageMap.put("total",page.getTotal()); //总条目数
        pageMap.put("currentPage",page.getCurrent()); //当前页数
        pageMap.put("pageSize",page.getSize()); //每页显示条目个数
        this.data = page.getRecords();
        this.success = true;
        this.page=pageMap;
    }
}
