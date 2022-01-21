package com.tul.manage.security.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @description: 附件模块枚举
 * @author: znegyu
 * @create: 2021-06-21 15:34
 **/
public enum AnnexModuleEnum implements IEnum<String> {
    /**
     * 附件模块枚举
     */
    TICKET("ticket", "IT服务台附件","ticketAnnex"),
    RISK("risk","风险信息附件", "riskAnnex");


    AnnexModuleEnum(String code, String value,String uploadPath) {
        this.code = code;
        this.value = value;
        this.uploadPath=uploadPath;
    }


    @EnumValue
    private final String code;

    @JsonValue
    private final String value;

    private final String uploadPath;

    @Override
    public String getValue() {
        return this.code;
    }


    @Override
    public String toString() {
        return this.value;
    }

    public String getUploadPath(){
        return this.uploadPath;
    }
}
