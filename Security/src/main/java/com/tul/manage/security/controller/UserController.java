package com.tul.manage.security.controller;

import apps.commons.base.BaseController;
import apps.commons.tips.ResponseTip;
import apps.commons.tips.Tip;
import apps.commons.util.page.PageInfo;
import com.tul.manage.security.entity.UserInfo;
import com.tul.manage.security.service.IPositionService;
import com.tul.manage.security.service.IUserInfoService;
import com.tul.manage.security.vo.response.UserInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description: 用戶api
 * @author: znegyu
 * @create: 2021-01-13 19:49
 **/
@Api(value = "用戶相关接口", tags = "用戶接口")
@RequestMapping("/admin/user")
@RestController
public class UserController extends BaseController {

    @Resource
    private IUserInfoService userInfoService;
    @Resource
    private IPositionService iPositionService;


    @ApiOperation("获取当前登录的用户信息")
    @GetMapping("/getUserInfo")
    public ResponseTip<UserInfo> getUserInfo() {
        return successTip(userInfoService.getUserInfo());
    }

    @ApiOperation("获取用户信息列表")
    @GetMapping("/getUserInfoList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页个数", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页码", paramType = "query"),
            @ApiImplicitParam(name = "empCode", value = "员工号", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "姓名", paramType = "query"),
            @ApiImplicitParam(name = "gender", value = "性别", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "手机号码", paramType = "query"),
            @ApiImplicitParam(name = "orgName", value = "组织区域", paramType = "query"),
            @ApiImplicitParam(name = "territory", value = "负责区域", paramType = "query"),
            @ApiImplicitParam(name = "roleNames", value = "角色id", paramType = "query")
    })
    public PageInfo<UserInfoVo> getUserInfoList() {
        return userInfoService.getUserInfoList();
    }





    @ApiOperation("获取所有的岗位列表")
    @GetMapping("/getPositionNameList")
    public Tip getPositionNameList(){
        return successTip(iPositionService.getPositionNameList());
    }

    @ApiOperation("新增或者修改外部员工")
    @PostMapping("addUserInfoOut")
    public Tip addUserInfoOut(@RequestBody UserInfo userInfo){
        userInfoService.addUserInfoOut(userInfo);
        return successTip(true);
    }

    @ApiOperation(value = "删除外部员工", notes = "删除部门员工", httpMethod = "POST")
    @DeleteMapping(value = "/delUserInfoOut")
    public ResponseTip<Object> delUserInfoOut(String id) {
        userInfoService.delUserInfoOut(id);
        return successTip(true);
    }

    @ApiOperation("获取该用户上级的ID")
    @GetMapping("/getUserLeaderId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query"),

    })
    public ResponseTip<UserInfo> getUserLeaderId(String userId){
        return successTip(userInfoService.getUserLeaderId(userId));
    }

}
