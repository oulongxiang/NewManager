package com.tul.manage.security.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 角色菜单vo
 * @author: znegyu
 * @create: 2021-01-22 17:07
 **/
@Data
public class RoleMenuVo {
    @ApiModelProperty(value = "角色id")
    private String roleId;
    @ApiModelProperty(value = "菜单id")
    private String menuId;
}
