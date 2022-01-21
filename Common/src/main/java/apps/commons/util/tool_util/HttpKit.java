package apps.commons.util.tool_util;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * Http工具类，用于处理request/response/session以及获取请求内容（参数/IP/设备/浏览器等）
 *
 * @author ZJL
 * @date 2019年9月18日 下午8:28:12
 */
public class HttpKit {

    /**
     * 获取 HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return null;
        }
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return null;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        if (getRequest() == null) {
            return null;
        }
        return getRequest().getSession();
    }

    /**
     * 获取指定的sessionKey
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttr(String key) {
        HttpSession session = getSession();
        return session != null ? (T) session.getAttribute(key) : null;
    }

    /**
     * 设置指定的sessionKey的值
     */
    public static void setSessionAttr(String key, Object value) {
        HttpSession session = getSession();
        if (session != null) {
            session.setAttribute(key, value);
        }
    }

    /**
     * 移除指定的sessionKey
     */
    public static void removeSessionAttr(String key) {
        HttpSession session = getSession();
        if (session != null) {
            session.removeAttribute(key);
        }
    }

    /**
     * 销毁session
     */
    public static void invalidateSession() {
        HttpSession session = getSession();
        if (session != null) {
            session.setMaxInactiveInterval(1);
        }
    }


    /**
     * 获取请求IP
     */
    public static String getIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String ip = request.getHeader("X-Real-IP");
        String xfor = request.getHeader("X-Forwarded-For");
        if (ToolUtil.isNotEmpty(xfor) && !"unKnown".equalsIgnoreCase(xfor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xfor.indexOf(",");
            if (index != -1) {
                return xfor.substring(0, index);
            } else {
                return xfor;
            }
        }
        xfor = ip;
        if (StringUtils.isNotEmpty(xfor) && !"unKnown".equalsIgnoreCase(xfor)) {
            return xfor;
        }
        if (ToolUtil.isEmpty(xfor) || "unknown".equalsIgnoreCase(xfor)) {
            xfor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
            xfor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
            xfor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
            xfor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(xfor) || "unknown".equalsIgnoreCase(xfor)) {
            xfor = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(xfor) ? "127.0.0.1" : xfor;

    }

    /**
     * 获取请求类型
     */
    public static String getMethod(HttpServletRequest request) {
        return request != null ? request.getMethod() : null;
    }

    /**
     * 获取请求URI
     */
    public static String getRequestURI(HttpServletRequest request) {
        return request != null ? request.getRequestURI() : null;
    }

    /**
     * 获取请求的键值
     */
    public static Map<String, String> getRequestParameters(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        HashMap<String, String> values = new HashMap<>();
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = (String) enums.nextElement();
            String paramValue = request.getParameter(paramName);
            values.put(paramName, paramValue);
        }
        return values;
    }

