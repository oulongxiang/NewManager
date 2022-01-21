package com.tul.manage.security.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织绑定vo
 *
 * @author zengyu
 * @date 2021-09-15 13:45
 **/
@Data
public class BindOrgVo {
    @ApiModelProperty(value = "上级组织id")
    private String parentOrgId;
    @ApiModelProperty(value = "组织id")
    private String orgId;
    @ApiModelProperty(value = "组织名称")
    private String orgName;
    @ApiModelProperty(value = "层级")
    private String orgIdHierarchy;
}
