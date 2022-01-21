package com.tul.manage.config;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.tul.manage.security.config.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;


/**
 * @description: Mybatis拦截器
 * @author: znegyu
 * @create: 2021-03-26 16:46
 **/
@Intercepts({
        @Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class})})
@Slf4j
public class MybatisInterceptor implements Interceptor {

    @Resource
    private JwtUtil jwtUtil;
    @Value("${spring.datasource.dynamic.datasource.admin.url}")
    public String url;

    @Value("${spring.datasource.dynamic.datasource.admin.username}")
    public String username;

    @Value("${spring.datasource.dynamic.datasource.admin.password}")
    public String password;

    @Value("${spring.datasource.dynamic.datasource.admin.driver-class-name}")
    public String driver;

    private String  insertSQL = "INSERT INTO system_log " +
            "(id, log_level, operation_time, operator_user_id, content," +
            "caller_package_name, caller_class_name, caller_method_name, caller_line, stack_trace)" +
            "VALUES (?, ?, ? ,?, ?, ?, ?, ?, ?, ?)";

    private static Connection connection=null;

    public Connection getConnection()
    {
        if(connection==null){
            try {
                Class.forName(driver);
                connection=DriverManager.getConnection(url,username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        BoundSql boundSql = statementHandler.getBoundSql();
        boolean isSave = true;
        String logLevel = "";
        switch (mappedStatement.getSqlCommandType()) {
            case INSERT:
                logLevel = "INSERT";
                break;
            case DELETE:
                logLevel = "DELETE";
                break;
            case UPDATE:
                logLevel = "UPDATE";
                //这里判断下逻辑删除
                if (boundSql.getSql().contains("SET is_delete=1") || boundSql.getSql().contains("SET deleted=1")) {
                    logLevel = "DELETE";
                }
                break;
            default:
                isSave = false;
        }
        if (isSave) {
            String userId = null;
            try {
                userId = jwtUtil.getUserId();
            } catch (Exception e) {
                //log.warn("捕获到的增删改操作无操作人");
            }
            Configuration configuration = mappedStatement.getConfiguration();
            PreparedStatement preparedStatement=getConnection().prepareStatement(insertSQL);
            preparedStatement.setString(1, IdUtil.objectId());
            preparedStatement.setString(2, logLevel);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(4, userId);
            preparedStatement.setString(5, showSql(configuration, boundSql));
            preparedStatement.setString(6, mappedStatement.getId());
            preparedStatement.setString(7, null);
            preparedStatement.setString(8, null);
            preparedStatement.setString(9, null);
            preparedStatement.setString(10, null);
            preparedStatement.executeUpdate();
        }
        return invocation.proceed();

    }

    @Override
    public Object plugin(Object target) {
        return target instanceof StatementHandler ? Plugin.wrap(target, this) : target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 参数处理
     * 如果参数是String，则添加单引号， 如果是日期，则转换为时间格式器并加单引号； 对参数是null和不是null的情况作了处理
     *
     * @param obj sql 参数
     * @return 处理后参数
     */
    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                    DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    private boolean isLogicalDeletion(String sql) {
        return sql.contains("SET is_delete=1") || sql.contains("SET deleted=1");
    }

    /**
     * 获取完整sql语句
     *
     * @param configuration configuration
     * @param boundSql      原始sql
     * @return 完整sql语句
     */
    public static String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?",
                        Matcher.quoteReplacement(getParameterValue(parameterObject)));

            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        // 打印出缺失，提醒该参数缺失并防止错位
                        sql = sql.replaceFirst("\\?", "缺失");
                    }
                }
            }
        }
        return sql;
    }

}
