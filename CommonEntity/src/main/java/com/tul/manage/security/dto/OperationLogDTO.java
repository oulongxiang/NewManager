package com.tul.manage.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录日志VO
 * @author zengyu
 * @date 2021-08-13 08:49
 **/
@Data
public class OperationLogDTO {
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "请求类型")
    private String requestType;
    @ApiModelProperty(value = "请求参数")
    private String requestParam;
    @ApiModelProperty(value = "操作")
    private String operation;
    @ApiModelProperty(value = "浏览器")
    private String browser;
    @ApiModelProperty(value = "操作系统")
    private String operatingSystem;
    @ApiModelProperty(value = "ip地址")
    private String ipAddress;
    @ApiModelProperty(value = "创建时间(等同于登录时间)")
    private LocalDateTime createTime;
}
