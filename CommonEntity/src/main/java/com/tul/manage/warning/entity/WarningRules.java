package com.tul.manage.warning.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tul.manage.handler.MybatisStringArrayTypeHandler;
import com.tul.manage.warning.enums.RuleStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 预警规则表
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="WarningRules对象", description="预警规则表")
public class WarningRules extends Model<WarningRules> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "预警类别")
    @TableField("type_id")
    private String typeId;

    @ApiModelProperty(value="风险类别id **外键** 对应表风险类别表【risk_type】字段【id】")
    @TableField("risk_type_id")
    private String riskTypeId;

    @ApiModelProperty(value = "规则编号")
    @TableField("rule_code")
    private String ruleCode;

    @ApiModelProperty(value = "规则内容")
    @TableField("rule_description")
    private String ruleDescription;

    @ApiModelProperty(value = "来源组织")
    @TableField("org_id")
    private String orgId;

    @ApiModelProperty(value = "负责人")
    @TableField("principal_emp_id")
    private String principalEmpId;

    @ApiModelProperty(value = "预警范围")
    @TableField(value = "org_ids",typeHandler = MybatisStringArrayTypeHandler.class)
    private List<String> orgIds;

    @ApiModelProperty(value = "报表连接")
    @TableField("report_url")
    private String reportUrl;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private RuleStatusEnum status;

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


    @ApiModelProperty(value = "执行人")
    @TableField("execute_emp_id")
    private String executeEmpId;

    @ApiModelProperty(value = "保存多选数据")
    @TableField("mark_ids")
    private String markIds;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
