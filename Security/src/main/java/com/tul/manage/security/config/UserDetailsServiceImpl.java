package com.tul.manage.security.config;


import apps.commons.exception.ServiceException;
import apps.commons.util.enums.BizExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tul.manage.security.dao.PermissionDao;
import com.tul.manage.security.dao.RoleDao;
import com.tul.manage.security.dao.UserInfoDao;
import com.tul.manage.security.entity.Role;
import com.tul.manage.security.entity.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 处理用户登录认证逻辑
 * @author: znegyu
 * @create: 2020-12-08 10:38
 **/
@Component("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private PermissionDao permissionDao;

    @Value("${server.servlet.context-path}")
    public String apiPrefix;

    /**
     * 通过账号从数据库查询出当前登录用户的信息,返回一个UserDetails
     * (Spring-Security需要根据这个返回的对象来判断用户是否登录成功!)
     * 将认证授权信息加入Redis缓存,减少重复访问数据库次数
     *
     * @param userId -登录用户id
     * @return -返回一个UserDetails对象
     * @throws UsernameNotFoundException -用户不存在
     */
    @Override
    @Cacheable(value = "UserDetailsCacheInAuditSystem", key = "#userId")
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //1.获取用户信息
        if (userId == null || "".equals(userId.trim())) {
            throw new UsernameNotFoundException("获取用户信息失败!");
        }
        UserInfo userInfo =userInfoDao.selectById(userId);;
        if (userInfo == null) {
            throw new ServiceException(BizExceptionEnum.NOT_USER);
        }
        //2.获取用户角色信息
        List<Role> roleList = roleDao.getRoleListInUserId(userId);
        //角色也是一种特殊的权限
        List<String> allPermission = roleList.stream().map(t -> "ROLE_" + t.getRoleCode()).collect(Collectors.toList());
        //3.获取用户接口权限列表
        List<String> permissionList = permissionDao.getUserAllApiPermission(roleDao.isAdminToUserId(userId) ? null : userId);
        permissionList = permissionList.stream().map(t -> apiPrefix + t).collect(Collectors.toList());
        //角色权限和普通权限合并
        allPermission.addAll(permissionList);
        //返回用户信息和用户权限合集给security管理
        return new LoginUserDetails(userInfo, AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", allPermission)));
    }
}
