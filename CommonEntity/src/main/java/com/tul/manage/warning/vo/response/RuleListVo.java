package com.tul.manage.warning.vo.response;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 预警规则明细Vo
 * @author: wangcy
 * @create: 2021-08-04
 */
@Data
public class RuleListVo {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "预警规则主键")
    private String ruleId;

    @ApiModelProperty(value = "预警指标说明")
    private String warningDiscription;

    @ApiModelProperty(value = "预警计划说明")
    private String warningPlanDiscription;

    @ApiModelProperty(value = "预警等级Id")
    private String gradeId;

    @ApiModelProperty(value = "预警等级")
    private String gradeName;

    @ApiModelProperty(value = "录入人ID")
    private String createEmpId;

    @ApiModelProperty(value = "录入时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "状态： 0禁用  1 启用  ")
    private Integer state;

    @ApiModelProperty(value = "状态名： 0禁用  1 启用  ")
    private String statName;

    @ApiModelProperty(value = "录入人")
    private String createEmpName;

}
