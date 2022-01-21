package com.tul.manage.config;

import cn.hutool.core.util.StrUtil;
import com.p6spy.engine.spy.appender.StdoutLogger;

/**
 * @description: 自定义p6spy日志打印类
 * @author: znegyu
 * @create: 2021-08-06 13:55
 **/
public class P6SpyLogger extends StdoutLogger {
    public P6SpyLogger() {
    }

    @Override
    public void logText(String text) {
        if(!StrUtil.isBlank(text)){
            System.err.println(text);
        }
    }
}
