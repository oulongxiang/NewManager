package apps.commons.base;

import apps.commons.tips.ResponseTip;
import apps.commons.tips.SuccessTip;
import apps.commons.tips.Tip;
import apps.commons.util.enums.BizExceptionEnum;
import apps.commons.util.enums.JWTUtil;
import apps.commons.util.page.PageInfo;
import apps.commons.util.tool_util.HttpKit;
import apps.commons.util.tool_util.PojoUtil;
import apps.commons.util.tool_util.ToolUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 基础控制器，用于处理返回Tip和request/response/session
 *
 * @author ZJL
 * @date 2019年9月18日 下午8:36:35
 */
public class BaseController {

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
     * success 是否成功
     *
     * @param success
     * @return
     */
    protected Tip tip(boolean success) {
        return new Tip(success);
    }

    /**
     * success 是否成功
     *
     * @param success success成功
     * @param message 返回消息
     * @return
     */
    protected Tip errorTip(boolean success, String message) {
        return new Tip(success, message);
    }

    /**
     * 返回成功（不推荐使用，swagger不明结果集）
     *
     * @param args
     * @return
     */
    protected JSONObject successTip(Object... args) {
        SuccessTip successTip = new SuccessTip();
        return successTip.getSuccessTip(args);
    }

    protected <T> ResponseTip<T> successTip(T t) {
        ResponseTip<T> tip = new ResponseTip<T>(true);
        tip.setData(t);
        return tip;
    }

    protected Tip errorTip() {
        return new Tip(BizExceptionEnum.OPERATION_FAILED);
    }

    protected Tip errorTip(BizExceptionEnum bizException) {
        return new Tip(bizException);
    }

    protected String getUserId() {
        return JWTUtil.getUserId(HttpKit.getRequest());
    }

    /**
     * 获取 HttpServletRequest
     */
    protected HttpServletRequest getHttpServletRequest() {
        return HttpKit.getRequest();
    }

    /**
     * 获取 HttpServletResponse
     */
    protected HttpServletResponse getHttpServletResponse() {
        return HttpKit.getResponse();
    }

    /**
     * 将请求的所有参数设置到一个Map中返回
     */
    public Map<String, Object> dealRequest() {
        return HttpKit.dealRequest();
    }

    /**
     * 将请求的所有参数设置到一个Map中返回
     */
    public Map<String, Object> dealRequest(HttpServletRequest request) {
        return HttpKit.dealRequest(request);
    }

    /**
     * 获取session
     */
    protected HttpSession getSession() {
        return HttpKit.getSession();
    }

    /**
     * 获取指定的sessionKey
     */
    protected static <T> T getSessionAttr(String key) {
        return HttpKit.getSessionAttr(key);
    }

    /**
     * 设置指定的sessionKey的值
     */
    protected static void setSessionAttr(String key, Object value) {
        HttpKit.setSessionAttr(key, value);
    }

    /**
     * 移除指定的sessionKey
     */
    protected static void removeSessionAttr(String key) {
        HttpKit.removeSessionAttr(key);
    }

    /**
     * 销毁session
     */
    protected static void invalidateSession() {
        HttpKit.invalidateSession();
    }

    protected <T> PageInfo<T> packForBT(IPage<T> page) {
        return new PageInfo<T>(page);
    }

    /**
     * 把service层的分页信息，封装为bootstrap table通用的分页封装
     */
    protected <T> PageInfo<T> packForBT(Page<T> page) {
        return new PageInfo<T>(page);
    }

    /**
     * 删除cookie
     */
    protected void deleteCookieByName(String cookieName) {
        Cookie[] cookies = this.getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                Cookie temp = new Cookie(cookie.getName(), "");
                temp.setMaxAge(0);
                this.getHttpServletResponse().addCookie(temp);
            }
        }
    }

    /**
     * 删除所有cookie
     */
    protected void deleteAllCookie() {
        Cookie[] cookies = this.getHttpServletRequest().getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                Cookie temp = new Cookie(cookie.getName(), "");
                temp.setMaxAge(0);
                this.getHttpServletResponse().addCookie(temp);
            }
        }
    }

    /**
     * 功能描述: <br>
     * 从request获取非空参数并转化为Map集合<br>
     * 注意：只有表单方式传值才能获取到，一般用于获取数据的controller
     *
     * @param obj
     * @author yangxianghua  2021年03月06日 下午16:07:36 周六
     * @return: 公共参数类
     */
    protected <T> T getCommonParam(Class<T> obj) {
        List<String> pageInfoParam = new ArrayList<>();
        pageInfoParam.add(PAGE_SIZE);
        pageInfoParam.add(CURRENT_PAGE);
        pageInfoParam.add(SORT);
        pageInfoParam.add(ORDER);
        // 参数Map
        Map properties = getHttpServletRequest().getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String key = "";
        String value = "";
        // 迭代
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            key = entry.getKey().toString();
            // 如果key是分页参数，则直接返回
            if (pageInfoParam.contains(key)) {
                continue;
            }
            Object valueObj = entry.getValue();
            // 值为空的话就终止本次循环
            if (ToolUtil.isEmpty(valueObj)) {
                continue;
            } else if (valueObj instanceof String[]) {
                Object[] values = (Object[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(key, value);
        }

        return PojoUtil.objectToJavaObject(returnMap, obj);
    }
}

