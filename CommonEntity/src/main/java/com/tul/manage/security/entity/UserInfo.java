package com.tul.manage.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tul.manage.security.enums.GenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 金鹊管理系统用户表
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserInfo对象", description="金鹊管理系统用户表")
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户id(主键-使用KK登录账号)")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "登录账号")
    @TableField("account_number")
    private String accountNumber;

    @ApiModelProperty(value = "登录密码")
    @TableField("login_password")
    private String loginPassword;

    @ApiModelProperty(value = "组织id **外键** 对应 【organization】表字段 org_id")
    @TableField("org_id")
    private String orgId;

    @ApiModelProperty(value = "员工号")
    @TableField("emp_code")
    private String empCode;

    @ApiModelProperty(value = "员工id")
    @TableField("emp_id")
    private Integer empId;

    @ApiModelProperty(value = "上级的员工id")
    @TableField(value = "leader_emp_id")
    private Integer leaderEmpId;

    @ApiModelProperty(value = "上级结构层级 根据 emp_id 逗号隔开")
    @TableField("emp_id_hierarchy")
    private String empIdHierarchy;

    @ApiModelProperty(value = "岗位id **外键** 对应 【position】表字段 post_id")
    @TableField("post_id")
    private String postId;

    @ApiModelProperty(value = "用户身份证号")
    @TableField("id_card")
    private String idCard;

    @ApiModelProperty(value = "用户名(这里是员工名)")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "性别")
    @TableField("gender")
    private GenderEnum gender;

    @ApiModelProperty(value = "联系电话")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "电子邮件")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "账号是否被锁定")
    @TableField("is_lock")
    private Boolean isLock;

    @ApiModelProperty(value = "KK(智慧联邦)登录账号")
    @TableField("sso_code")
    private String ssoCode;

    @ApiModelProperty(value = "上次登录时间")
    @TableField("login_time")
    private LocalDateTime loginTime;

    @ApiModelProperty(value = "登录次数")
    @TableField("login_count")
    private Integer loginCount;

    @ApiModelProperty(value = "是否删除")
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建人 user_id")
    @TableField("create_user_id")
    private String createUserId;

    @ApiModelProperty(value = "修改人 user_id")
    @TableField("update_user_id")
    private String updateUserId;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public UserInfo self(){
        return this;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
