package com.tul.manage.security.config;

import apps.commons.exception.ServiceException;
import apps.commons.util.enums.BizExceptionEnum;
import apps.commons.util.tool_util.HttpKit;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @description: Jwt工具类
 * @author: znegyu
 * @create: 2020-11-23 15:34
 **/
@Component
public class JwtUtil {
    /**
     * Token过期时间
     */
    @Value("${project.jwt.EXPIRE_TIME}")
    private Long EXPIRE_TIME;

    /**
     * 长Token过期时间
     */
    @Value("${project.jwt.LONG_EXPIRE_TIME}")
    private Long LONG_EXPIRE_TIME;

    /**
     * Token加密密钥
     */
    @Value("${project.jwt.SECRET}")
    private String SECRET;

    /**
     * 长Token加密密钥
     */
    @Value("${project.jwt.LONG_SECRET}")
    private String LONG_SECRET;

    /**
     * 发行人
     */
    @Value("${project.jwt.ISSUER}")
    private String ISSUER;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 创建加密Token
     *
     * @param userId -用户身Id
     * @return -加密后的Token
     */
    public String createToken(String userId) {
        if (userId == null || "".equals(userId.trim())) {
            throw new ServiceException(BizExceptionEnum.TOKEN_ERROR);
        }
        Algorithm algorithm = getAlgorithm(SECRET);
        //创建和签名令牌的Token构建器
        String token = JWT.create()
                //设置发布者
                .withIssuer(ISSUER)
                //添加自定义内容 (这里为添加userId)
                .withClaim("uniqueIdentifier",userId)
                //生成时间
                .withIssuedAt(new Date())
                //到期时间
                //.withExpiresAt(date) //这里不设置到期,通过Redis来判断是否到期
                //指定算法进行签名
                .sign(algorithm);
        //将Token放在Redis上,并设置到期时间
        redisTemplate.opsForValue().set("JWTCacheInAuditSystem::" + userId, token, EXPIRE_TIME, TimeUnit.HOURS);
        //设置长Token
        createLongToken(userId);
        return token;
    }

    /**
     * 创建长token
     *
     * @param uniqueIdentifier 用户唯一标识符
     */
    private void createLongToken(String uniqueIdentifier) {
        Algorithm algorithm = getAlgorithm(LONG_SECRET);
        //创建和签名令牌的Token构建器
        String token = JWT.create()
                //设置发布者
                .withIssuer(ISSUER)
                //添加自定义内容 (这里为添加userId)
                .withClaim("uniqueIdentifier", uniqueIdentifier)
                //生成时间
                .withIssuedAt(new Date())
                //到期时间
                //这里不设置到期,通过Redis来判断是否到期
                //.withExpiresAt(date)
                //指定算法进行签名
                .sign(algorithm);
        //将Token放在Redis上,并设置到期时间
        redisTemplate.opsForValue().set("JWTCacheInAuditSystem_LONG::" + uniqueIdentifier, token, LONG_EXPIRE_TIME, TimeUnit.HOURS);
    }

    /**
     * 校验token
     *
     * @param token 要校验的Token
     * @return -校验成功时 返回自定义userId
     */
    public String verify(String token) {
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(BizExceptionEnum.UN_AUTH_ERROR);
        }
        Algorithm algorithm = getAlgorithm(SECRET);
        //创建带有用于验证令牌签名的算法的构建器
        //设置签名
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        //验证 token
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(BizExceptionEnum.TOKEN_ERROR);
        }
        String result=null;
        //验证通过后获取用户唯一标识符(这里的唯一标识符为用户身份证)和用户id
        try{
            result = jwt.getClaims().get("uniqueIdentifier").asString();
        }catch (Exception e){
            throw new ServiceException(BizExceptionEnum.TOKEN_CHECK);
        }
        //通过redis判断是否过期
        String tokenRedis = (String) redisTemplate.opsForValue().get("JWTCacheInAuditSystem::" + result);
        if (tokenRedis == null) {
            //token过期后,判断长token是否过期
            String longTokenRedis = (String) redisTemplate.opsForValue().get("JWTCacheInAuditSystem_LONG::" + result);
            if (longTokenRedis == null) {
                //token过期
                throw new ServiceException(BizExceptionEnum.TOKEN_LONGTIMENOOPERATION);
            }
            //长Token未过期
            if (longTokenVerify(longTokenRedis)) {
                //刷新token和长token时间
                redisTemplate.opsForValue().set("JWTCacheInAuditSystem::" + result, token, EXPIRE_TIME, TimeUnit.HOURS);
                redisTemplate.opsForValue().set("JWTCacheInAuditSystem_LONG::" + result, longTokenRedis, LONG_EXPIRE_TIME, TimeUnit.HOURS);
                return result;
            } else {
                //token过期
                throw new ServiceException(BizExceptionEnum.TOKEN_LONGTIMENOOPERATION);
            }
        }
        //账号在其他地方登录(2021/5/31 zengyu 取消踢掉线功能)
//        if (!token.equals(tokenRedis)) {
//            throw new ServiceException(BizExceptionEnum.TOKEN_LOGINERROR);
//        }

        return result;
    }

    private boolean longTokenVerify(String longToken) {
        Algorithm algorithm = getAlgorithm(LONG_SECRET);
        //创建带有用于验证令牌签名的算法的构建器设置签名
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        //验证 token
        DecodedJWT jwt;
        try {
            jwt = verifier.verify(longToken);
        } catch (JWTVerificationException e) {
            return false;
        }
        //验证通过后获取用户唯一标识符
        String uniqueIdentifier = jwt.getClaims().get("uniqueIdentifier").asString();
        if (uniqueIdentifier == null || "".equals(uniqueIdentifier.trim())) {
            return false;
        }
        //通过redis判断是否过期
        String tokenRedis = (String) redisTemplate.opsForValue().get("JWTCacheInAuditSystem_LONG::" + uniqueIdentifier);
        if (tokenRedis == null) {
            return false;
        }
        return longToken.equals(tokenRedis);
    }


    /**
     * 通过密钥生成签名
     *
     * @param secret -密钥
     * @return 签名对象
     */
    private Algorithm getAlgorithm(String secret) {
        //使用HMAC256算法生成签名
        Algorithm algorithm;
        try {
            algorithm = Algorithm.HMAC256(secret);
            return algorithm;
        } catch (UnsupportedEncodingException e) {
            throw new ServiceException(BizExceptionEnum.TOKEN_ALGORITHM_ERROR);
        }
    }

    /**
     * 获取用户id
     * @return 用户id
     */
    public String getUserId(){
        String token = Objects.requireNonNull(HttpKit.getRequest()).getHeader("Authorization");
        if(StrUtil.isBlank(token)){
            return "无";
        }
        return verify(token);
    }

}
