package com.tul.manage.warning.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 指定区域负责人Vo
 *
 * @author zengyu
 * @date 2021-09-14 15:05
 **/
@Data
public class AppointTerritoryVo {
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "组织区域id")
    private String orgId;
}
