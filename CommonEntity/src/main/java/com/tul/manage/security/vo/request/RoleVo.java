package com.tul.manage.security.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 角色接收VO
 * @author: znegyu
 * @create: 2021-01-13 09:53
 **/
@Data
public class RoleVo {
    @ApiModelProperty(value = "角色名")
    private String roleName;
    @ApiModelProperty(value = "角色编码")
    private String roleCode;
    @ApiModelProperty(value = "说明")
    private String description;
}
