package com.tul.manage.security.config;

import lombok.Data;

/**
 * @description: 账号密码登录传输对象
 * @author: znegyu
 * @create: 2021-01-12 15:43
 **/
@Data
public class LoginBean {
    private String accountNumber;
    private String loginPassword;
}
