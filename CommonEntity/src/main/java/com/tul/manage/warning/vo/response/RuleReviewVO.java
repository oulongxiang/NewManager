package com.tul.manage.warning.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RuleReviewVO {

    @ApiModelProperty(value = "明细ID")
    private String id;

    @ApiModelProperty(value = "排名")
    private String rank;

    @ApiModelProperty(value = "规则编号")
    private String ruleCode;

    @ApiModelProperty(value = "规则内容")
    private String ruleDiscription;

    @ApiModelProperty(value = "预警类别")
    private String typeId;

    @ApiModelProperty(value = "预警范围")
    private List<String> orgIds;

    @ApiModelProperty(value = "实际范围")
    private String orgId;

    @ApiModelProperty(value = "预警等级")
    private String gradeId;

    @ApiModelProperty(value = "负责人")
    private String principalEmpId;

    @ApiModelProperty(value = "上次排行")
    private String lastRank;

    @ApiModelProperty(value = "上榜次数")
    private String rankTimes;

    @ApiModelProperty(value = "风险数量")
    private int num;
}
