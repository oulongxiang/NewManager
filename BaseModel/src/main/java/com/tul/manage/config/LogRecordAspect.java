package com.tul.manage.config;

import apps.commons.util.tool_util.HttpKit;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.tul.manage.security.config.IgnoreOperationLog;
import com.tul.manage.security.config.JwtUtil;
import com.tul.manage.security.config.LoginMethod;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangxianghua  2020年10月29日 下午16:19:07 周四
 * @description 定义请求切面，请求来的时候，会把请求写入控制台和日志
 * <br /> 被拦截的请求将不会写入，例如权限不足或者异常处理
 */
@Aspect
@Component
@Slf4j
public class LogRecordAspect {
    private long beforeTime;

    @Resource
    private JwtUtil jwtUtil;

    @Value("${spring.profiles.active}")
    private String env;

    /**
     * 扫描路径
     */
    private final String pointcut = "execution(* com.tul.manage.*.*.*Controller.*(..)) && !execution(* com.tul.manage.security.controller.LoginController.logout(..))";

    @Value("${spring.datasource.dynamic.datasource.admin.url}")
    public String url;

    @Value("${spring.datasource.dynamic.datasource.admin.username}")
    public String username;

    @Value("${spring.datasource.dynamic.datasource.admin.password}")
    public String password;

    @Value("${spring.datasource.dynamic.datasource.admin.driver-class-name}")
    public String driver;

    private String  insertLoginSQL = "INSERT INTO user_info_login_record " +
            "(id, user_id, login_type, browser, operating_system,ip_address,create_time,deleted)" +
            "VALUES (?, ?, ? ,?, ?, ?, ?, ?)";

    private String  insertOperationQL = "INSERT INTO operation_log " +
            "(id, user_id, request_type, browser, operating_system,ip_address,create_time,deleted,operation,request_param)" +
            "VALUES (?, ?, ? ,?, ?, ?, ?, ?,?,?)";

    private static Connection connection=null;

