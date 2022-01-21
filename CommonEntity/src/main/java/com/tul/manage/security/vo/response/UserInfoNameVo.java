package com.tul.manage.security.vo.response;

import com.tul.manage.security.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 前端用户管理vo
 * @author: znegyu
 * @create: 2021-01-23 14:14
 **/
@Data
public class UserInfoNameVo {

    @ApiModelProperty(value = "id")
    private String userId;

    @ApiModelProperty(value = "上级的员工id")
    private String leaderEmpIds;

    @ApiModelProperty(value = "岗位id **外键** 对应 【position】表字段 post_id")
    private String postId;

    @ApiModelProperty(value = "员工姓名")
    private String userName;

    @ApiModelProperty(value = "员工身份证号码")
    private String idCard;

    @ApiModelProperty(value = "所属组织区域id")
    private String orgId;

    @ApiModelProperty(value = "电子邮件")
    private String email;

    @ApiModelProperty(value = "性别")
    private GenderEnum gender;

    @ApiModelProperty(value = "枚举性别类型")
    private Integer genders;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "登录密码")
    private String loginPassword;
    @ApiModelProperty(value = "是否锁定")
    private Boolean isLock;
    @ApiModelProperty(value = "登录次数")
    private Integer loginCount;
    @ApiModelProperty(value = "员工id")
    private Integer empId;
}
