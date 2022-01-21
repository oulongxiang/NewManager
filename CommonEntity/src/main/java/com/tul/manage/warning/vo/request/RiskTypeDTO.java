package com.tul.manage.warning.vo.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RiskTypeDTO {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 风险类别名称
     */
    @ApiModelProperty(value = "风险类别名称")
    private String riskType;

    /**
     * 录入人
     */
    @ApiModelProperty(value="录入人")
    @TableField("create_emp_id")
    private String createEmpId;
}
