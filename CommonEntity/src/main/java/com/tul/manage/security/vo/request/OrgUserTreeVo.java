package com.tul.manage.security.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 组织用户树Vo
 * @author: znegyu
 * @create: 2021-06-29 17:36
 **/
@Data
public class OrgUserTreeVo {
    @ApiModelProperty(value = "角色id")
    private String userId;
    @ApiModelProperty(value = "上级组织id")
    private String parentOrgId;
    @ApiModelProperty(value = "组织id")
    private String orgId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "组织名称")
    private String orgName;
}
