package com.tul.manage.security.config;

import com.tul.manage.security.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @description: 用户凭证等信息（用于提供Spring Security当前登录的用户信息）
 * @author: znegyu
 * @create: 2020-12-08 09:41
 **/
public class LoginUserDetails implements UserDetails {

    /**
     * 用户的基本信息
     */
    private final UserInfo userInfo;

    /**
     * 用户拥有的权限列表
     */
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * 构造函数
     * @param userInfo -用户基本信息
     * @param authorities -用户拥有的权限列表
     */
    public LoginUserDetails(UserInfo userInfo, Collection<? extends GrantedAuthority> authorities){
        this.userInfo=userInfo;
        this.authorities=authorities;
    }

    /**
     * 账户是否未过期，过期无法验证。(这里直接返回true,不用校验,项目用不上)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁，锁定的用户无法进行身份验证
     * <p>
     * 密码锁定
     * </p>
     */
    @Override
    public boolean isAccountNonLocked() {
        return !userInfo.getIsLock();
    }


    /**
     * 指示是否已过期的用户的凭据(密码)，过期的凭据防止认证。(这里直接返回true,不用校验,项目用不上)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否被启用或禁用。禁用的用户无法进行身份验证。(这里直接返回true,不用校验,项目用不上)
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userInfo.getLoginPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getId();
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
