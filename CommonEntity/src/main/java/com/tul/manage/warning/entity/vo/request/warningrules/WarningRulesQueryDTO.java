package com.tul.manage.warning.entity.vo.request.warningrules;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WarningRulesQueryDTO {

    @ApiModelProperty(value = "规则编号")
    private String ruleCode;

    @ApiModelProperty(value = "预警类别")
    private String typeId;

    @ApiModelProperty(value = "来源组织")
    private String orgId;

    @ApiModelProperty(value = "负责人")
    private String principalEmpId;

    @ApiModelProperty(value = "规则内容")
    private String ruleDescription;

    @ApiModelProperty(value = "规则状态")
    private Integer status;

    @ApiModelProperty(value = "执行人")
    private String executeEmpId;


}
