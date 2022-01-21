package com.tul.manage.security.config;


import com.tul.manage.security.filter.ExceptionHandlerFilter;
import com.tul.manage.security.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

/**
 * @description: SpringSecurity配置类
 * @author: znegyu
 * @create: 2020-12-08 09:12
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 忽略权限的资源
     */
    private static final String[] AUTH_LIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/doc.html",
            "/druid/**",
    };

    /**
     * 忽略权限的接口
     */
    private static final String[] API_LIST = {
            "/admin/invoiceRecords/**",
            "/admin/phoneOrders/**",
            "/admin/user/login",
            "/admin/user/loginToSso",
            "/admin/user/getUserInfo",
            "/admin/user/getOrganizationTree",
            "/admin/user/logout",
            "/admin/user/getUserLeaderId",
            "/admin/bind/**",
            "/admin/test/**",
            "/admin/riskInfo/getNotHandleRisk",
            "/admin/riskInfo/getInHandleRisk",
            "/admin/warningRules/getUserList",
            "/admin/dict/getDictListToType",
            "/admin/permission/getTopMenu",
            "/admin/permission/getLeftMenu",
    };

    @Resource
    private JwtFilter jwtFilter;
    @Resource
    private ExceptionHandlerFilter exceptionHandlerFilter;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //跨域配置
                .cors().and()
                //添加jwt过滤器
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                //添加异常铺抓Failure为首个Failure,这样才能捕获所有的Failure链里的异常
                .addFilterBefore(exceptionHandlerFilter, jwtFilter.getClass())
                .csrf().disable()
                //所有请求都要认证
                .authorizeRequests()
                //需要放开的接口和资源
                .antMatchers(API_LIST).permitAll()
                .antMatchers(AUTH_LIST).permitAll()
                //自定义授权规则
                .anyRequest().access("@rbacService.hasPermission(request,authentication)").and()
                .exceptionHandling().authenticationEntryPoint(defaultAuthenticationEntryPoint).accessDeniedHandler(defaultAuthenticationEntryPoint)
                //去除session和默认注销操作
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().logout().disable();
    }


    /**
     * 密码匹配
     *
     * @param auth auth
     * @throws Exception e
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     * 认证管理器用于自定义认证
     *
     * @return authenticationManager
     * @throws Exception e
     */
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 跨域配置
     *
     * @return corsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
