package apps.commons.util.enums;

import cn.hutool.core.map.MapUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 枚举工具类
 * @author: znegyu
 * @create: 2021-07-09 21:50
 **/
public class EnumUtil {

    /**
     * 枚举转 List
     * @param enumClass 枚举
     * @return mapList
     */
    public static List<Map<String,Object>> getEnumMapList(Class<?> enumClass){
        List<Map<String,Object>> result = new ArrayList<>();
        try {
            Object[] objects = enumClass.getEnumConstants();
            Method getValue = enumClass.getMethod("getValue");
            Method toString = enumClass.getMethod("toString");
            Class<?> returnType = getValue.getReturnType();
            for (Object object : objects) {
                Map<String,Object> map = MapUtil.newHashMap();
                String code = getValue.invoke(object).toString();
                String info = toString.invoke(object).toString();
                map.put("value",info);
                map.put("code",returnType.equals(Integer.class)?Integer.valueOf(code):code);
                result.add(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
