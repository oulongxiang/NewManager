package com.tul.manage.warning.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @description: 预警状态枚举
 * @author: znegyu
 * @create: 2021-06-21 15:34
 **/
public enum RuleStatusEnum implements IEnum<Integer> {
    /**
     * 预警状态枚举
     */
    USE(1, "生效"),
    STOP(0, "停用"),
    ABANDONED(-1,"废弃");

    RuleStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }


    @EnumValue
    private final Integer code;

    @JsonValue
    private final String value;

    @Override
    public Integer getValue() {
        return this.code;
    }


    @Override
    public String toString() {
        return this.value;
    }
}
