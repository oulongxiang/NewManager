package com.tul.manage.config;

import apps.commons.exception.ServiceException;
import apps.commons.tips.Tip;
import apps.commons.util.enums.BizExceptionEnum;
import cn.hutool.core.util.StrUtil;
import com.tul.manage.security.config.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * 全局异常拦截器
 *
 * @author zengyu
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Resource
    private JwtUtil jwtUtil;

    private static final Pattern PX  =Pattern.compile("");

    private static final  String REGEX = "8[0-9]{3}";

    /**
     * 处理自定义的业务异常
     *
     * @param req req
     * @param e   e
     * @return tip
     */
    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public Tip serviceExceptionHandler(HttpServletRequest req, HttpServletResponse res, ServiceException e) {
        e.printStackTrace();
        log.error("发生业务异常！原因是：" + getErrorMessage(e));
        if (e.getCode().matches(REGEX)) {
            res.setStatus(401);
        }
        return new Tip(e.getCode(), e.getMessage(), false);
    }

    /**
     * 身份认证异常
     *
     * @param req req
     * @param e   e
     * @return tip
     */
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public Tip internalAuthenticationServiceExceptionHandler(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) {
        e.printStackTrace();
        log.error("用户认证异常!原因是：" + getErrorMessage(e));
        return new Tip(BizExceptionEnum.TOKEN_USEPWD_ERROR);
    }

    /**
     * 异常处理
     *
     * @param req req
     * @param e   e
     * @return tip
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Tip exceptionHandler(HttpServletRequest req, HttpServletResponse res, Exception e) {
        e.printStackTrace();
        String stackTrace = ExceptionUtils.getStackTrace(e);
        log.error(getErrorMessage(e), getOneStackTraceElementAndOtherMessage(e,stackTrace));
        return new Tip(false, "运行异常！请联系集团信息中心!");
    }

    /**
     * 获取异常错误信息
     *
     * @param e 异常
     * @return 异常错误信息
     */
    private   String getErrorMessage(Exception e) {
        if (StrUtil.isBlank(e.getMessage())) {
            return e.toString();
        }
        return e.getMessage();
    }

    /**
     * 获取第一次出现异常的堆栈信息，因为日志需要记录出错的详细堆栈信息，不将这个信息传给logback的话，logback会将堆栈出错信息定死
     * 详细处理流程请查看 LogDbAppender
     *
     * @param e 异常
     * @return 第一次出现异常的堆栈信息
     */
    private List<Object> getOneStackTraceElementAndOtherMessage(Exception e, String stackTrace) {
        List<Object> result = new ArrayList<>();
        StackTraceElement stackTraceElement = null;
        if (e.getStackTrace().length > 0) {
            stackTraceElement = e.getStackTrace()[0];
        }
        result.add(stackTraceElement);
        result.add(getLoginUserId());
        result.add(stackTrace);
        return result;
    }



    private String getLoginUserId() {
        //如果能通过token获取到当前登录用户的话，将当前登录用户的userId也插入数据库
        try {
            return jwtUtil.getUserId();
        } catch (Exception e) {
            return null;
        }
    }
}
