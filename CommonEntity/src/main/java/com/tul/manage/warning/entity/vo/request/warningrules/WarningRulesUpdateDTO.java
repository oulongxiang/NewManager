package com.tul.manage.warning.entity.vo.request.warningrules;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class WarningRulesUpdateDTO {

    @ApiModelProperty(value = "当前行id")
    private String id;

    @ApiModelProperty(value = "预警类别")
    private String typeId;

    @ApiModelProperty(value = "规则编号")
    private String ruleCode;

    @ApiModelProperty(value = "规则内容")
    private String ruleDescription;

    @ApiModelProperty(value = "来源组织")
    private String orgId;

    @ApiModelProperty(value = "负责人")
    private String principalEmpId;

    @ApiModelProperty(value = "执行人")
    private String executeEmpId;

    @ApiModelProperty(value = "预警范围")
    private List<String> orgIds;

    @ApiModelProperty(value = "报表连接")
    private String reportUrl;

    @ApiModelProperty(value = "保存多选项")
    private String markIds;

    @ApiModelProperty(value = "风险类别")
    private String riskTypeId;

}
