package com.tul.manage.security.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 分配组织用户Vo
 * @author: znegyu
 * @create: 2021-06-30 08:51
 **/
@Data
public class OrgUserRoleVo {

    @ApiModelProperty(value = "角色id")
    private String roleId;
}
