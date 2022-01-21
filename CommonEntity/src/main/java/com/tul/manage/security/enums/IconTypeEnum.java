package com.tul.manage.security.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 图标类型枚举
 *
 * @author zengyu
 */
public enum IconTypeEnum implements IEnum<String> {
    /**
     * 图标类型
     */
    ALIBABA("alibaba-default", "阿里图标"),
    CUSTOM("custom", "自定义图标"),
    EL("el", "饿了么图标");

    IconTypeEnum(String code, String value) {
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
