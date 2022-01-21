package com.tul.manage.security.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * hr性别枚举
 *
 * @author zengyu
 */
public enum GenderEnum implements IEnum<Integer> {

    /**
     * 性别
     */
    MALE(760, "男"),
    FEMALE(761, "女");

    GenderEnum(int code, String value) {
        this.code = code;
        this.value=value;
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

    @JsonCreator
    public static GenderEnum getGenderToCode(Integer code){
        switch (code){
            case 760: return GenderEnum.MALE;
            case 761: return GenderEnum.FEMALE;
            default:return null;
        }
    }

}
