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
 * 预警规则明细表
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RuleList对象", description="预警规则明细表")
public class RuleList extends Model<RuleList> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "预警规则主键")
    @TableField("rule_id")
    private String ruleId;

    @ApiModelProperty(value = "预警指标说明")
    @TableField("warning_discription")
    private String warningDiscription;

    @ApiModelProperty(value = "预警计划说明")
    @TableField("warning_plan_discription")
    private String warningPlanDiscription;

    @ApiModelProperty(value = "预警等级")
    @TableField("grade_id")
    private String gradeId;

    @ApiModelProperty(value = "录入人")
    @TableField("create_emp_id")
    private String createEmpId;

    @ApiModelProperty(value = "录入时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除")
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "状态： 0禁用  1 启用  ")
    @TableField("state")
    private Integer state;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
