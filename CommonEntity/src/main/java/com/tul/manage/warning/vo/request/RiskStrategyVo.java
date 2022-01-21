package com.tul.manage.warning.vo.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @创建人 陈天艺
 * @创建时间 2021/8/3
 * @描述
 */
@Data
public class RiskStrategyVo {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "策略名称")
    private String strategyName;

    @ApiModelProperty(value = "上级策略主键")
    private String topId;

    @ApiModelProperty(value = "上级策略名称")
    private String topName;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "录入人")
    @TableField("create_emp_id")
    private String createEmpId;

    @ApiModelProperty(value = "录入人姓名")
    private String createEmpName;

    @ApiModelProperty(value = "录入时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    @ApiModelProperty(value = "风险编号")
    private String riskCode;

    @ApiModelProperty(value = "风险名称")
    private String ruleDescription;

}
