package com.tul.manage.security.controller;

import apps.commons.base.BaseController;
import apps.commons.tips.ResponseTip;
import apps.commons.tips.Tip;
import cn.hutool.core.lang.tree.Tree;
import com.tul.manage.security.config.IgnoreOperationLog;
import com.tul.manage.security.service.IMenuService;
import com.tul.manage.security.service.IPermissionService;
import com.tul.manage.security.vo.request.RoleMenuVo;
import com.tul.manage.security.vo.request.RolePermissionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description: 权限相关接口
 * @author: znegyu
 * @create: 2021-01-13 09:46
 **/
@Api(value = "权限相关接口", tags = "权限接口")
@RequestMapping("/admin/permission")
@RestController
public class PermissionController extends BaseController {

    @Resource
    private IMenuService menuService;
    @Resource
    private IPermissionService permissionService;

    @IgnoreOperationLog
    @ApiOperation("获取用户左侧菜单(路由+权限)")
    @GetMapping("/getLeftMenu")
    public ResponseTip<List<Tree<String>>> getLeftMenu() {
        return successTip(menuService.getLeftMenuList());
    }

    @IgnoreOperationLog
    @ApiOperation("获取用户顶层菜单")
    @GetMapping("/getTopMenu")
    public ResponseTip<List<Map<String, Object>>> getTopMenu() {
        return successTip(menuService.getTopMenuList());
    }

    @ApiOperation("根据角色获取该角色所有的权限(菜单+控件元素)列表")
    @GetMapping("/getPermissionListToRole")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", paramType = "query", required = true),
    })
    public ResponseTip<Object> getPermissionListToRoleId(@RequestParam String roleId) {
        return successTip(permissionService.getPermissionListToRoleId(roleId));
    }

    @ApiOperation("更改角色权限")
    @PostMapping("/updateRolePermission")
    public Tip updateRolePermission(@RequestBody RolePermissionVo rolePermissionVo) {
        permissionService.updateRolePermission(rolePermissionVo);
        return tip(true);
    }

    @ApiOperation("给角色添加菜单权限")
    @PostMapping("/addRolePermission")
    public Tip addRolePermission(@RequestBody RoleMenuVo roleMenuVo) {
        permissionService.addRolePermission(roleMenuVo);
        return tip(true);
    }

    @ApiOperation("给角色删除菜单权限")
    @PostMapping("/delRolePermission")
    public Tip delRolePermission(@RequestBody RoleMenuVo roleMenuVo) {
        permissionService.delRolePermission(roleMenuVo);
        return tip(true);
    }

}
