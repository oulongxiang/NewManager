package com.tul.manage.warning.vo.response;

import com.tul.manage.warning.entity.RiskInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author subaoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RiskInfoReportFormVo extends RiskInfo {

    @ApiModelProperty(value = "风险编号")
    private String riskCode;

    @ApiModelProperty(value = "风险名称")
    private String riskName;

    @ApiModelProperty(value = "风险描述")
    private String ruleDescription;

    @ApiModelProperty(value = "风险类别")
    private String typeName;

    @ApiModelProperty(value = "处理结果")
    private String resultName;

    @ApiModelProperty(value = "风险等级id")
    private String strategyId;

    @ApiModelProperty(value = "处理人名称")
    private String disposeResult;

    @ApiModelProperty(value = "应对策略名称id")
    private String gradeId;


}
