package com.tul.manage.security.vo.response;

import com.tul.manage.security.enums.LoginTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description 登录日志VO
 * @author zengyu
 * @create 2021-08-13 08:49
 **/
@Data
public class LoginLogVo {
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "登录类型")
    private LoginTypeEnum loginType;
    @ApiModelProperty(value = "浏览器")
    private String browser;
    @ApiModelProperty(value = "操作系统")
    private String operatingSystem;
    @ApiModelProperty(value = "ip地址")
    private String ipAddress;
    @ApiModelProperty(value = "创建时间(等同于登录时间)")
    private LocalDateTime createTime;
}
