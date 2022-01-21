package com.tul.manage.security.vo.response;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tul.manage.security.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 前端用户管理vo
 * @author: znegyu
 * @create: 2021-01-23 14:14
 **/
@Data
public class UserInfoVo {
    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "id")
    private String userId;

    @ApiModelProperty(value = "登录账号")
    private String accountNumber;

    @JsonIgnore
    @ApiModelProperty(value = "登录密码")
    private String loginPassword;

    @ApiModelProperty(value = "组织id **外键** 对应 【organization】表字段 org_id")
    private String orgId;

    @ApiModelProperty(value = "列表显示的组织机构名称")
    private String orgName;

    @ApiModelProperty(value = "员工号")
    private String empCode;

    @ApiModelProperty(value = "员工id")
    private Integer empId;

    @ApiModelProperty(value = "上级的员工id")
    private Integer leaderEmpId;

    @ApiModelProperty(value = "岗位id **外键** 对应 【position】表字段 post_id")
    private String postId;

    @ApiModelProperty(value = "用户身份证号")
    private String idCard;

    @ApiModelProperty(value = "用户名(这里是员工名)")
    private String userName;

    @ApiModelProperty(value = "性别")
    private GenderEnum gender;

    @ApiModelProperty(value = "联系电话")
    private String mobile;

    @ApiModelProperty(value = "电子邮件")
    private String email;

    @ApiModelProperty(value = "账号是否被锁定")
    private Boolean isLock;

    @ApiModelProperty(value = "KK(智慧联邦)登录账号")
    private String ssoCode;

    @ApiModelProperty(value = "上次登录时间")
    private LocalDateTime loginTime;

    @ApiModelProperty(value = "登录次数")
    private Integer loginCount;

    @ApiModelProperty(value = "岗位名称")
    private String postName;

    @ApiModelProperty(value = "负责组织区域")
    private String responsibleOrgName;

    @ApiModelProperty(value = "拥有角色名称列表")
    private List<String> roleNames;

}
