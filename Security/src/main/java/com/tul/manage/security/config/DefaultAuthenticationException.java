package com.tul.manage.security.config;

import org.springframework.security.core.AuthenticationException;

/**
 * <pre>
 * 对象功能 自定义认证异常类
 * 开发人员：曾煜
 * 创建时间：2020/12/11 22:38
 * </pre>
 *
 * @author zengyu
 */
public class DefaultAuthenticationException extends AuthenticationException {
    public DefaultAuthenticationException(Integer code, String message) {
        super(message);
    }
}
