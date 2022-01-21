package com.tul.manage.warning.vo.response;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 预警作业VO类
 * @author wuyaobin
 * @create 2021-08-04
 */
@Data
public class WarningJobVo {
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "预警规则主键")
    @TableField("rule_id")
    private String ruleId;

    @ApiModelProperty(value = "预警类别")
    @TableField("type_name")
    private String typeName;

    @ApiModelProperty(value = "规则编号")
    @TableField("rule_code")
    private String ruleCode;

    @ApiModelProperty(value = "执行计划")
    @TableField("exec_plan")
    private String execPlan;

    @ApiModelProperty(value = "执行作业")
    @TableField("exec_job")
    private String execJob;

    @ApiModelProperty(value = "执行信息")
    @TableField("message")
    private String message;

    @ApiModelProperty(value = "执行状态 0 未启用  1 启用")
    @TableField("state")
    private Integer state;

    //TODO:是否保留
    /*@ApiModelProperty(value = "录入人")
    @TableField("create_emp_id")
    private String createEmpId;*/

    @ApiModelProperty(value = "录入人姓名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "录入时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
