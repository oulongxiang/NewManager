package com.tul.generator.support;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 数据库类型
 * @author zengyu
 * @date 2021-08-26 13:38
 **/
public enum DbTypeEnum implements IEnum<String> {

    /***
     * 数据库类型
     */
    MYSQL("mysql","mysql"),
    SQL_SERVER("sqlserver","sqlserver");

    DbTypeEnum(String code, String value) {
        this.code = code;
        this.value=value;
    }


    @JsonValue
    @EnumValue
    private final String code;
    private final String value;

    @Override
    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static DbTypeEnum getTimerStatusToCode(String code){
        switch (code){
            case "mysql": return MYSQL;
            case "sqlserver": return SQL_SERVER;
            default:return null;
        }
    }

}
