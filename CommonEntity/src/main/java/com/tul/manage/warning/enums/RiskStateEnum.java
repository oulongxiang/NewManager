package com.tul.manage.warning.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RiskStateEnum implements IEnum<Integer> {
    /**
     * 风险状态枚举
     */
    HANDLEING(1, "处理中"),
    NOTHANDLE(0, "未处理"),
    FINISH(2,"已处理");

    RiskStateEnum(Integer code, String value) {
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
