package com.tul.manage.warning.vo.response;

import com.tul.manage.warning.enums.RuleStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WarningRulesVO {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "预警类别")
    private String typeId;

    @ApiModelProperty(value = "规则编号")
    private String ruleCode;

    @ApiModelProperty(value = "规则内容")
    private String ruleDescription;

    @ApiModelProperty(value = "来源组织ID")
    private String orgId;

    @ApiModelProperty(value = "负责人")
    private String principalEmpId;

    @ApiModelProperty(value = "预警范围")
    private List<String> orgIds;

    @ApiModelProperty(value = "报表连接")
    private String reportUrl;

    @ApiModelProperty(value = "状态")
    private RuleStatusEnum status;

    @ApiModelProperty(value = "录入人")
    private String createEmpId;

    @ApiModelProperty(value = "录入时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "执行人")
    private String executeEmpId;

    @ApiModelProperty(value = "来源组织名称")
    private String orgName;

    @ApiModelProperty(value = "来源组织名称")
    private String TypeName;

    @ApiModelProperty(value = "预警范围集合")
    private String markIds;

    @ApiModelProperty(value = "是否可以进行生效操作")
    private String handleState;

    @ApiModelProperty(value = "是否可以进行生效操作")
    private String idList;

    @ApiModelProperty(value = "风险类别")
    private String riskTypeId;

    @ApiModelProperty(value = "风险类别名字")
    private String rTypeName;

}
