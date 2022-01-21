package com.tul.manage.security.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tul.manage.security.entity.Role;
import com.tul.manage.security.vo.response.UserRoleListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Mapper
@DS("Admin")
public interface RoleDao extends BaseMapper<Role> {
    /**
     * 通过用户id获取用户角色
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<Role> getRoleListInUserId(String userId);

    /**
     * 通过用户Id判断该用户是否超级管理员
     *
     * @param userId 用户Id
     * @return false-不是 true-是
     */
    Boolean isAdminToUserId(String userId);

    /**
     * 获取已分配的角色列表
     *
     * @return 已分配的角色列表
     */
    List<Role> getAllocatedRoleList();

    /**
     * 获取用户角色关系
     *
     * @return 用户角色关系
     */
    List<Map<String, String>> getUserRole();

    /**
     * 通过用户id获取关联的角色信息
     * @param userId 用户id
     * @return 关联角色信息列表
     */
    List<UserRoleListVo> getRoleListToUser(@Param("userId") String userId);


    /**
     * 获取所有角色列表
     * @return 所有角色列表
     */
    List<UserRoleListVo> getAllRoleList();


}