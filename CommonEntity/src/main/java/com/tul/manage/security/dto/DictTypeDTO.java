package com.tul.manage.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DictTypeDTO {
    @ApiModelProperty(value = "类型")
    private String name;

    @ApiModelProperty(value = "描述")
    private String describe;

    @ApiModelProperty(value = "备注信息")
    private String explain;

}
