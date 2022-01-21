package com.tul.manage.config;

import cn.hutool.db.sql.SqlFormatter;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @description: SQL分析打印工具(不能用于生产环境)
 * @author: znegyu
 * @create: 2020-12-14 09:03
 **/
public class P6SpyConfig implements MessageFormattingStrategy {


    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        String sqlFormat = SqlFormatter.format(sql);
        String quartzKeyword2 = "PRIORITY DESC";
        String quartzKeyword1 = "NEXT_FIRE_TIME ASC";
        if(sqlFormat.contains(quartzKeyword1) && sqlFormat.contains(quartzKeyword2)){
            return "";
        }
        String quartzKeyword3 = "MISFIRE_INSTR = -1";
        if(sqlFormat.contains(quartzKeyword3)){
            return "";
        }
        return StringUtils.isNotBlank(sql) ? " 执行SQL消耗时间：" +
                elapsed + " 毫秒 " + "执行时间: " + now + "\n 执行的 SQL 语句：" +
                sqlFormat + "\n" : "";
    }
}
