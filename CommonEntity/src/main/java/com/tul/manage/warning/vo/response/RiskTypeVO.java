package com.tul.manage.warning.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RiskTypeVO {

    /**
     * 风险类别ID
     */
    @ApiModelProperty(value = "风险类别ID")
    private String id;

    /**
     * 风险类别名称
     */
    @ApiModelProperty(value="风险类别名称")
    private String riskType;

    /**
     * 录入人
     */
    @ApiModelProperty(value="录入人")
    private String createEmpId;

    /**
     * 录入时间
     */
    @ApiModelProperty(value="录入时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private LocalDateTime updateTime;

}
