package com.tul.manage.security.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @description: 角色枚举
 * @author: znegyu
 * @create: 2021-06-21 15:34
 **/
public enum RoleEnum implements IEnum<String> {
    /**
     * 角色枚举
     */
    JIN_QUE_ADMIN("jinQueAdmin", "金鹊管理员"),
    REGIONAL_ACCOUNTING("RegionalAccounting", "区域核算"),
    HEADQUARTERS_ACCOUNTING("HeadquartersAccounting","总部核算"),
    ADMIN("admin","超级管理员"),
    ORDINARY_USER("OrdinaryUser","普通用户"),
    TEST_USER("testUser","测试用户");

    RoleEnum(String code, String value) {
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
