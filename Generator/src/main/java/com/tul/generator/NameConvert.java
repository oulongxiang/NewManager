package com.tul.generator;

import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.google.common.base.CaseFormat;

/**
 * @description: 名字格式化规则
 * @author: znegyu
 * @create: 2021-05-08 17:28
 **/
public class NameConvert implements INameConvert {

    /**
     * 数据库表名转实体类名字规则
     * @param tableInfo 表信息
     * @return 生成的实体类名字
     */
    @Override
    public String entityNameConvert(TableInfo tableInfo) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,tableInfo.getName());
    }

    /**
     * 数据库字段名转实体类字段名字规则
     * @param field 字段信息
     * @return 生成得实体属性名字
     */
    @Override
    public String propertyNameConvert(TableField field) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, field.getName());
    }

}