    /**
     * 获取请求的键值
     */
    public static String[] getParameterValues(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        List<String> values = new ArrayList<>();
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paramName = (String) enums.nextElement();
            String paramValue = request.getParameter(paramName);
            values.add(paramValue);
        }
        return values.toArray(new String[values.size()]);
    }


    /**
     * 获取用户代理String类型
     */
    public static String getUserAgentString(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return request.getHeader("User-Agent");
    }

    /**
     * 获取用户代理对象
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return UserAgent.parseUserAgentString(getUserAgentString(request));
    }

    /**
     * 获取用户代理对象
     */
    public static UserAgent getUserAgent(String userAgentString) {
        if (userAgentString == null) {
            return null;
        }
        return UserAgent.parseUserAgentString(userAgentString);
    }

    /**
     * 获取设备类型
     */
    public static DeviceType getDeviceType(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return getUserAgent(request).getOperatingSystem().getDeviceType();
    }

    /**
     * 获取设备类型
     */
    public static DeviceType getDeviceType(UserAgent userAgent) {
        if (userAgent == null) {
            return null;
        }
        return userAgent.getOperatingSystem().getDeviceType();
    }

    /**
     * 是否是PC
     */
    public static boolean isComputer(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        return DeviceType.COMPUTER.equals(getDeviceType(request));
    }

    /**
     * 是否是手机
     */
    public static boolean isMobile(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        return DeviceType.MOBILE.equals(getDeviceType(request));
    }

    /**
     * 是否是平板
     */
    public static boolean isTablet(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        return DeviceType.TABLET.equals(getDeviceType(request));
    }

    /**
     * 是否是手机和平板
     */
    public static boolean isMobileOrTablet(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        DeviceType deviceType = getDeviceType(request);
        return DeviceType.MOBILE.equals(deviceType) || DeviceType.TABLET.equals(deviceType);
    }

    /**
     * 获取浏览类型
     */
    public static Browser getBrowser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return getUserAgent(request).getBrowser();
    }

    /**
     * 获取请求IP
     */
    public static String getIp() {
        return getIp(getRequest());
    }

    /**
     * 获取请求类型
     */
    public static String getMethod() {
        return getMethod(getRequest());
    }

    /**
     * 获取请求URI
     */
    public static String getRequestURI() {
        return getRequestURI(getRequest());
    }

    /**
     * 获取所有请求的值
     */
    public static Map<String, String> getRequestParameters() {
        return getRequestParameters(getRequest());
    }

    /**
     * 获取用户代理String
     *
     * @param request
     * @return
     */
    public static String getUserAgentString() {
        return getUserAgentString(getRequest());
    }

    /**
     * 获取用户代理对象
     *
     * @param request
     * @return
     */
    public static UserAgent getUserAgent() {
        return getUserAgent(getRequest());
    }

    /**
     * 获取设备类型
     *
     * @param request
     * @return
     */
    public static DeviceType getDeviceType() {
        return getDeviceType(getRequest());
    }

    /**
     * 是否是PC
     *
     * @param request
     * @return
     */
    public static boolean isComputer() {
        return isComputer(getRequest());
    }

    /**
     * 是否是手机
     *
     * @param request
     * @return
     */
    public static boolean isMobile() {
        return isMobile(getRequest());
    }

    /**
     * 是否是平板
     *
     * @param request
     * @return
     */
    public static boolean isTablet() {
        return isTablet(getRequest());
    }

    /**
     * 是否是手机和平板
     *
     * @param request
     * @return
     */
    public static boolean isMobileOrTablet() {
        return isMobileOrTablet(getRequest());
    }

    /**
     * 获取浏览类型
     *
     * @param request
     * @return
     */
    public static Browser getBrowser() {
        return getBrowser(getRequest());
    }

    /**
     * 将请求的所有参数设置到一个Map中返回
     *
     * @param request
     * @return
     */
    public static Map<String, Object> dealRequest() {
        return dealRequest(getRequest());
    }

    /**
     * 将请求的所有参数设置到一个Map中返回
     *
     * @param request
     * @return
     */
    public static Map<String, Object> dealRequest(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, String[]> keys = request.getParameterMap();
        Set<String> keySet = keys.keySet();
        for (Object object : keySet) {
            map.put(object.toString(), request.getParameter(object.toString()));
        }
        return map;
    }

    /**
     * 封装用户设备信息对象
     *
     * @return UserAgentMessage
     */
    public static UserAgentMessage getUserAgentMessage() {
        return getUserAgentMessage(getRequest());
    }

    /**
     * 封装用户设备信息对象
     *
     * @param request
     * @return UserAgentMessage
     */
    public static UserAgentMessage getUserAgentMessage(HttpServletRequest request) {
        UserAgentMessage userAgentMessage = new UserAgentMessage();
        if (request != null) {
            //获取请求url
            String uri = HttpKit.getRequestURI(request);
            //获取请求ip
            String ip = HttpKit.getIp(request);
            //获取用户设备
            String deviceType = HttpKit.getDeviceType(request).toString();
            //获取浏览器内核
            String browser = HttpKit.getBrowser(request).toString();
            //获取代理信息
            String userAgent = HttpKit.getUserAgentString(request);
            //获取请求参数
            Map<String, String> map = HttpKit.getRequestParameters(request);
            String params = "";
            if (map != null && !map.isEmpty()) {
                String.valueOf(map);
            }
            //设值
            userAgentMessage.setUri(uri);
            userAgentMessage.setIp(ip);
            userAgentMessage.setDeviceType(deviceType);
            userAgentMessage.setBrowser(browser);
            userAgentMessage.setUserAgent(userAgent);
            userAgentMessage.setParams(params);
        }
        return userAgentMessage;
    }

    /**
     * 判断来源哪个应用端，0：门户 1：PC 2：安卓 3 ：后台管理 4：分析平台
     */
    public static Integer getFromtype() {
        HttpServletRequest request = HttpKit.getRequest();
        if (request == null) {
            return null;
        }
        Integer fromtype = null;
        String referer = request.getHeader("Referer");
        if (ToolUtil.isNotEmpty(referer)) {
            if (referer.contains("/custom-pc") || referer.contains("/homework-pc")) {
                fromtype = 1;
            } else if (referer.contains("/custom-app") || referer.contains("/homework-app")) {
                fromtype = 2;
            }
        }
        String uri = request.getRequestURI();
        if (ToolUtil.isNotEmpty(uri)) {
            if (uri.contains("/statistics")) {
                fromtype = 4;
            }
        }
        return fromtype;
    }

    /**
     * @description 请求记录写入日志
     * @author yangxianghua  2020年10月30日 上午09:34:09 周五
     */
    public static StringBuilder getRequestRecordInfo(String statusCode) {
        HttpServletRequest request = getRequest();
        //打印请求的内容
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));//获取请求头中的User-Agent
        return getStringBuilder(statusCode, request, userAgent);
    }

    private static StringBuilder getStringBuilder(String statusCode, HttpServletRequest request, UserAgent userAgent) {
        StringBuilder info = new StringBuilder();
        info.append("[")
                .append(request.getMethod())
                .append("] ")
                .append(request.getRemoteAddr())
                .append(" ")
                .append(statusCode)
                .append(" ")
                .append(request.getRequestURI())
                .append(" ")
                .append(userAgent.getBrowser().toString())
                .append(" ")
                .append(userAgent.getBrowserVersion())
                .append(" ")
                .append(userAgent.getOperatingSystem().toString())
                .append(" ");
        return info;
    }


    public static StringBuilder getRequestRecordInfo(UserAgent userAgent,HttpServletRequest request,String statusCode) {
        return getStringBuilder(statusCode, request, userAgent);
    }

}
