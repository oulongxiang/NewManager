package com.tul.manage.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tul.manage.security.enums.LoginTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 登录记录表
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserInfoLoginRecord对象", description="登录记录表")
public class UserInfoLoginRecord extends Model<UserInfoLoginRecord> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id 主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "登录的用户id **外键** 对应 用户表【user_info】字段 【id】")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "登录类型 【sso -单点登录 】【account -账号密码登录】")
    @TableField("login_type")
    private LoginTypeEnum loginType;

    @ApiModelProperty(value = "使用的浏览器")
    @TableField("browser")
    private String browser;

    @ApiModelProperty(value = "使用的操作系统")
    @TableField("operating_system")
    private String operatingSystem;

    @ApiModelProperty(value = "ip地址")
    @TableField("ip_address")
    private String ipAddress;

    @ApiModelProperty(value = "创建时间(相当于登录时间)")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "逻辑删除")
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
