package com.tul.manage.security.controller;

import apps.commons.base.BaseController;
import apps.commons.tips.ResponseTip;
import apps.commons.tips.Tip;
import apps.commons.util.page.PageInfo;
import com.tul.manage.security.entity.Role;
import com.tul.manage.security.service.IRoleService;
import com.tul.manage.security.service.IUserInfoService;
import com.tul.manage.security.vo.request.OrgUserRoleVo;
import com.tul.manage.security.vo.request.RoleVo;
import com.tul.manage.security.vo.request.UserRoleVo;
import com.tul.manage.security.vo.response.UserInfoVo;
import com.tul.manage.security.vo.response.UserRoleListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 角色控制器
 * @author: znegyu
 * @create: 2021-01-13 10:12
 **/
@Api(value = "角色相关接口", tags = "角色接口")
@RequestMapping("/admin/role")
@RestController
public class RoleController extends BaseController {
    @Resource
    private IRoleService roleService;
    @Resource
    private IUserInfoService userInfoService;

    @ApiOperation("添加角色")
    @PostMapping("/addRole")
    public Tip addRole(@RequestBody RoleVo role) {
        roleService.addRole(role);
        return tip(true);
    }

    @ApiOperation(value = "获取角色列表", notes = "获取角色列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页个数", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页码", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段名称", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", paramType = "query"),
            @ApiImplicitParam(name = "roleName", value = "角色名称", paramType = "query"),
    })
    @RequestMapping(value = "/getRoleList")
    public PageInfo<Role> getRoleList(@RequestParam(required = false) String roleName) {
        return new PageInfo<>(roleService.getRoleList(roleName));
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/delRole")
    public Tip delRole(String id) {
        roleService.delRole(id);
        return tip(true);
    }

    @ApiOperation("更新角色")
    @PostMapping("/updateRole")
    public Tip updateRole(@RequestBody Role role) {
        roleService.updateRole(role);
        return tip(true);
    }

    @ApiOperation("添加用户到指定角色")
    @PostMapping("/addUserToRole")
    public Tip addUserToRole(@RequestBody UserRoleVo userRoleVo) {
        roleService.addUserToRole(userRoleVo);
        return tip(true);
    }

    @ApiOperation("添加组织所有用户到指定角色")
    @PostMapping("/addOrgUserListToRole")
    public Tip addOrgUserListToRole(@RequestBody OrgUserRoleVo orgUserRoleVo) {
        roleService.addOrgUserListToRole(orgUserRoleVo);
        return tip(true);
    }



    @ApiOperation("移除用户角色")
    @PostMapping("/delRoleUser")
    public Tip delRoleUser(@RequestBody UserRoleVo userRoleVo) {
        roleService.delUserRole(userRoleVo);
        return tip(true);
    }

    @ApiOperation("获取指定角色已绑定的用户列表")
    @GetMapping("/getUserListToRole")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", paramType = "query", required = true)
    })
    public ResponseTip<List<UserInfoVo>> getUserListToRole(@RequestParam String roleId) {
        return successTip(userInfoService.getUserListToRole(roleId));
    }

    @ApiOperation("获取指定用户的角色列表")
    @GetMapping("/getRoleListToUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户", paramType = "query", required = true)
    })
    public ResponseTip<List<UserRoleListVo>> getRoleListToUser(@RequestParam String userId) {
        return successTip(roleService.getRoleListToUser(userId));
    }

    @ApiOperation("获取所有角色列表")
    @GetMapping("/getAllRoleList")
    public ResponseTip<List<UserRoleListVo>> getAllRoleList() {
        return successTip(roleService.getAllRoleList());
    }

}
