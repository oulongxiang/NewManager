package com.tul.manage.security.controller;


import apps.commons.base.BaseController;
import apps.commons.util.page.PageInfo;
import com.tul.manage.security.config.IgnoreOperationLog;
import com.tul.manage.security.dto.OperationLogDTO;
import com.tul.manage.security.entity.ApiLog;
import com.tul.manage.security.service.IApiLogService;
import com.tul.manage.security.service.IOperationLogService;
import com.tul.manage.security.service.ISystemLogService;
import com.tul.manage.security.vo.response.SystemLogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统日志表 前端控制器
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-03-23
 */
@Api(value = "第三方日志接口", tags = "第三方日志相关接口")
@RestController
@RequestMapping("/admin/systemLog")
public class SystemLogController extends BaseController {

    @Resource
    private ISystemLogService iSystemLogService;
    @Resource
    private IApiLogService iApiLogService;
    @Resource
    private IOperationLogService operationLogService;

    @ApiOperation("获取错误日志信息列表")
    @PostMapping("/getSystemLogList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "operatorName", value = "操作人员的姓名", paramType = "query"),
            @ApiImplicitParam(name = "errType", value = "日志类型", paramType = "query"),
            @ApiImplicitParam(name = "beginDate", value = "查询开始操作时间", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "查询结束操作时间", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页个数", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页码", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", paramType = "query")
    })
    public PageInfo<SystemLogVo> getSystemLogList(String operatorName,Integer errType,
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginDate,
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate) {
        return iSystemLogService.getSystemLogList(operatorName,errType, beginDate, endDate);
    }

    @ApiOperation("根据sql的类型获取对应的操作日志报表")
    @PostMapping("/getSqlOperationList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "operatorType", value = "操作类型", paramType = "query"),
            @ApiImplicitParam(name = "operatorName", value = "操作人员的姓名", paramType = "query"),
            @ApiImplicitParam(name = "beginDate", value = "查询开始操作时间", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "查询结束操作时间", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页个数", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页码", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", paramType = "query")
    })
    public PageInfo<SystemLogVo> getSqlOperationList(String operatorType,
                                                     String operatorName,
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginDate,
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate) {
        return iSystemLogService.getSqlOperationList(operatorType, operatorName, beginDate, endDate);
    }

    @ApiOperation("查询ApiLog列表")
    @GetMapping("/getApiLogList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "api类型", paramType = "query"),
            @ApiImplicitParam(name = "param", value = "json值", paramType = "query"),
            @ApiImplicitParam(name = "responseState", value = "是否成功", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页个数", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页码", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", paramType = "query")
    })
    public PageInfo<ApiLog> getApiLogList(String param, String type, String responseState, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return iApiLogService.getApiLogList(param, type, responseState, startTime, endTime);
    }

    @IgnoreOperationLog
    @ApiOperation("获取操作日志")
    @GetMapping("/getOperationLogList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "操作用户id", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "操作用户名(模糊查)", paramType = "query"),
            @ApiImplicitParam(name = "beginOperationDate", value = "操作开始时间(登录时间范围)", paramType = "query"),
            @ApiImplicitParam(name = "endOperationDate", value = "操作结束时间(登录时间范围)", paramType = "query"),
            @ApiImplicitParam(name = "requestType", value = "请求类型 ", paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页数(不传默认1)", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页个数(不传默认10)", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段", paramType = "query"),
            @ApiImplicitParam(name = "order", value = "asc或desc(升序或降序)", paramType = "query")
    })
    public PageInfo<OperationLogDTO> getOperationLogList() {
        return operationLogService.getOperationLogList();
    }

}

