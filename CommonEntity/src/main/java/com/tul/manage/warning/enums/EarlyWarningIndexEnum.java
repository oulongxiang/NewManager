package com.tul.manage.warning.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 *  预警指标枚举
 * @author zengyu
 * @date 2021-11-04 15:14
 **/
public enum EarlyWarningIndexEnum implements IEnum<String> {

    /**
     * 工龄销量相关
     */
    WORKING_YEARS_0("0", "连续3个月销量下降"),
    WORKING_YEARS_1("1", "工龄>1且3个月销量<6000"),
    WORKING_YEARS_2("2","连续3个月销量下降超过15%"),
    WORKING_YEARS_3("3","连续6个月平均销量低于6W(非胰岛素)"),
    WORKING_YEARS_4("4","连续6个月平均销量低于4W(胰岛素)"),
    WORKING_YEARS_5("5","销售连续3个月每月下降超过5%"),
    WORKING_YEARS_6("6","连续3-6个月平均销量低于4万"),
    WORKING_YEARS_7("7","连续12个月以上平均销量低于4万"),
    WORKING_YEARS_8("8","连续6-12个月平均销量低于4万"),
    WORKING_YEARS_9("9","销售连续3个月每月下降超过20%");

    @EnumValue
    private final String code;

    @JsonValue
    private final String value;

    EarlyWarningIndexEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getValue() {
        return code;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
