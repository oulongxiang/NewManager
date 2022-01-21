package com.tul.manage.security.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @description: 登录方式枚举
 * @author: znegyu
 * @create: 2021-06-21 15:34
 **/
public enum LoginTypeEnum implements IEnum<String> {
    /**
     * 登录方式
     */
    ACCOUNT("account", "账号密码登录"),
    KK_BROADCAST("sso", "单点登录");

    LoginTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }


    @EnumValue
    private final String code;

    @JsonValue
    private final String value;

    @Override
    public String getValue() {
        return this.code;
    }


    @Override
    public String toString() {
        return this.value;
    }
}
