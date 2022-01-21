package com.tul.manage.security.controller;

import apps.commons.base.BaseController;
import apps.commons.tips.ResponseTip;
import apps.commons.tips.Tip;
import apps.commons.util.page.PageInfo;
import com.tul.manage.security.config.LoginBean;
import com.tul.manage.security.config.LoginMethod;
import com.tul.manage.security.service.IUserInfoLoginRecordService;
import com.tul.manage.security.service.IUserInfoService;
import com.tul.manage.security.vo.response.LoginLogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @description: 登录控制器
 * @author: znegyu
 * @create: 2021-01-09 15:25
 **/
@Api(value = "登录相关接口", tags = "登录相关接口")
@RestController
@RequestMapping("/admin/user")
public class LoginController extends BaseController {
    @Resource
    private IUserInfoService userInfoService;

    @Resource
    private IUserInfoLoginRecordService userInfoLoginRecordService;

    /**
     * 通过账号密码登录
     * 账号:用户手机号码(唯一标识符)
     * 密码:默认是(tul3933)
     *
     * @param loginBean 登录所需数据
     * @return 包含token的json
     */
    @LoginMethod("account")
    @ApiOperation("用户账号密码登录")
    @PostMapping("/login")
    public ResponseTip<Map<String, Object>> login(@RequestBody LoginBean loginBean) {
        return successTip(userInfoService.authentication(loginBean));
    }


    @ApiOperation("用户注销")
    @PostMapping("/logout")
    public Tip logout() {
        userInfoService.logout();
        return tip(true);
    }

    @ApiOperation("用户修改密码")
    @PostMapping("/updatePassword")
    public Tip updatePassword(@RequestBody Map<String, Object> data) {
        userInfoService.updatePassword(data);
        return tip(true);
    }

    @ApiOperation("获取登录日志")
    @GetMapping("/getLoginLogList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "登录用户id", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "登录用户名(模糊查)", paramType = "query"),
            @ApiImplicitParam(name = "beginLoginDate", value = "登录开始时间(登录时间范围)", paramType = "query"),
            @ApiImplicitParam(name = "endLoginDate", value = "登录结束时间(登录时间范围)", paramType = "query"),
            @ApiImplicitParam(name = "loginType", value = "登录类型 ", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页数(不传默认1)", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页个数(不传默认10)", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", paramType = "query")
    })
    public PageInfo<LoginLogVo> getLoginLogList() {
        return userInfoLoginRecordService.getLoginLogList();
    }

}
