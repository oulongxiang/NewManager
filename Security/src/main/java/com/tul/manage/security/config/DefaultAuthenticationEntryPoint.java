package com.tul.manage.security.config;

import apps.commons.tips.Tip;
import apps.commons.util.ServletUtils;
import apps.commons.util.enums.BizExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证异常和权限异常
 *
 * @author zengyu
 */
@Component
@Slf4j
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.info(request.getRequestURL() + "用户认证失败(" + e.getLocalizedMessage() + ")");
        ServletUtils.render(request, response, new Tip(BizExceptionEnum.USER_AUTHENTICATION_ERROR));
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        log.info(request.getRequestURL() + "没有权限(" + e.getLocalizedMessage() + ")");
        ServletUtils.render(request, response, new Tip(BizExceptionEnum.ACT_AUTH_ERROR));
    }


}