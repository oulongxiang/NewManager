package com.tul.manage.warning.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tul.manage.handler.MybatisStringArrayTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 *
 * @author Automatic Generation
 * @date 2021-10-20 16:45:41
 */
@Data
@TableName("rule_rank")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "")
public class RuleRank extends Model<RuleRank> {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="id")
    private String id;

    /**
     * 排名
     */
    @ApiModelProperty(value="排名")
    @TableField("rank")
    private Integer rank;

    /**
     * 年
     */
    @ApiModelProperty(value="年")
    @TableField("year")
    private Integer year;

    /**
     * 季度
     */
    @ApiModelProperty(value="季度")
    @TableField("season")
    private Integer season;

    /**
     * 明细ID
     */
    @ApiModelProperty(value="明细ID")
    @TableField("rule_list_id")
    private String ruleListId;

    /**
     * 规则编码
     */
    @ApiModelProperty(value="规则编码")
    @TableField("rule_code")
    private String ruleCode;

    /**
     * 规则内容
     */
    @ApiModelProperty(value="规则内容")
    @TableField("warning_discription")
    private String warningDiscription;

    /**
     * 负责人
     */
    @ApiModelProperty(value="负责人")
    @TableField("principal_emp_id")
    private String principalEmpId;

    /**
     * 预警范围
     */
    @ApiModelProperty(value="预警范围")
    @TableField(value = "org_names",typeHandler = MybatisStringArrayTypeHandler.class)
    private List<String> orgNames;

    /**
     * 实际范围
     */
    @ApiModelProperty(value="实际范围")
    @TableField("org_name")
    private String orgName;

    /**
     * 上次排名
     */
    @ApiModelProperty(value="上次排名")
    @TableField("last_rank")
    private Integer lastRank;

    /**
     * 上榜次数
     */
    @ApiModelProperty(value="上榜次数")
    @TableField("rank_times")
    private Integer rankTimes;

    /**
     * 风险数量
     */
    @ApiModelProperty(value="风险数量")
    @TableField("risk_nums")
    private Integer riskNums;

    /**
     * 是否删除
     */
    @ApiModelProperty(value="是否删除")
    @TableLogic
    @TableField(value="is_deleted",fill = FieldFill.INSERT)
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @TableField(value="create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 预警等级
     */
    @ApiModelProperty(value="预警等级")
    @TableField("gradeId")
    private String gradeId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
