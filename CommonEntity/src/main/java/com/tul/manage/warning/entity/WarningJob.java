package com.tul.manage.warning.entity;

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
 * 预警作业
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="WarningJob对象", description="预警作业")
public class WarningJob extends Model<WarningJob> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "预警规则主键")
    @TableField("rule_id")
    private String ruleId;

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

    @ApiModelProperty(value = "录入人")
    @TableField("create_emp_id")
    private String createEmpId;

    @ApiModelProperty(value = "录入时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean isDeleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
