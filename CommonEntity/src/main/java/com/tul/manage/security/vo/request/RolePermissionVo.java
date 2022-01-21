package com.tul.manage.security.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description: 角色权限vo
 * @author: znegyu
 * @create: 2021-01-22 16:28
 **/
@Data
public class RolePermissionVo {
    @ApiModelProperty(value = "角色id")
    private String roleId;
    @ApiModelProperty(value = "勾选的菜单元素控件id列表")
    private List<String> selectElementIds;
}
