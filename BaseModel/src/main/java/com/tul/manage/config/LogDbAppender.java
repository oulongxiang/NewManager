package com.tul.manage.config;

import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.db.DBAppenderBase;
import cn.hutool.core.util.IdUtil;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

/**
 * @description: 日志数据库库配置
 * @author: znegyu
 * @create: 2021-03-19 21:40
 **/
public class LogDbAppender extends DBAppenderBase<ILoggingEvent> {
    static final int ID_INDEX = 1;
    static final int LOG_LEVEL_INDEX = 2;
    static final int OPERATION_TIME_INDEX = 3;
    static final int OPERATOR_USER_ID_INDEX = 4;
    static final int CONTENT_INDEX = 5;
    static final int CALLER_PACKAGE_NAME_INDEX = 6;
    static final int CALLER_CLASS_NAME_INDEX = 7;
    static final int CALLER_METHOD_NAME_INDEX = 8;
    static final int CALLER_LINE_INDEX = 9;
    static final int STACK_TRACE_INDEX = 10;

    /**
     * 错误日志表获取主键的方法(没啥用处)
     */
    protected static final Method GET_GENERATED_KEYS_METHOD;

    private static final StackTraceElement EMPTY_CALLER_DATA = CallerData.naInstance();

    /**
     * 插入错误日志表的语句
     */
    protected String insertSQL;

    static {
        Method getGeneratedKeysMethod;
        try {
            getGeneratedKeysMethod = PreparedStatement.class.getMethod("getGeneratedKeys", (Class[]) null);
        } catch (Exception var2) {
            getGeneratedKeysMethod = null;
        }
        GET_GENERATED_KEYS_METHOD = getGeneratedKeysMethod;
    }

    @Override
    public void start() {
        insertSQL = "INSERT INTO system_log " +
                "(id, log_level, operation_time, operator_user_id, content," +
                "caller_package_name, caller_class_name, caller_method_name, caller_line, stack_trace)" +
                "VALUES (?, ?, ? ,?, ?, ?, ?, ?, ?, ?)";
        super.start();
    }

    /**
     * @return 主键获取方法
     */
    @Override
    protected Method getGeneratedKeysMethod() {
        return GET_GENERATED_KEYS_METHOD;
    }

    /**
     * @return 日志插入语句
     */
    @Override
    protected String getInsertSQL() {
        return insertSQL;
    }

    /**
     * 这个是日志存储到数据库的主要方法
     *
     * @param iLoggingEvent     日志事件
     * @param connection        连接信息
     * @param preparedStatement 用于执行sql的对象
     * @throws Throwable 异常
     */
    @Override
    protected void subAppend(ILoggingEvent iLoggingEvent, Connection connection, PreparedStatement preparedStatement) throws Throwable {
        String level = iLoggingEvent.getLevel().toString();
        //先查看是否有传入的出错详细堆栈信息，有的话直接拿来用,没有则说明非运行时异常，不需要入库
        if (iLoggingEvent.getArgumentArray() != null && iLoggingEvent.getArgumentArray().length > 0) {
            if (iLoggingEvent.getArgumentArray()[0] instanceof List) {
                List<Object> mordStackTraceElement = (List<Object>) iLoggingEvent.getArgumentArray()[0];
                if (mordStackTraceElement.size() > 0) {
                    Object oneStackTraceElement = mordStackTraceElement.get(0);
                    if (oneStackTraceElement instanceof StackTraceElement) {
                        //错误日志
                        StackTraceElement stackTraceElement = (StackTraceElement) oneStackTraceElement;
                        preparedStatement.setString(CALLER_PACKAGE_NAME_INDEX, stackTraceElement.getClassName());
                        preparedStatement.setString(CALLER_CLASS_NAME_INDEX, stackTraceElement.getFileName());
                        preparedStatement.setString(CALLER_METHOD_NAME_INDEX, stackTraceElement.getMethodName());
                        preparedStatement.setInt(CALLER_LINE_INDEX, stackTraceElement.getLineNumber());
                    }
                    //判断该错误日志是否有操作人
                    if (mordStackTraceElement.size() > 1) {
                        Object userId = mordStackTraceElement.get(1);
                        Object stackTrace = mordStackTraceElement.get(2);
                        if (stackTrace instanceof String) {
                            String stackTraceNew = (String) stackTrace;
                            preparedStatement.setString(STACK_TRACE_INDEX, stackTraceNew);
                        } else {
                            preparedStatement.setString(STACK_TRACE_INDEX, null);
                        }
                        if (userId instanceof String) {
                            String operatorUserId = (String) userId;
                            preparedStatement.setString(OPERATOR_USER_ID_INDEX, operatorUserId);
                        } else {
                            preparedStatement.setString(OPERATOR_USER_ID_INDEX, null);
                        }
                    }
                }
            }
        } else {
            return;
        }
        preparedStatement.setString(ID_INDEX, IdUtil.objectId());
        preparedStatement.setString(LOG_LEVEL_INDEX, level);
        preparedStatement.setTimestamp(OPERATION_TIME_INDEX, new Timestamp(iLoggingEvent.getTimeStamp()));
        preparedStatement.setString(CONTENT_INDEX, iLoggingEvent.getMessage());
        int updateCount = preparedStatement.executeUpdate();
        if (updateCount != 1) {
            this.addWarn("日志插入数据库失败!");
        }
    }

    private StackTraceElement extractFirstCaller(StackTraceElement[] callerDataArray) {
        StackTraceElement caller = EMPTY_CALLER_DATA;
        if (hasAtLeastOneNonNullElement(callerDataArray)) {
            caller = callerDataArray[0];
        }
        return caller;
    }

    private boolean hasAtLeastOneNonNullElement(StackTraceElement[] callerDataArray) {
        return callerDataArray != null && callerDataArray.length > 0 && callerDataArray[0] != null;
    }

    @Override
    protected void secondarySubAppend(ILoggingEvent iLoggingEvent, Connection connection, long l) throws Throwable {

    }

}
