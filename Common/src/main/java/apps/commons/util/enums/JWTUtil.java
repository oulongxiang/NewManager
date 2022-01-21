package apps.commons.util.enums;

import apps.commons.util.tool_util.HttpKit;
import apps.commons.util.tool_util.StrKit;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.lettuce.core.RedisBusyException;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: JWT工具类
 * @author: znegyu
 * @create: 2020-11-18 14:44
 **/
public class JWTUtil {

    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    public static String getUserId(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (!StrKit.isBlank(bearerToken)) {
            if(bearerToken.startsWith("Bearer "))
            {
                bearerToken = bearerToken.substring(7);
            }
            String userId = getUserId(bearerToken);
            return userId;
        }else{
            throw new RedisBusyException("用户token不正确，请重新登录");
        }
    }

    public static String getUserId(){
        String bearerToken = HttpKit.getRequest().getHeader("Authorization");
        if (!StrKit.isBlank(bearerToken)) {
            String userId = getUserId(bearerToken);
            return userId;
        }else{
            throw new RedisBusyException("用户token不正确，请重新登录");
        }
    }
}
