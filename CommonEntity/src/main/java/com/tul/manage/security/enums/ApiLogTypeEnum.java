package com.tul.manage.security.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @description: 第三方接口日志类型
 * @author: znegyu
 * @create: 2021-06-21 15:34
 **/
public enum ApiLogTypeEnum implements IEnum<String> {
    /**
     * 菜单类型
     */
    KK_MESSAGE_PUSH("kkMessagePush", "KK消息推送"),
    KK_BROADCAST("kkBroadcast", "KK广播推送"),
    RUN_REVIEW("runReview","启动流程"),
    SEND_MAIL("sendMail","发送邮件"),
    OCR_VAT_INVOICE("creVatInvoice","百度增值税发票识别");

    ApiLogTypeEnum(String code, String value) {
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
