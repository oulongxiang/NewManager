package com.tul.manage.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.entity.Role;
import com.tul.manage.security.vo.request.OrgUserRoleVo;
import com.tul.manage.security.vo.request.RoleVo;
import com.tul.manage.security.vo.request.UserRoleVo;
import com.tul.manage.security.vo.response.UserRoleListVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
public interface IRoleService extends IService<Role> {
    /**
     * 添加角色
     * @param role 所需信息
     */
    void addRole(RoleVo role);


    /**
     * 获取角色列表分页信息
     * @param roleName 要模糊查询的角色名
     * @return 分页后得角色信息
     */
    IPage<Role> getRoleList(String roleName);

    /**
     * 通过id删除角色
     * @param id 角色id
     */
    void delRole(String id);


    /**
     * 更新角色信息
     * @param role 所需信息
     */
    void updateRole(Role role);

    /**
     * 给指定用户添加角色
     * @param userRoleVo 所需信息
     */
    void addUserToRole(UserRoleVo userRoleVo);

    /**
     * 给指定组织下的所有人员添加角色
     * @param orgUserRoleVo 所需信息
     */
    void addOrgUserListToRole(OrgUserRoleVo orgUserRoleVo);

    /**
     * 移除用户的某个角色
     * @param userRoleVo 所需信息
     */
    void delUserRole(UserRoleVo userRoleVo);

    /**
     * 通过用户id获取关联的角色信息
     * @param userId 用户id
     * @return 关联的角色信息列表
     */
    List<UserRoleListVo> getRoleListToUser(String userId);

    /**
     * 获取所有角色列表
     * @return 所有角色列表
     */
    List<UserRoleListVo> getAllRoleList();


    /**
     * 获取所有角色
     * @return 角色列表
     */
    List<Map<String,Object>> bindRole();

}
