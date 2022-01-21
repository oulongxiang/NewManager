package com.tul.manage.plan.controller;

import apps.commons.base.BaseController;
import apps.commons.util.page.PageInfo;
import com.tul.manage.plan.entity.AuditPlan;
import com.tul.manage.plan.service.IAuditPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 审计计划主表
 *
 * @author Automatic Generation
 * @date 2022-01-10 14:54:41
 */
@RestController
@RequestMapping("/admin/auditPlan" )
@Api(value = "审计计划主表接口", tags = "审计计划主表接口")
public class AuditPlanController extends BaseController{

    @Resource
    private IAuditPlanService auditPlanService;

    @ApiOperation(value = "带条件分页查询",notes = "带条件分页查询")
    @GetMapping("/queryAuditPlanList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "playType", value = "计划类型", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "计划状态", paramType = "query"),
            @ApiImplicitParam(name = "targetType", value = "审计对象", paramType = "query"),
            @ApiImplicitParam(name = "auditUserId", value = "主审人", paramType = "query"),
            @ApiImplicitParam(name = "auditEnddate", value = "时间", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页个数", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页码", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "true-升序 false-降序", paramType = "query")
    })
    public PageInfo<AuditPlan> queryAuditPlanList(){
        return auditPlanService.queryAuditPlanInfoPageList();
    }
}
