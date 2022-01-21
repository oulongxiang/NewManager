package com.tul.manage.security.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 菜单类型枚举
 *
 * @author zengyu
 */
public enum MenuTypeEnum implements IEnum<String> {
    /**
     * 菜单类型
     */
    PC("pc", "电脑端"),
    MOBILE("mobile", "移动端");

    MenuTypeEnum(String code, String value) {
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
