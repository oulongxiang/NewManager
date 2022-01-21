package com.tul.manage.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统日志表
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SystemLog对象", description="系统日志表")
public class SystemLog extends Model<SystemLog> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "系统日志表主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "日志级别")
    @TableField("log_level")
    private String logLevel;

    @ApiModelProperty(value = "操作(日志记录)时间")
    @TableField("operation_time")
    private LocalDateTime operationTime;

    @ApiModelProperty(value = "操作人员身份证号")
    @TableField("operator_user_id")
    private String operatorUserId;

    @ApiModelProperty(value = "日志内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "打印日志的包名")
    @TableField("caller_package_name")
    private String callerPackageName;

    @ApiModelProperty(value = "打印日志的类名")
    @TableField("caller_class_name")
    private String callerClassName;

    @ApiModelProperty(value = "打印日志的方法名")
    @TableField("caller_method_name")
    private String callerMethodName;

    @ApiModelProperty(value = "打印日志的行数")
    @TableField("caller_line")
    private Integer callerLine;

    @ApiModelProperty(value = "堆栈信息")
    @TableField("stack_trace")
    private Integer stackTrace;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
