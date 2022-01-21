package com.tul.manage.plan.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 审计计划主表
 *
 * @author Automatic Generation
 * @date 2022-01-10 14:54:41
 */
@Data
@TableName("audit_plan")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "审计计划主表")
public class AuditPlan extends Model<AuditPlan> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="主键")
    private String id;

    /**
     * 计划类型 【temp 临时计划,  year 年度计划】 同时对应字典类型为【plan_type】的数据字典【dict】字段【code】
     */
    @ApiModelProperty(value="计划类型 【temp 临时计划,  year 年度计划】 同时对应字典类型为【plan_type】的数据字典【dict】字段【code】")
    @TableField("plan_type")
    private String planType;

    /**
     * 项目名称
     */
    @ApiModelProperty(value="项目名称")
    @TableField("project_name")
    private String projectName;

    /**
     * 项目种类 (数据字典)  对应类型为【project_type】的数据字典【dict】字段【code】
     */
    @ApiModelProperty(value="项目种类 (数据字典)  对应类型为【project_type】的数据字典【dict】字段【code】")
    @TableField("project_type")
    private String projectType;

    /**
     * 项目归属(组织架构id) **外键** 对应组织表【organization】字段【org_id】
     */
    @ApiModelProperty(value="项目归属(组织架构id) **外键** 对应组织表【organization】字段【org_id】")
    @TableField("project_affiliation")
    private String projectAffiliation;

    /**
     * 项目协作【comprehensive -综合性(跨组织协作的项目); non_comprehensive-非综合性;】 (数据字典) 对应字典类型为【project_cooperation】的数据字典【dict】字段【code】
     */
    @ApiModelProperty(value="项目协作【comprehensive -综合性(跨组织协作的项目); non_comprehensive-非综合性;】 (数据字典) 对应字典类型为【project_cooperation】的数据字典【dict】字段【code】")
    @TableField("project_cooperation")
    private String projectCooperation;

    /**
     * 项目密级【top_secret-绝密; non_top_secret-非绝密;】 (数据字典) 对应字典类型为【project_secret】的数据字典【dict】字段【code】
     */
    @ApiModelProperty(value="项目密级【top_secret-绝密; non_top_secret-非绝密;】 (数据字典) 对应字典类型为【project_secret】的数据字典【dict】字段【code】")
    @TableField("project_secret")
    private String projectSecret;

    /**
     * 主审人id  **外键** 对应用户表【user_info】字段【id】
     */
    @ApiModelProperty(value="主审人id  **外键** 对应用户表【user_info】字段【id】")
    @TableField("audit_user_id")
    private String auditUserId;

    /**
     * 审计线索
     */
    @ApiModelProperty(value="审计线索")
    @TableField("audit_clue")
    private String auditClue;

    /**
     * 审计目的
     */
    @ApiModelProperty(value="审计目的")
    @TableField("audit_goal")
    private String auditGoal;

    /**
     * 审计范围
     */
    @ApiModelProperty(value="审计范围")
    @TableField("audit_scope")
    private String auditScope;

    /**
     * 审计方法
     */
    @ApiModelProperty(value="审计方法")
    @TableField("audit_way")
    private String auditWay;

    /**
     * 审计开始日期
     */
    @ApiModelProperty(value="审计开始日期")
    @TableField("audit_begin_date")
    private LocalDateTime auditBeginDate;

    /**
     * 审计结束日期
     */
    @ApiModelProperty(value="审计结束日期")
    @TableField("audit_end_date")
    private LocalDateTime auditEndDate;

    /**
     * 备注（富文本）
     */
    @ApiModelProperty(value="备注（富文本）")
    @TableField("remark")
    private String remark;

    /**
     * 录入人  **外键** 对应用户表【user_info】字段【id】
     */
    @ApiModelProperty(value="录入人  **外键** 对应用户表【user_info】字段【id】")
    @TableField("create_user_id")
    private String createUserId;

    /**
     * 录入时间
     */
    @ApiModelProperty(value="录入时间")
    @TableField(value="create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 是否删除
     */
    @ApiModelProperty(value="是否删除")
    @TableLogic
    @TableField(value="is_deleted",fill = FieldFill.INSERT)
    private Boolean isDeleted;

    /**
     * 状态 【add -新增计划;  approval-已立项; discard-废弃; discard_approval-作废审批中(年度); plan_approval-立项审批中(临时);】同时对应字典类型为【plan_status】的数据字典【dict】字段【code】
     */
    @ApiModelProperty(value="状态 【add -新增计划;  approval-已立项; discard-废弃; discard_approval-作废审批中(年度); plan_approval-立项审批中(临时);】同时对应字典类型为【plan_status】的数据字典【dict】字段【code】")
    @TableField("status")
    private String status;

    /**
     * 废弃理由
     */
    @ApiModelProperty(value="废弃理由")
    @TableField("abandoned_reason")
    private String abandonedReason;

    /**
     * 主审人
     */
    @ApiModelProperty(value="主审人")
    private String governorName;

    /**
     * 项目成员
     */
    @ApiModelProperty(value="项目成员")
    private String projectMemberName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
