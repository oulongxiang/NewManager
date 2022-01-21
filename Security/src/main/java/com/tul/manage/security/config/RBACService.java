package com.tul.manage.security.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 对象功能 权限设计模型业务层
 * 开发人员：曾煜
 * 创建时间：2020/12/10 21:01
 * </pre>
 **/
@Component("rbacService")
public class RBACService {
    /**
     * 自定义权限匹配规则
     *
     * @param request        -当前请求
     * @param authentication -已认证的主体信息
     * @return -可访问 true 不可访问 false
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        //当前已认证的用户主体
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String uri = request.getRequestURI();
            //判断是否超级管理员，超级管理员不需要校验
            if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_admin"))) {
                return true;
            }
            //需要将请求uri和UserDetails中的权限集合做比对,因此需要封装为相同类型进行对比
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(uri);
            return userDetails.getAuthorities().contains(simpleGrantedAuthority);
        }
        return false;
    }
}
