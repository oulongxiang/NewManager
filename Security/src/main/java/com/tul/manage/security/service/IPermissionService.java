package com.tul.manage.security.service;


import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tul.manage.security.entity.Permission;
import com.tul.manage.security.vo.request.RoleMenuVo;
import com.tul.manage.security.vo.request.RolePermissionVo;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
public interface IPermissionService extends IService<Permission> {
    /**
     * 根据角色id获取权限列表(菜单+控件元素)
     *
     * @param roleId 角色id
     * @return 权限列表(菜单 + 控件元素)
     */
    List<Tree<String>> getPermissionListToRoleId(String roleId);

    /**
     * 更新角色权限
     *
     * @param rolePermissionVo 勾选的权限和指定的角色id
     */
    void updateRolePermission(RolePermissionVo rolePermissionVo);

    /**
     * 给角色添加权限
     *
     * @param roleMenuVo 所需参数
     */
    void addRolePermission(RoleMenuVo roleMenuVo);

    /**
     * 删除菜单权限
     *
     * @param roleMenuVo 所需参数
     */
    void delRolePermission(RoleMenuVo roleMenuVo);

}
