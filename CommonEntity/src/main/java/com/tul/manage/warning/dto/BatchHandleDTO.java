package com.tul.manage.warning.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BatchHandleDTO {
    @ApiModelProperty(value = "批量类型")
    private Integer batchType;
    @ApiModelProperty(value = "批量选中ID")
    private List<String> selectListId;
    @ApiModelProperty(value = "原因")
    private String reson;
}
