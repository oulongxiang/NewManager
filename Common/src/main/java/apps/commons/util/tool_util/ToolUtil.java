package apps.commons.util.tool_util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.*;
import java.util.Map.Entry;


/**
 * 高频方法集合类
 * @author ZJL
 * @date 2019年9月18日 下午8:54:36
 */
public class ToolUtil {
    public final static Logger logger = LoggerFactory.getLogger(ToolUtil.class);

	//分隔符
    public static final String SEPARATOR = ";;;";
    
    
    /**
     * 获取随机位数的字符串
     * @Date 2017/8/24 14:09
     */
    public static String getRandomString(int length) {
    	String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    	try {
    		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    		StringBuffer sb = new StringBuffer();
    		for (int i = 0; i < length; i++) {
    			int number = random.nextInt(base.length());
    			sb.append(base.charAt(number));
    		}
    		return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }

    /**
     * 获取异常的具体信息
     * @Date 2017/3/30 9:21
     * @version 2.0
     */
    public static String getExceptionMsg(Exception e) {
        StringWriter sw = new StringWriter();
        try {
            e.printStackTrace(new PrintWriter(sw));
        } finally {
            try {
                sw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sw.getBuffer().toString().replaceAll("\\$", "T");
    }

    /**
     * 比较两个对象是否相等。<br>
     * 相同的条件有两个，满足其一即可：<br>
     * 1. obj1 == null && obj2 == null; 2. obj1.equals(obj2)
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 是否相等
     */
    public static boolean equals(Object obj1, Object obj2) {
        return (obj1 != null) ? (obj1.equals(obj2)) : (obj2 == null);
    }

    /**
     * 计算对象长度，如果是字符串调用其length函数，集合类调用其size函数，数组调用其length属性，其他可遍历对象遍历计算长度
     *
     * @param obj 被计算长度的对象
     * @return 长度
     */
    public static int length(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length();
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).size();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size();
        }

        int count;
        if (obj instanceof Iterator) {
            Iterator<?> iter = (Iterator<?>) obj;
            count = 0;
            while (iter.hasNext()) {
                count++;
                iter.next();
            }
            return count;
        }
        if (obj instanceof Enumeration) {
            Enumeration<?> enumeration = (Enumeration<?>) obj;
            count = 0;
            while (enumeration.hasMoreElements()) {
                count++;
                enumeration.nextElement();
            }
            return count;
        }
        if (obj.getClass().isArray() == true) {
            return Array.getLength(obj);
        }
        return -1;
    }

    /**
     * 对象中是否包含元素
     *
     * @param obj     对象
     * @param element 元素
     * @return 是否包含
     */
    public static boolean contains(Object obj, Object element) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            if (element == null) {
                return false;
            }
            return ((String) obj).contains(element.toString());
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).contains(element);
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).values().contains(element);
        }

        if (obj instanceof Iterator) {
            Iterator<?> iter = (Iterator<?>) obj;
            while (iter.hasNext()) {
                Object o = iter.next();
                if (equals(o, element)) {
                    return true;
                }
            }
            return false;
        }
        if (obj instanceof Enumeration) {
            Enumeration<?> enumeration = (Enumeration<?>) obj;
            while (enumeration.hasMoreElements()) {
                Object o = enumeration.nextElement();
                if (equals(o, element)) {
                    return true;
                }
            }
            return false;
        }
        if (obj.getClass().isArray() == true) {
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                Object o = Array.get(obj, i);
                if (equals(o, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取异常的具体信息前20行
     * @Date 2017/3/30 9:21
     * @version 2.0
     */
    public static String getExceptionSmallMsg(Exception e) {
        StringWriter sw = new StringWriter();
        StringBuilder sb = new StringBuilder();
        try {
            e.printStackTrace(new PrintWriter(sw));
            String msg = sw.getBuffer().toString().replaceAll("\\$", "T");
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(msg.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
            for (int i = 0; i < 20; i++) {
                sb.append(br.readLine()+"\n");
            }
        } catch (IOException e1) {
            logger.error(getExceptionMsg(e1));
        } finally {
            try {
                sw.close();
            } catch (IOException e1) {

            }
        }
        return sb.toString();
    }

    /**
     * 对象是否不为空(新增)
     *
     * @param obj String,List,Map,Object[],int[],long[]
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * 对象是否为空
     *
     * @param obj String,List,Map,Object[],int[],long[]
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            if ("".equals(o.toString().trim())) {
                return true;
            }
        } else if (o instanceof List) {
            if (((List) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Set) {
            if (((Set) o).size() == 0) {
                return true;
            }
        } else if (o instanceof Object[]) {
            if (((Object[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof int[]) {
            if (((int[]) o).length == 0) {
                return true;
            }
        } else if (o instanceof long[]) {
            if (((long[]) o).length == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象组中是否存在 Empty Object
     *
     * @param os 对象组
     * @return
     */
    public static boolean isOneEmpty(Object... os) {
        for (Object o : os) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对象组中是否全是 Empty Object
     *
     * @param os
     * @return
     */
    public static boolean isAllEmpty(Object... os) {
        for (Object o : os) {
            if (!isEmpty(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为数字
     *
     * @param obj
     * @return
     */
    public static boolean isNum(Object obj) {
        try {
            Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 如果为空, 则调用默认值
     *
     * @param str
     * @return
     */
    public static Object getValue(Object str, Object defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        return str;
    }

    /**
     * 格式化文本
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... values) {
        return StrKit.format(template, values);
    }

    /**
     * 格式化文本
     *
     * @param template 文本模板，被替换的部分用 {key} 表示
     * @param map      参数值对
     * @return 格式化后的文本
     */
    public static String format(String template, Map<?, ?> map) {
        return StrKit.format(template, map);
    }

    /**
     * 强转->string,并去掉多余空格
     *
     * @param str
     * @return
     */
    public static String toStr(Object str) {
        return toStr(str, "");
    }

    /**
     * 强转->string,并去掉多余空格
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static String toStr(Object str, String defaultValue) {
        if (null == str) {
            return defaultValue;
        }
        return str.toString().trim();
    }

    /**
     * 强转->int
     *
     * @param obj
     * @param defaultValue
     * @return
     */
	public static int toInt(Object value, int defaultValue) {
		return Convert.toInt(value, defaultValue);
	}

    /**
     * 强转->long
     *
     * @param obj
     * @return
     */
	public static long toLong(Object value) {
		return toLong(value, -1);
	}

    /**
     * 强转->long
     *
     * @param obj
     * @param defaultValue
     * @return
     */
	public static long toLong(Object value, long defaultValue) {
		return Convert.toLong(value, defaultValue);
	}

    /**
     * map的key转为小写
     *
     * @param map
     * @return Map<String , Object>
     */
    public static Map<String, Object> caseInsensitiveMap(Map<String, Object> map) {
        Map<String, Object> tempMap = new HashMap<>();
        for (String key : map.keySet()) {
            tempMap.put(key.toLowerCase(), map.get(key));
        }
        return tempMap;
    }

    /**
     * 获取map中第一个数据值
     *
     * @param <K> Key的类型
     * @param <V> Value的类型
     * @param map 数据源
     * @return 返回的值
     */
    public static <K, V> V getFirstOrNull(Map<K, V> map) {
        V obj = null;
        for (Entry<K, V> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }

    /**
     * 创建StringBuilder对象
     *
     * @return StringBuilder对象
     */
    public static StringBuilder builder(String... strs) {
        final StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str);
        }
        return sb;
    }

    /**
     * 创建StringBuilder对象
     *
     * @return StringBuilder对象
     */
    public static void builder(StringBuilder sb, String... strs) {
        for (String str : strs) {
            sb.append(str);
        }
    }

    /**
     * 去掉指定后缀
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffix(String str, String suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return str;
        }

        if (str.endsWith(suffix)) {
            return str.substring(0, str.length() - suffix.length());
        }
        return str;
    }

    /**
     * 当前时间
     *
     * @author stylefeng
     * @Date 2017/5/7 21:56
     */
    public static String currentTime() {
        return DateUtil.getTime();
    }

    /**
     * 首字母大写
     *
     * @author stylefeng
     * @Date 2017/5/7 22:01
     */
    public static String firstLetterToUpper(String val) {
        return StrKit.firstCharToUpperCase(val);
    }

    /**
     * 首字母小写
     * @Date 2017/5/7 22:02
     */
    public static String firstLetterToLower(String val) {
        return StrKit.firstCharToLowerCase(val);
    }

    /**
     * 判断是否是windows操作系统
     * @Date 2017/5/24 22:34
     */
    public static Boolean isWinOs() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取临时目录
     * @Date 2017/5/24 22:35
     */
    public static String getTempPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 把一个数转化为int
     * @Date 2017/11/15 下午11:10
     */
    public static Integer toInt(Object val) {
        if (val instanceof Double) {
            BigDecimal bigDecimal = new BigDecimal((Double) val);
            return bigDecimal.intValue();
        } else {
            return Integer.valueOf(val.toString());
        }

    }

    /**
     * 获取项目路径
     */
    public static String getWebRootPath(String filePath) {
        try {
            String path = ToolUtil.class.getClassLoader().getResource("").toURI().getPath();
            path = path.replace("/WEB-INF/classes/", "");
            path = path.replace("/target/classes/", "");
            path = path.replace("file:/", "");
            if (ToolUtil.isEmpty(filePath)) {
                return path;
            } else {
                return path + "/" + filePath;
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文件后缀名 不包含点
     */
    public static String getFileSuffix(String fileWholeName) {
        if (ToolUtil.isEmpty(fileWholeName)) {
            return "none";
        }
        int lastIndexOf = fileWholeName.lastIndexOf(".");
        return fileWholeName.substring(lastIndexOf + 1);
    }
    
    /**
	 * 字段转换驼峰命名
	 * @param s
	 * @return
	 */
	public static String toCamelCase(String name) {
		return StrKit.toCamelCase(name);
    }
	
	/**
     * JSON结果集判断状态是否成功
     *
     * @param obj 判断code=200, 必须包含code的key, 可以是String,JSONObject,Map
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isSuccessCode(Object o) {
        if (isEmpty(o)) {
            return false;
        }
        if (o instanceof String && isJsonObject(o.toString())) {
        	JSONObject json = JSONObject.parseObject(o.toString());
            if (json.getInteger("code") == 200) {
                return true;
            }
        } else if (o instanceof JSONObject) {
        	JSONObject json = (JSONObject) o;
            if (json.getInteger("code") == 200) {
                return true;
            }
        } else if (o instanceof Map) {
        	Map map = (Map) o;
            if (map.containsKey("code") && Integer.parseInt(map.get("code").toString()) == 200) {
                return true;
            }
        } else {
        	// 得到类对象
            Class userCla = (Class) o.getClass();
            /* 得到类中的所有属性集合 */
            Field[] fs = userCla.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true); // 设置些属性是可以访问的
                try {
                    if (f.getName().endsWith("code") && isNotEmpty(f.get(o)) && Integer.parseInt(f.get(o).toString()) == 200) {
                    	return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    /**
     *  是否JsonObject
     */
    public static boolean isJsonObject(String data) {
    	if (data != null && data.startsWith("{") && data.endsWith("}")) {
    		try {
				JSON.parseObject(data);
    			return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	return false;
    }
    
    /**
     * 是否JsonArray
     */
    public static boolean isJsonArray(String data) {
    	if (data != null && data.startsWith("[") && data.endsWith("]")) {
    		try {
				JSON.parseArray(data);
    			return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	return false;
    }
    
    /**
     *  对传入的对象进行数据清洗，将属性值为null和""的去掉，其他字段名和属性值存入map集合
     */
    public static Map<String,String> objectToMap(Object requestParameters) throws IllegalAccessException {
        
        Map<String, String> map = new HashMap<>();
        // 获取f对象对应类中的所有属性域
        Field[] fields = requestParameters.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(requestParameters);
                if (o != null && StringUtils.isNotBlank(o.toString().trim())) {
                    map.put(varName, o.toString().trim());
                    // 恢复访问控制权限
                    fields[i].setAccessible(accessFlag);
                }
        }
        return map;
    }

    /**
     * 比较两个对象,并返回不一致的信息
     */
    public static String contrastObj(Object pojo1, Map<?, ?> pojo2) {
        String str = "";
        try {
            Class<?> clazz = pojo1.getClass();
            Field[] fields = pojo1.getClass().getDeclaredFields();
            int i = 1;
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(pojo1);
                Object o2 = pojo2.get(field.getName());
                if (o1 == null || o2 == null) {
                    continue;
                }
                if (o1 instanceof Date) {
                    o1 = DateUtil.getDay((Date) o1);
                }
                if (!o1.toString().equals(o2.toString())) {
                    if (i != 1) {
                        str += SEPARATOR;
                    }
                    str += "字段名称" + field.getName() + ",旧值:" + o1 + ",新值:" + o2;
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 比较两个对象,并返回不一致的信息
     *
     * @author stylefeng
     * @Date 2017/5/9 19:34
     */
    public static String contrastObj(Object pojo1, Object pojo2) {
        String str = "";
        try {
        	Class<?> clazz = pojo1.getClass();
            Field[] fields = pojo1.getClass().getDeclaredFields();
            int i = 1;
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(pojo1);
                Object o2 = getMethod.invoke(pojo2);
                if (o1 == null || o2 == null) {
                    continue;
                }
                if (o1 instanceof Date) {
                    o1 = DateUtil.getDay((Date) o1);
                }
                if (!o1.toString().equals(o2.toString())) {
                    if (i != 1) {
                        str += SEPARATOR;
                    }
                    str += "字段名称" + field.getName() + ",旧值:" + o1 + ",新值:" + o2;
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 比较两个对象pojo1和pojo2,并输出不一致信息
     */
	public static String contrastObj(String key, Object pojo1, Map<String, String> pojo2) throws IllegalAccessException, InstantiationException {
        String str = parseMutiKey(key, pojo2) + SEPARATOR;
        try {
        	Class<?> clazz = pojo1.getClass();
            Field[] fields = pojo1.getClass().getDeclaredFields();
            int i = 1;
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(pojo1);
                Object o2 = pojo2.get(StrKit.firstCharToLowerCase(getMethod.getName().substring(3)));
                if (o1 == null || o2 == null) {
                    continue;
                }
                if (o1 instanceof Date) {
                    o1 = DateUtil.getDay((Date) o1);
                } else if (o1 instanceof Integer) {
                    o2 = Integer.parseInt(o2.toString());
                }
                if (!o1.toString().equals(o2.toString())) {
                    if (i != 1) {
                        str += SEPARATOR;
                    }
                    String fieldName = field.getName();
                    str += "字段名称:" + fieldName + ",旧值:" + o1 + ",新值:" + o2;
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 比较两个对象pojo1和pojo2,并输出不一致信息
     */
	public static String contrastObjByName(String key, Object pojo1, Map<String, String> pojo2) throws IllegalAccessException, InstantiationException {
        String str = parseMutiKey(key, pojo2) + SEPARATOR;
        try {
        	Class<?> clazz = pojo1.getClass();
            Field[] fields = pojo1.getClass().getDeclaredFields();
            int i = 1;
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                String prefix = "get";
                int prefixLength = 3;
                if ("java.lang.Boolean".equals(field.getType().getName())) {
                    prefix = "is";
                    prefixLength = 2;
                }
                Method getMethod = null;
                try {
                    getMethod = clazz.getDeclaredMethod(prefix + StrKit.firstCharToUpperCase(field.getName()));
                } catch (NoSuchMethodException e) {
                    System.err.println("this className:" + clazz.getName() + " is not methodName: " + e.getMessage());
                    continue;
                }
                Object o1 = getMethod.invoke(pojo1);
                Object o2 = pojo2.get(StrKit.firstCharToLowerCase(getMethod.getName().substring(prefixLength)));
                if (o1 == null || o2 == null) {
                    continue;
                }
                if (o1 instanceof Date) {
                    o1 = DateUtil.getDay((Date) o1);
                } else if (o1 instanceof Integer) {
                    o2 = Integer.parseInt(o2.toString());
                }
                if (!o1.toString().equals(o2.toString())) {
                    if (i != 1) {
                        str += SEPARATOR;
                    }
                    String fieldName = field.getName();
                    str += "字段名称:" + fieldName + ",旧值:" + o1 + ",新值:" + o2;
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    
    /**
     * 解析多个key(逗号隔开的)
     */
    public static String parseMutiKey(String key, Map<String, String> requests) {
        StringBuilder sb = new StringBuilder();
        if (key.indexOf(",") != -1) {
            String[] keys = key.split(",");
            for (String item : keys) {
                String value = requests.get(item);
                sb.append(item + "=" + value + ",");
            }
            return StrKit.removeSuffix(sb.toString(), ",");
        } else {
            String value = requests.get(key);
            sb.append(key + "=" + value);
            return sb.toString();
        }
    }
    
    /**
	 * 将字符串转换成Double类型
	 * @param str
	 * @return
	 */
	public static Double parseDouble(String str) {
		if (isNotEmpty(str)) {
			return Double.parseDouble(str);
		}
		return 0.0;
	}
	
	public static Map<String,Object> newMap(Object... args) {
		JSONObject result = new JSONObject();
		if (args != null) {
			if (args.length % 2 != 0) {
				throw new IllegalArgumentException("The number of arguments must be an even number");
			}
			for (int i = 0; i < args.length; i += 2) {
				result.put(args[i].toString(), args[i + 1]);
			}
		}
		return result;
	}
	
	/**
	 * 处理文本换行问题
	 * @param str
	 * @return
	 */
	public static String dealNewLine(String str) {
		StringBuffer sb = new StringBuffer();
		if(str!=null && str.length()>0){
			String[] split = str.split("\r\n");
			if(split.length==0){
				for(int q=0;q<split.length;q++){
					if(q!=split.length-1){
						sb.append(split[q]+"<br/>");
					}else{
						sb.append(split[q]);
					}
				}
			}else{
				String[] split1 = str.split("\n");
				for(int q=0;q<split1.length;q++){
					if(q!=split1.length-1){
						sb.append(split1[q]+"<br/>");
					}else{
						sb.append(split1[q]);
					}
				}
			}
		}
		String result = sb.toString();
		return result;
	}

    /**
     * 格式化一个字符串
     * @author caicai, 2015年3月6日 下午10:23:18
     * @param str 字符串原文
     * @param objs 自动根据顺序替换原文中得“{n}”这样的字符串，n从1开始
     * @return
     */
    public static String formats(String str, Object... objs) {
        if (str == null || str.length() == 0) {
            return str;
        }
        for (int i = 0; i < objs.length; i++) {
            String obj;
            if (objs[i] == null) {
                obj = "";
            }else{
                obj = objs[i].toString().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
            }
            str = str.replaceAll("\\{" + (i + 1) + "\\}", obj);
        }
        return str;
    }

}
