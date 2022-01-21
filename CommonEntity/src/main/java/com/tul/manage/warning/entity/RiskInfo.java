package com.tul.manage.warning.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tul.manage.handler.MybatisStringArrayTypeHandler;
import com.tul.manage.warning.enums.RiskStateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 风险信息表
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RiskInfo对象", description="风险信息表")
public class RiskInfo extends Model<RiskInfo> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "是否人工")
    @TableField("is_man")
    private Boolean isMan;

    @ApiModelProperty(value = "规则主键")
    @TableField("rule_id")
    private String ruleId;

    @ApiModelProperty(value = "风险编号")
    @TableField("risk_code")
    private String riskCode;

    @ApiModelProperty(value = "来源组织")
    @TableField("org_id")
    private String orgId;

    @ApiModelProperty(value = "预警类别")
    @TableField("type_id")
    private String typeId;

    @ApiModelProperty(value="手动输入的预警类别")
    @TableField("manual_input_type")
    private String manualInputType;

    @ApiModelProperty(value = "预警等级")
    @TableField("grade_id")
    private String gradeId;

    @ApiModelProperty(value = "规则内容")
    @TableField("rule_description")
    private String ruleDescription;

    @ApiModelProperty(value = "作用范围")
    @TableField(value = "org_ids",typeHandler = MybatisStringArrayTypeHandler.class )
    private List<String> orgIds;

    @ApiModelProperty(value = "风险责任人")
    @TableField("principal_emp_id")
    private String principalEmpId;

    @ApiModelProperty(value = "执行人")
    @TableField("emp_id")
    private String empId;

    @ApiModelProperty(value = "报表链接")
    @TableField("report_url")
    private String reportUrl;

    @ApiModelProperty(value = "录入人")
    @TableField("create_emp_id")
    private String createEmpId;

    @ApiModelProperty(value = "录入时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value="数据创建/捕获时间")
    @TableField(value="date_create_time",fill = FieldFill.INSERT)
    private LocalDateTime dateCreateTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除")
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "风险影响")
    @TableField("risk_impact")
    private String riskImpact;

    @ApiModelProperty(value = "可能后果")
    @TableField("implications")
    private String implications;

    @ApiModelProperty(value = "风险概率")
    @TableField("risk_probability")
    private String riskProbability;

    @ApiModelProperty(value = "风险期望")
    @TableField("risk_expectation")
    private String riskExpectation;

    @ApiModelProperty(value = "风险策略")
    @TableField("strategy_id")
    private String strategyId;

    @ApiModelProperty(value = "应对措施")
    @TableField("solutions")
    private String solutions;

    @ApiModelProperty(value = "处理结果")
    @TableField("dispose_result")
    private String disposeResult;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "处理人")
    @TableField("dispose_emp_id")
    private String disposeEmpId;

    @ApiModelProperty(value = "处理时间")
    @TableField("dispose_time")
    private LocalDateTime disposeTime;

    @ApiModelProperty(value = "风险信息状态")
    @TableField("state")
    private RiskStateEnum state;

    @ApiModelProperty(value = "规则明细ID")
    @TableField("rule_list_id")
    private String ruleListId;

    @ApiModelProperty(value = "保存多选数据")
    @TableField("mark_ids")
    private String markIds;

    @ApiModelProperty(value = "风险类别")
    @TableField("risk_type_id")
    private String riskTypeId;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
