package apps.commons.util.tool_util;

import org.apache.ibatis.reflection.ReflectionException;
import org.springframework.beans.BeansException;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Function<T,R> 工具类
 * @author winsun
 */
public class FunctionUtil {

	private static Map<Class<?>, SerializedLambda> CLASS_LAMDBA_CACHE = new ConcurrentHashMap<>();
	
	/**
	 * 通过公有方法获取私有变量名称
	 * @param fn
	 * @return
	 */
	public static <T> String getFieldName(MFunction<T, ?> fn) {
        SerializedLambda lambda = getSerializedLambda(fn);
        String methodName = lambda.getImplMethodName();
        return methodToProperty(methodName);
    }
	
	/**
	 * lambda表达式获取属性名称
	 * @param                  <T>
	 * @param func
	 * @return
	 * @throws BeansException
	 */
	@SafeVarargs
	public static <T> String[] getFieldNames(MFunction<T, ?>... func) throws BeansException {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < func.length; i++) {
			String fieldName = getFieldName(func[i]);
			list.add(fieldName);
		}
		String[] fieldNames = list.toArray(new String[list.size()]);
		return fieldNames;
	}

	
    /***
     * 获取类对应的Lambda
     * @param fn
     * @return
     */
    private static SerializedLambda getSerializedLambda(Serializable fn){
        //先检查缓存中是否已存在
        SerializedLambda lambda = CLASS_LAMDBA_CACHE.get(fn.getClass());
        if(lambda == null){
            try{//提取SerializedLambda并缓存
                Method method = fn.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                lambda = (SerializedLambda) method.invoke(fn);
                CLASS_LAMDBA_CACHE.put(fn.getClass(), lambda);
            }
            catch (Exception e){
            	throw new ReflectionException("Error serialized lambda ");
            }
        }
        return lambda;
    }
    
    private static String methodToProperty(String name) {
        if (name.startsWith("is")) {
          name = name.substring(2);
        } else if (name.startsWith("get") || name.startsWith("set")) {
          name = name.substring(3);
        } else {
          throw new ReflectionException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
        }
        if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
          name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }
        return name;
      }

	
}
