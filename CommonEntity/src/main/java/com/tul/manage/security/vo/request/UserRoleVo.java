package com.tul.manage.security.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 分配角色vo
 * @author: znegyu
 * @create: 2021-01-22 10:27
 **/
@Data
public class UserRoleVo {
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "角色id")
    private String roleId;
}
