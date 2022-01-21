package com.tul.manage.warning.vo.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 预警类别列表Vo
 * @author: Tanjy
 * @create: 2021-8-3 16:22
 **/
@Data
public class WarningTypeVo {

    @ApiModelProperty(value = "类别id")
    private String id;

    @ApiModelProperty(value = "所属组织")
    private String orgName;

    @ApiModelProperty(value = "所属组织Id")
    private String orgId;

    @ApiModelProperty(value = "分类名称")
    private String typeName;

    @ApiModelProperty(value = "录入人")
    private String userName;

    @ApiModelProperty(value = "录入日期")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新日期")
    private LocalDateTime updateTime;


}
