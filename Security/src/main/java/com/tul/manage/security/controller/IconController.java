package com.tul.manage.security.controller;


import apps.commons.base.BaseController;
import apps.commons.tips.ResponseTip;
import com.tul.manage.security.config.IgnoreOperationLog;
import com.tul.manage.security.service.IIconService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 图标表 前端控制器
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-01-28
 */
@Api(value = "图标相关接口", tags = "图标相关接口")
@RestController
@RequestMapping("/admin/icon")
public class IconController extends BaseController {
    @Resource
    private IIconService iIconService;

    @IgnoreOperationLog
    @ApiOperation("获取图标列表")
    @GetMapping("/bindIconSelect")
    public ResponseTip<Object> getIcon() {
        return successTip(iIconService.bindIconSelect());
    }
}

