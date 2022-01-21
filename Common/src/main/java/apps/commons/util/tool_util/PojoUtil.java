package apps.commons.util.tool_util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 实体bean工具类（PO<-->VO）
 */
public class PojoUtil extends BeanUtils {

    private final static Logger logger = LoggerFactory.getLogger(PojoUtil.class);

    /**
     * 复制List中的对象转换T
     *
     * @param list
     * @param clazz
     * @return
     */
    public static <T> List<T> copyProperties(List<?> list, Class<T> clazz) {
        if (null != list && !list.isEmpty()) {
            List<T> result = new ArrayList<T>();
            for (Object obj : list) {
                T t = BeanUtils.instantiateClass(clazz);
                BeanUtils.copyProperties(obj, t);
                result.add(t);
            }
            return result;
        }
        return null;
    }

    /**
     * Bean拷贝，lambda表达式排除指定属性名称
     *
     * @param source
     * @param target
     * @param func
     */
    @SafeVarargs
    public static <T> void copyProperties(Object source, Object target, MFunction<T, ?>... func) {
        String[] fieldNames = FunctionUtil.getFieldNames(func);
        copyProperties(source, target, fieldNames);
    }

    /*
     * 将Object转换成相应的java对象
     */
    @SuppressWarnings("static-access")
    public static <T> T objectToJavaObject(Object obj, Class<T> classz) {
        if (null == obj || null == classz) {
            return null;
        }
        try {
            JSONObject json = new JSONObject();
            JSONObject dataJson = (JSONObject) json.toJSON(obj);
            T tt = dataJson.toJavaObject(classz);
            return tt;
        } catch (Exception ex) {
            logger.error("{}", "object转换成java对象失败..." + ex.getMessage());
        }
        return null;
    }

}
