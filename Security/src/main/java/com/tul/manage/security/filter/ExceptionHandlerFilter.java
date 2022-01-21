package com.tul.manage.security.filter;

import apps.commons.exception.ServiceException;
import apps.commons.tips.Tip;
import apps.commons.util.ServletUtils;
import com.tul.manage.security.config.DefaultAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * 对象功能 - Filter异常铺抓
 * 开发人员：曾煜
 * 创建时间：2020/12/11 23:09
 * </pre>
 *
 * @author zengyu
 */
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ServiceException e) {
            Tip tip = new Tip(false);
            tip.setMessage(e.getMessage());
            tip.setCode(e.getCode());
            ServletUtils.render(request, response, tip);
        } catch (DefaultAuthenticationException e) {
            ServletUtils.render(request, response, new Tip("401", e.getMessage(), false));
        } catch (Exception e) {
            ServletUtils.render(request, response, new Tip(false, e.getMessage()));
        }
    }
}
