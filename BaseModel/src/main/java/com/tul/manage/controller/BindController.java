package com.tul.manage.controller;

import apps.commons.base.BaseController;
import apps.commons.tips.ResponseTip;
import com.tul.manage.security.config.IgnoreOperationLog;
import com.tul.manage.security.service.IDictService;
import com.tul.manage.security.service.IRoleService;
import com.tul.manage.security.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description: 数据绑定接口
 * @author: znegyu
 * @create: 2021-05-21 10:24
 **/
@IgnoreOperationLog
@Api(value = "数据绑定接口", tags = "数据绑定接口")
@RestController
@RequestMapping("/admin/bind")
public class BindController extends BaseController {

    @Resource
    private IDictService dictService;
    @Resource
    private IRoleService roleService;
    @Resource
    private IUserInfoService userInfoService;

    @ApiOperation("绑定下级用户选择框")
    @GetMapping("/bindSubordinateUserSelect")
    public ResponseTip<List<Map<String, Object>>> bindSubordinateUserSelect(){
        return successTip(userInfoService.bindSubordinateUserSelect());
    }



    @ApiOperation("绑定数据字典")
    @GetMapping("/getDictAdd")
    public ResponseTip<Object> getDictAdd(){
        return successTip(dictService.getDictAdd());
    }

    @ApiOperation("绑定角色")
    @GetMapping("/bindRole")
    public ResponseTip<Object> bindRole(){ return successTip(roleService.bindRole());}




}
