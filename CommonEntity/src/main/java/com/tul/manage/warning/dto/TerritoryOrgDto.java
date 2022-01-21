package com.tul.manage.warning.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 负责组织区域DTO
 *
 * @author zengyu
 * @date 2021-09-14 15:21
 **/
@Data
public class TerritoryOrgDto {
    @ApiModelProperty(value = "负责区域表id")
    private String territoryId;
    @JsonIgnore
    @ApiModelProperty(value = "负责人id")
    private String userId;
    @ApiModelProperty(value = "组织区域名称")
    private String orgName;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
