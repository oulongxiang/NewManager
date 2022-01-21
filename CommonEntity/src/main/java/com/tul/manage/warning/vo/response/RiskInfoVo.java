package com.tul.manage.warning.vo.response;

import com.tul.manage.warning.entity.RiskInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author subaoyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RiskInfoVo extends RiskInfo {



    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "风险类别名称")
    private String typeName;

    @ApiModelProperty(value = "预警表规则内容")
    private String warningRuleDescription;

    @ApiModelProperty(value = "风险责任人名称")
    private String principalEmpName;

    @ApiModelProperty(value = "执行人名称")
    private String empName;

    @ApiModelProperty(value = "录入人名称")
    private String createEmpName;

    @ApiModelProperty(value = "处理人名称")
    private String disposeEmpName;

    @ApiModelProperty(value = "风险策略名称")
    private String strategyName;

    @ApiModelProperty(value = "规则编号")
    private String ruleCode;

    @ApiModelProperty(value = "预警指标说明")
    private String warningDiscription;

    @ApiModelProperty(value = "区域组织缩写")
    private String abridgeCode;

    @ApiModelProperty(value = "上传文件名")
    private String uploadFileName;

    @ApiModelProperty(value = "上传文件路径")
    private String fileUrl;

    @ApiModelProperty(value = "上传文件id")
    private String annexId;

    @ApiModelProperty(value = "是否手动输入风险类别")
    private Boolean isManualInputType;

    @ApiModelProperty(value = "用于绑定预警范围多选框")
    private List<List<String>> bindSelectOrgId;

    @ApiModelProperty(value = "预警范围不做修改时返回")
    private String idList;

    @ApiModelProperty(value = "风险类别名字")
    private String rTypeName;
}
