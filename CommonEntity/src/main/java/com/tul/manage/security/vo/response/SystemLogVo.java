package com.tul.manage.security.vo.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日志vo
 * @author zengyu
 */
@Data
public class SystemLogVo {
    @ApiModelProperty(value = "操作人员姓名")
    private String name;
    @ApiModelProperty(value = "日志级别")
    private String logLevel;
    @ApiModelProperty(value = "日志内容")
    private String content;
    @ApiModelProperty(value = "打印日志的包名")
    private String callerPackageName;
    @ApiModelProperty(value = "打印日志的方法名")
    private String callerMethodName;
    @ApiModelProperty(value = "打印日志的行数")
    private Integer callerLine;
    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operationTime;
    @ApiModelProperty(value = "操作人员身份证")
    private String operatorUserId;
    @ApiModelProperty(value ="堆栈信息")
    private String stackTrace;
}
