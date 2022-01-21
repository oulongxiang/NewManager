package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tul.manage.security.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Mapper
@DS("Admin")
public interface PermissionDao extends BaseMapper<Permission> {

    /**
     * 获取该用户所有可请求的接口URI (用于接口权限校验)
     *
     * @param userId -用户id
     * @return -可请求的接口URI列表
     */
    List<String> getUserAllApiPermission(String userId);

    /**
     * 判断角色是否有此菜单权限
     * @param roleId 角色id
     * @param menuId 菜单id
     * @return true-有 false-无
     */
    Boolean hasPermissionToRoleMenu(@Param("roleId") String roleId, @Param("menuId")String menuId);

}