    public Connection getConnection()
    {
        if(connection==null){
            try {
                Class.forName(driver);
                connection= DriverManager.getConnection(url,username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * 定义切点Pointcut
     */
    @Pointcut(value = pointcut)
    public void excudeService() {
    }

    @Before("excudeService()")
    public void doBefore(JoinPoint joinPoint) {
        //开始计时
        beforeTime = System.currentTimeMillis();
    }

    @SuppressWarnings("unchecked")
    @AfterReturning(returning = "ret", pointcut = "excudeService()")
    public void doAfterReturning(JoinPoint joinPoint,Object ret) {
        //开发环境不记录日志
        String dev="dev";
        if(dev.equals(env)){
            return;
        }
        //方法swagger 注释
        String apiOperationValue = "无";
        Class<?> aClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        String loginType = null;
        IgnoreOperationLog ignoreOperationLogToController = aClass.getAnnotation(IgnoreOperationLog.class);
        IgnoreOperationLog ignoreOperationLogToMethod=null;
        //切入的日志是否登录方法
        boolean isLogin = false;
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                ApiOperation annotation = method.getAnnotation(ApiOperation.class);
                ignoreOperationLogToMethod = method.getAnnotation(IgnoreOperationLog.class);
                LoginMethod loginMethod = method.getAnnotation(LoginMethod.class);
                if(loginMethod!=null && !StrUtil.isBlank(loginMethod.value())){
                    isLogin=true;
                    loginType=loginMethod.value();
                }
                if(annotation!=null){
                    apiOperationValue =annotation.value();
                    break;
                }
            }
        }
        boolean isIgnoreOperationLog = ignoreOperationLogToController!=null ||  ignoreOperationLogToMethod!=null;
        JSONObject parse = (JSONObject) JSONObject.toJSON(ret);
        String code =parse==null||parse.get("code")==null?"200":parse.get("code").toString();
        String userId="无";
        if(isLogin){
            try {
                assert parse != null;
                Map<String, Object> data = (Map<String, Object>) parse.get("data");
                Map<String, Object> userInfo = (Map<String, Object>) data.get("userInfo");
                userId = userInfo.get("id").toString();
            }catch (Exception ignored){}
        }else{
            userId=jwtUtil.getUserId();
        }
        HttpServletRequest request = HttpKit.getRequest();
        //打印请求的内容
        assert request != null;
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String browser=userAgent.getBrowser()==null?"未知浏览器":userAgent.getBrowser().toString();
        String browserVersion=userAgent.getBrowserVersion()==null?"未知浏览器版本":userAgent.getBrowserVersion().toString();
        String operatingSystem=userAgent.getOperatingSystem()==null?"未知操作系统":userAgent.getOperatingSystem().toString();
        //记录登录日志
        if(isLogin){
            saveLoginMessage(userId,
                    loginType,
                    browser+" - "+browserVersion,
                    operatingSystem,
                    request.getRemoteAddr());
        }else{
            //记录操作日志
            if (!isIgnoreOperationLog) {
                //请求参数json
                String requestParam;
                if("GET".equals(request.getMethod()) || "DELETE".equals(request.getMethod())){
                    requestParam= JSONUtil.toJsonStr(request.getParameterMap());
                }else{
                    requestParam=JSONUtil.toJsonStr(getNameAndValue(joinPoint));
                }
                if("{}".equals(requestParam)){
                    requestParam="";
                }
                saveOperationMessage(userId,
                        request.getMethod(),
                        browser+" - "+browserVersion,
                        operatingSystem,
                        request.getRemoteAddr(),apiOperationValue,
                        requestParam);
            }
        }
        StringBuilder head=new StringBuilder("["+userId+"] ");
        head.append("[").append(apiOperationValue).append("] ");
        StringBuilder info = HttpKit.getRequestRecordInfo(userAgent,request,code);
        info.append("[")
                .append(System.currentTimeMillis() - beforeTime)
                .append("ms]");
        head.append(info);
        //处理完请求后，返回内容
        log.info(head.toString());
    }

    /**
     * 记录登录成功请求到登录记录表
     * @param userId 用户id
     * @param loginType 登录类型 sso -【 单点登录 】【account -账号密码登录】
     * @param browser 浏览器信息
     * @param operatingSystem 操作系统信息
     * @param ipAddress ip地址
     */
    private void saveLoginMessage(String userId,String loginType,String browser,String operatingSystem,String ipAddress){
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(insertLoginSQL);
            preparedStatement.setString(1, IdUtil.objectId());
            preparedStatement.setString(2,  userId);
            preparedStatement.setString(3, loginType);
            preparedStatement.setString(4, browser);
            preparedStatement.setString(5,  operatingSystem);
            preparedStatement.setString(6, ipAddress);
            preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(8,false);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录请求到操作日志表
     * @param userId 用户id
     * @param requestType 请求类型
     * @param browser 浏览器信息
     * @param operatingSystem 操作系统信息
     * @param ipAddress ip地址
     * @param operation 操作
     * @param requestParam 请求参数
     */
    private void saveOperationMessage(String userId,String requestType,String browser,String operatingSystem,String ipAddress,String operation,String requestParam){
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(insertOperationQL);
            preparedStatement.setString(1, IdUtil.objectId());
            preparedStatement.setString(3, requestType);
            preparedStatement.setString(2,  userId);
            preparedStatement.setString(4, browser);
            preparedStatement.setString(5,  operatingSystem);
            preparedStatement.setString(6, ipAddress);
            preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(8,false);
            preparedStatement.setString(9, operation);
            preparedStatement.setString(10, requestParam);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取参数Map集合
     * @param joinPoint
     * @return
     */
    Map<String, Object> getNameAndValue(JoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>();
        try {
            Object[] paramValues = joinPoint.getArgs();
            String[] paramNames = ((CodeSignature)joinPoint.getSignature()).getParameterNames();
            for (int i = 0; i < paramNames.length; i++) {
                param.put(paramNames[i], paramValues[i]);
            }
            return param;
        }catch (Exception e){
            return param;
        }
    }

}