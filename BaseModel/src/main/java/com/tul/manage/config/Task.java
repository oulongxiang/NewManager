package com.tul.manage.config;

import com.tul.manage.security.dao.SystemLogDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 定时任务
 * @author: znegyu
 * @create: 2021-06-09 13:15
 **/
@Configuration
@EnableScheduling
@Slf4j
public class Task {

    @Resource
    private SystemLogDao systemLogDao;

    /**
     * 每天中午12点定时清除3个月之前的系统日志 (物理删除)
     */
    @Scheduled(cron = "0 0 12 * * ? ")
    public void delSysLog() {
        try{
            systemLogDao.taskDelSysLog();
        }catch (Exception e){
            log.error(e.getLocalizedMessage(), getOneStackTraceElementAndOtherMessage(e));
        }

    }


    private List<Object> getOneStackTraceElementAndOtherMessage(Exception e) {
        List<Object> result = new ArrayList<>();
        StackTraceElement stackTraceElement = null;
        if (e.getStackTrace().length > 0) {
            stackTraceElement = e.getStackTrace()[0];
        }
        result.add(stackTraceElement);
        result.add("task");
        result.add(ExceptionUtils.getStackTrace(e));
        return result;
    }
}
