package com.tul.manage.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 操作日志表
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OperationLog对象", description="操作日志表")
public class OperationLog extends Model<OperationLog> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "操作日志表id 主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "登录的用户id **外键** 对应 用户表【user_info】字段 【id】")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "请求类型")
    @TableField("request_type")
    private String requestType;

    @ApiModelProperty(value="请求参数")
    @TableField("request_param")
    private String requestParam;

    @ApiModelProperty(value = "操作")
    @TableField("operation")
    private String operation;

    @ApiModelProperty(value = "浏览器")
    @TableField("browser")
    private String browser;

    @ApiModelProperty(value = "操作系统")
    @TableField("operating_system")
    private String operatingSystem;

    @ApiModelProperty(value = "请求ip地址")
    @TableField("ip_address")
    private String ipAddress;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "是否删除")
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
