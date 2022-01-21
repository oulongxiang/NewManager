package com.tul.manage.security.filter;

import com.tul.manage.security.config.JwtUtil;
import com.tul.manage.security.dao.RoleDao;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: JWT请求过滤器
 * @author: znegyu
 * @create: 2020-12-11 10:11
 **/
@Component
public class JwtFilter extends OncePerRequestFilter {
    //继承OncePerRequestFilter来保证一次请求只通过一次filter，而不需要重复执行(为了兼容不同的web container)
    @Resource
    private JwtUtil jwtUtil;
    @Resource(name = "UserDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    @Resource
    private RoleDao roleDao;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //先获取请求头中的token
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken != null && !"".equals(jwtToken.trim())) {
            //校验token的正确性并获取用户唯一标识符(这里的唯一标识符就是用户的身份证号码)
            String userId = jwtUtil.verify(jwtToken);
            //SecurityContextHolder 中保存的是当前访问者的信息
            //登录用户没有认证时，先进行认证
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                //获取当前用户的信息
                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
                //授权
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
