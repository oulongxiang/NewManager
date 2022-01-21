package com.tul.manage.security.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 角色用户Vo
 * @author: znegyu
 * @create: 2021-05-29 13:52
 **/
@Data
public class UserRoleListVo {
    @ApiModelProperty(value = "角色id")
    private String roleId;
    @ApiModelProperty(value = "用户角色关联关系id(唯一)")
    private String userRoleId;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
}
