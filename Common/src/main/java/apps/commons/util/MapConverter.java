package apps.commons.util;

import apps.commons.util.page.PageInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对象Map转换器
 * 
 * <pre>
 * 构建转换器：（其中文中的“/ *  * /”为层级格式化用的占位符，使得代码更加清晰易读）
 * MapConverter m = new MapConverter(Test.class) //
 *         .add("id") // 增加id字段
 *         .add("name") // 增加name字段
 *         .add("book") // 增加第一层子对象
 *         .add(/ * * /"*") // 增加第一层子对象的所有字段
 *         .del(/ * * /"name") // 移除第一层子对象的name字段
 *         .add(/ * * /"time", "yyyyMMdd") // 增加time字段并指定格式化样式
 *         .add(/ * * /"questionList") // 增加第二层子集合
 *         .add(/ *              * /"id") // 
 *         .add(/ *              * /"number") //
 *         .add(/ *              * /"name") //
 *         .add(/ *              * /"answer") // 增加第三层子对象
 *         .add(/ *                     * /"*") // 
 * ;
 * 
 * // 转换为Map或Map集合 
 *     Map<String, Object> map = m.toMap(单个对象);
 *     List<Map<String, Object>> mapList = m.toMap(集合对象);
 * </pre>
 * 
 * @author caicai, 2015年11月23日 下午2:30:19
 */
public class MapConverter {

    final MapConverter parent;

    final Class<?> type;

    final Map<String, FieldInfo> fieldList = new LinkedHashMap<>();

    /** 创建一个基本对象的转换器 */
    public MapConverter(Class<?> type) {
        this(null, type);
    }

    private MapConverter(MapConverter map, Class<?> type) {
        super();
        this.parent = map;
        this.type = type;
    }

    /**
     * 增加一个字段
     * @author caicai, 2015年11月23日 下午2:32:53
     * @param name 字段名称，特殊语法：<br>
     *            1. * 表示所有字段（只加入基本类型，不推荐使用），<br>
     *            2. xx>yy 表示使用别名yy输出，<br>
     *            3. xx~yy 表示使用别名yy输出xx枚举的name2值<br>
     *            4. xx#zz 表示将xx以zz的格式输出，通常用于日期类，可以搭配>别名用法使用，例如：xxx>yy#zzz<br>
     * @return 如果name为自定义对象则返回此对象的类型映射，否则返回当前this自身
     */
    public MapConverter add(String name) {
        return addField(name, null, null, null, null);
    }

    /**
     * 批量增加若干个字段的便捷方法
     * @author caicai, 2015年11月23日 下午2:32:53
     * @param names 字段名称，特殊语法：<br>
     *            1. * 表示所有字段（只加入基本类型，不推荐使用），<br>
     *            2. xx>yy 表示使用别名yy输出，<br>
     *            3. xx~yy 表示使用别名yy输出xx枚举的name2值<br>
     *            4. xx#zz 表示将xx以zz的格式输出，通常用于日期类，可以搭配>别名用法使用，例如：xxx>yy#zzz<br>
     * @return 此方法返回自身
     */
    public MapConverter add(String... names) {
        for (String name : names) {
            addField(name, null, null, null, null);
        }
        return this;
    }

    /**
     * 直接对Map增加一个对象，而不使用当前对象的任何字段
     * @author caijianqing, 2019年6月10日 上午11:28:58
     * @param name
     * @param value
     * @return
     */
    public MapConverter addValue(String name, Object value) {
        return addField(name, null, null, value, null);
    }

    /**
     * 增加一个字段
     * @author caicai, 2015年11月23日 下午2:31:13
     * @param name 字段名称，特殊语法：<br>
     *            1. * 表示所有字段，<br>
     *            2. xx>yy 表示使用别名yy输出，<br>
     *            3. xx~yy 表示使用别名yy输出xx枚举的name2值<br>
     *            4. xx#zz 表示将xx以zz的格式输出，通常用于日期类，可以搭配>别名用法使用，例如：xxx>yy#zzz<br>
     * @param alias 别名
     * @param format 格式，针对Date类型
     * @param value 直接指定值内容，而不使用当前对象的任何字段
     * @param converter 转换器
     * @return 如果字段类型为自定义对象则返回新的MapConverter对象<br>
     *         如果为集合类型则返回ListMap对象<br>
     *         其他类型则直接返回对象自身
     */
    private MapConverter addField(String name, String alias, String format, Object value, ValueConverter converter) {
        name = name.trim();
        if (name.equals("*")) {
//            throw new RuntimeException("禁止使用此语法");
                        List<Field> fields = getFields(type);
                        for (Field field : fields) {
                            if (isBaseType(field.getType())) {
                                // 基本对象：加入
                                fieldList.put(alias != null ? alias : field.getName(),
                                        new FieldInfo(field.getType(), null, field.getName(), alias, format, value, null, converter));
                            } else if (Collection.class.isAssignableFrom(field.getType())) {
                                // 集合：跳过
                            } else {
                                // 构造对象：跳过
                            }
                        }
                        return this;
        } else {
            // 处理直接值
            if (value != null) {
                fieldList.put(name, new FieldInfo(type, null, name, null, null, value, null, null));
                return this;
            }
            // 解析特殊语法
            if (alias == null && format == null && converter == null) {
                Matcher mName = Pattern.compile("^([\\w\\$]+)").matcher(name); // 字段原名
                Matcher mAlias = Pattern.compile(">([\\w\\$]+)").matcher(name); // 常规别名>
                Matcher mFormat = Pattern.compile("#([\\S ]+)").matcher(name); // 输出格式#
                if (!mName.find()) {
                    throw new RuntimeException("字段名语法错误：" + name);
                }
                name = mName.group(1);
                if (mAlias.find()) {
                    alias = mAlias.group(1);
                }
                if (mFormat.find()) {
                    format = mFormat.group(1);
                }
            }
            // 处理常规字段 
            Field field = getField(type, name);
            if (field == null) {
                throw new RuntimeException("找不到字段：" + type.getName() + "." + name);
            }
            Class<?> type = field.getType();
            if (isBaseType(type)) {
                // 基本对象
                fieldList.put(alias != null ? alias : name, new FieldInfo(type, null, name, alias, format, null, null, converter));
                return this;
            } else if (Collection.class.isAssignableFrom(type)) {
                // 单列集合
                Class<?> elementType = null;
                Type t = field.getGenericType();
                if (t instanceof ParameterizedType) {
                    elementType = (Class<?>) ((ParameterizedType) t).getActualTypeArguments()[0];
                } else {
                    t = type.getGenericSuperclass();
                    if (t instanceof ParameterizedType) {
                        elementType = (Class<?>) ((ParameterizedType) t).getActualTypeArguments()[0];
                    } else {
                        throw new RuntimeException("无法在集合字段找到元素泛型类型：" + field);
                    }
                }
                if (elementType == null) {
                    throw new RuntimeException("集合字段必须指定元素泛型类型：" + field);
                }
                if (isBaseType(elementType)) {
                    // 集合中的元素为基本类型
                    fieldList.put(alias != null ? alias : name, new FieldInfo(type, elementType, name, alias, format, null, null, converter));
                    return this;
                } else {
                    // 集合中的元素为扩展对象
                    ListMapConverter newmap = new ListMapConverter(this, elementType);
                    fieldList.put(alias != null ? alias : name, new FieldInfo(type, elementType, name, alias, format, null, newmap, converter));
                    return newmap;
                }
            } else if (Map.class.isAssignableFrom(type)) {
                // 键值对集合
                Class<?> elementType = null;
                Type t = field.getGenericType();
                if (t instanceof ParameterizedType) {
                    elementType = (Class<?>) ((ParameterizedType) t).getActualTypeArguments()[1];
                } else {
                    t = type.getGenericSuperclass();
                    if (t instanceof ParameterizedType) {
                        elementType = (Class<?>) ((ParameterizedType) t).getActualTypeArguments()[1];
                    } else {
                        throw new RuntimeException("无法在集合字段找到元素泛型类型：" + field);
                    }
                }
                if (elementType == null) {
                    throw new RuntimeException("集合字段必须指定元素泛型类型：" + field);
                }
                if (isBaseType(elementType)) {
                    // 集合中的元素为基本类型
                    fieldList.put(alias != null ? alias : name, new FieldInfo(type, elementType, name, alias, format, null, null, converter));
                    return this;
                } else {
                    // 集合中的元素为扩展对象
                    ListMapConverter newmap = new ListMapConverter(this, elementType);
                    fieldList.put(alias != null ? alias : name, new FieldInfo(type, elementType, name, alias, format, null, newmap, converter));
                    return newmap;
                }
            } else {
                // 构造对象：需要返回新对象
                MapConverter newmap = new MapConverter(this, type);
                fieldList.put(alias != null ? alias : name, new FieldInfo(type, null, name, alias, format, null, newmap, converter));
                return newmap;
            }
        }

    }

    private boolean isBaseType(Class<?> type) {
        if (type.isPrimitive() //
                || type.isEnum() //
                || type == String.class //
                || type == Integer.class //
                || type == Double.class //
                || type == Boolean.class //
                || type == Date.class //
                || type == Short.class //
                || type == Float.class //
                || type == Long.class //
                || type == Byte.class //
                || type == Character.class //
        ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 从已增加的字段中移除一个字段，如果移除没有被加入的字段则会抛出异常
     * @author caicai, 2015年11月23日 下午2:33:23
     * @param name
     * @return
     */
    public MapConverter del(String name) {
        name = name.trim();
        FieldInfo f = fieldList.remove(name);
        if (f != null) {
            return this;
        }
        throw new RuntimeException("找不到已加入的字段：" + type.getName() + "." + name);
    }

    /**
     * 结束当前类型的转换器，返回父类型的转换器
     * @author caicai, 2015年11月23日 下午2:33:55
     * @return
     */
    public MapConverter end() {
        if (parent == null) {
            return this;
        } else {
            return parent;
        }
    }

    /**
     * 声明add结束，返回根类型的转换器
     * @author caicai, 2015年11月23日 下午2:36:02
     * @return
     */
    public MapConverter finish() {
        if (parent == null) {
            return this;
        } else {
            return parent.finish();
        }
    }

    /**
     * 将一个集合对象转换为Map集合
     * @author caicai, 2015年11月23日 下午2:37:03
     * @param objList
     * @return
     */
    public List<Map<String, Object>> toMapList(Collection<?> objList) {
        if (objList == null) {
            return null;
        }
        MapConverter map = finish();
        return toMapList(0, objList, map);
    }

    /* 功能描述: <br>
     * 将一个PageInfo对象转换
     * @author yangxianghua  2021年01月21日 上午10:20:57 周四
      * @param: objList
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public Map<String, Object> pageInfoToMapList(PageInfo t) {
//        Collection<?> objList
        Map<String, Object> result = new HashMap<>();
        result.put("page",t.getPage());
        result.put("code",t.getCode());
        result.put("message",t.getMessage());
        result.put("success",t.isSuccess());
        if (t.getData() == null) {
            result.put("data",null);
            return result;
        }

        MapConverter map = finish();
        List<Map<String, Object>> maps = toMapList(0, t.getData(), map);
        result.put("data",maps);
        return result;
    }

    /**
     * 将一个基本对象转换为Map对象
     * @author caicai, 2015年11月23日 下午2:36:39
     * @param obj
     * @return
     */
    public Map<String, Object> toMap(Object obj) {
        if (obj == null) {
            return null;
        } else if (Collection.class.isAssignableFrom(obj.getClass())) {
            throw new RuntimeException("集合对象请使用toMapList(...)方法：" + obj);
        }
        MapConverter map = finish();
        return toMap(0, obj, map);
    }

    private List<Map<String, Object>> toMapList(int level, Object obj, MapConverter m) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (obj != null) {
            for (Object e : ((Collection<?>) obj)) {
                Map<String, Object> childMap = toMap(++level, e, m);
                list.add(childMap);
            }
        }
        //        if (list.isEmpty()) {
        //            return null;
        //        }
        return list;
    }

    private Map<String, Object> toMap(int level, Object obj, MapConverter m) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            for (FieldInfo info : m.fieldList.values()) {
                // 处理直接值
                if (info.value != null) {
                    map.put(info.name(), info.value);
                    continue;
                }
                // 处理常规字段
                Field field = getField(m.type, info.name);
                field.setAccessible(true);
                Object value = field.get(obj);
                // field.setAccessible(false);

                // 调试方法
                //                if (info.format == null) {
                //                    System.out.println(Strings.repeat(" ", level * 4) + info.name);
                //                } else {
                //                    System.out.println(Strings.repeat(" ", level * 4) + info.name + ":" + info.format);
                //                }
                if (value == null) {
                    map.put(info.name(), info.defaultValue(value));
                    continue;
                }

                if (info.child == null) {
                    // 基本字段
                    if (info.convert != null) {
                        Object value2 = info.convert.converTo(field, value);
                        map.put(info.name(), info.defaultValue(value2));
                    } else if (info.format != null && Date.class.isAssignableFrom(field.getType())) {
                        String value2 = format(((Date) value), info.format);
                        map.put(info.name(), info.defaultValue(value2));
                    } else if (info.format != null && field.getType().isEnum()) {
                        try {
                            Field f = field.getType().getDeclaredField(info.format);
                            f.setAccessible(true);
                            Object val = f.get(value);
                            map.put(info.name(), info.defaultValue(val));
                        } catch (NoSuchFieldException | SecurityException e) {
                            throw new RuntimeException("在枚举找（" + field.getType() + "）不到字段：" + info.format);
                        }
                    } else {
                        map.put(info.name(), info.defaultValue(value));
                    }
                } else {
                    if (info.child.getClass() == MapConverter.class) {
                        // 类别成员
                        Map<String, Object> childMap = toMap(++level, value, info.child);
                        map.put(info.name(), info.defaultValue(childMap));

                    } else if (info.child.getClass() == ListMapConverter.class) {
                        // 集合
                        List<Map<String, Object>> list = toMapList(++level, value, info.child);
                        map.put(info.name(), info.defaultElementValue(list));
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        level--;
        return map;
    }

    /**
     * 懒人专用日期格式化方法
     * @author VoidChoy 2014年7月29日 下午2:36:23
     * @param date 日期对象，如果为NULL则直接返回null
     * @param pattern 可选的日期表达式，不填写默认使用：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String format(Date date, String... pattern) {
        if (date == null) {
            return null;
        }
        DateFormat df;
        if (pattern == null || pattern.length == 0 || pattern[0] == null) {
            df = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            df = getSimpleDateFormat(pattern[0]);
        }
        return df.format(date);
    }

    /** 默认格式 */
    private static final String defaultSdfDatePattern = "yyyy-MM-dd HH:mm:ss";

    /** 默认格式的缓存 */
    private static final Map<Long, SimpleDateFormat> defaultSdfMap = new HashMap<Long, SimpleDateFormat>();

    /** 自定义格式的缓存 */
    private static final Map<String, Map<Long, SimpleDateFormat>> sdfMap = new HashMap<String, Map<Long, SimpleDateFormat>>();

    /**
     * 返回一个线程安全的SimpleDateFormat类
     * @param pattern 自定义格式
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        return getSimpleDateFormat(pattern, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * 返回一个线程安全的SimpleDateFormat类
     * @param pattern 自定义格式
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormat(String pattern, Locale locale) {
        synchronized (sdfMap) {
            Map<Long, SimpleDateFormat> m = sdfMap.get(pattern);
            if (m == null) {
                m = new HashMap<Long, SimpleDateFormat>();
                sdfMap.put(pattern, m);
            }
            Long id = Thread.currentThread().getId();
            SimpleDateFormat sdf = m.get(id);
            if (sdf == null) {
                sdf = new SimpleDateFormat(pattern, locale);
                m.put(id, sdf);
            }
            return sdf;
        }
    }


    /**
     * 返回指定类型指定名称的字段
     * @author caicai, 2015年5月15日 下午2:11:25
     * @param type
     * @param fieldName
     * @return
     */
    public static Field getField(Class<?> type, String fieldName) {
        return getFieldsMap(type).get(fieldName);
        //        List<Field> fieldList = getFields(type);
        //        for (Field field : fieldList) {
        //            if (field.getName().equals(fieldName)) {
        //                return field;
        //            }
        //        }
        //        return null;
    }

    /**
     * 返回常规的字段Map集合
     * @author caijianqing, 2019年5月20日 下午4:24:53
     * @param type
     * @return 返回的Map对象，key=Field名称，value=字段Field对象
     */
    public static Map<String, Field> getFieldsMap(Class<?> type) {
        Map<String, Field> map = getFieldMapCache.get(type);
        if (map == null) {
            map = new HashMap<>();
            List<Field> fields = getFields(type);
            for (Field field : fields) {
                map.put(field.getName(), field);
            }
            map = Collections.unmodifiableMap(map);
            getFieldMapCache.put(type, map);
        }
        return Collections.unmodifiableMap(map);
    }

    private static final Map<Class<?>, List<Field>> getFieldsCache = new HashMap<>();

    private static final Map<Class<?>, Map<String, Field>> getFieldMapCache = new HashMap<>();

    /**
     * 返回常规的字段List集合
     * @param type 类型
     * @return 返回private, protected, public，并且非抽象、非静态、非瞬时的字段
     */
    public static List<Field> getFields(Class<?> type) {
        List<Field> list = getFieldsCache.get(type);
        if (list != null) {
            return Collections.unmodifiableList(list);
        }
        list = getFieldsHasSuper(type, //
                Modifier.PRIVATE | Modifier.PROTECTED | Modifier.PUBLIC, // friendly, private, protected, public
                Modifier.ABSTRACT | Modifier.STATIC | Modifier.TRANSIENT /* 添加了transient关键字可以在生成toString和equals方法时自动跳过这个字段 */ , // 非抽象、非静态、非瞬时
                true);
        synchronized (getFieldsCache) {
            getFieldsCache.put(type, list);
        }
        return Collections.unmodifiableList(list);
    }

    /**
     * 获取所有字段（包括父类）
     * @param type 类型
     * @param includeModifier 字段修饰值，比如：{@link Modifier#PUBLIC}, <br>
     *            可以使用“|”连接多个，例如：{@link Modifier#PRIVATE}|{@link Modifier#TRANSIENT}
     * @param excludeModifier 不包含的修饰类型
     * @param includeFriendly 是否包含无修饰符的字段
     * @return
     */
    public static List<Field> getFieldsHasSuper(Class<?> type, int includeModifier, int excludeModifier, boolean includeFriendly) {
        List<Field> list = new LinkedList<>();
        if (type != Class.class) {
            Class<?> superClass = type.getSuperclass();
            if (superClass == null || superClass == Object.class) {
                list.addAll(getFieldsNoSuper(type, includeModifier, excludeModifier, includeFriendly));
            } else {
                list.addAll(getFieldsHasSuper(superClass, includeModifier, excludeModifier, includeFriendly));
                list.addAll(getFieldsNoSuper(type, includeModifier, excludeModifier, includeFriendly));
            }
        }
        list = Collections.unmodifiableList(list);
        return list;
    }

    /**
     * 获取所有字段（不包括父类）
     * @param type 类型
     * @param includeModifier 包含的字段修饰值，比如：{@link Modifier#PUBLIC}, <br>
     *            可以使用“|”连接多个，例如：{@link Modifier#PRIVATE}|{@link Modifier#TRANSIENT}
     * @param excludeModifier 不包含的修饰类型
     * @param includeFriendly 是否包含无修饰符的字段
     * @return
     */
    public static List<Field> getFieldsNoSuper(Class<?> type, int includeModifier, int excludeModifier, boolean includeFriendly) {
        List<Field> list = new LinkedList<>();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            if (field.getModifiers() == 0) {
                if (includeFriendly) {
                    list.add(field);
                }
                continue;
            }
            if ((field.getModifiers() & excludeModifier) != 0) {
                continue;
            }
            if ((field.getModifiers() & includeModifier) != 0) {
                list.add(field);
            }
        }
        return list;
    }

    /***
     * 转换器
     * @author caicai, 2016年9月12日 下午1:03:19
     */
    public static interface ValueConverter {

        /**
         * 转换
         * @author caicai, 2016年9月12日 下午1:03:25
         * @param field 字段对象
         * @param value 字段原始值
         * @return 返回转换后的值
         */
        public Object converTo(Field field, Object value);

    }

    private class FieldInfo {

        final Map<String, Object> emptyMap = new HashMap<>();

        final List<Object> emptyList = new ArrayList<>();

        /** 字段数据类型 */
        final Class<?> type;

        /** 数组或集合的元素类型 */
        final Class<?> elementType;

        final String name;

        final String alias;

        final String format;

        final Object value;

        final ValueConverter convert;

        final MapConverter child;

        public FieldInfo(Class<?> type, Class<?> elementType, String name, String alias, //
                String format, Object value, MapConverter child, ValueConverter convert) {
            this.type = type;
            this.elementType = elementType;
            this.name = name;
            this.alias = alias;
            this.format = format;
            this.value = value;
            this.child = child;
            this.convert = convert;
        }

        @Override
        public String toString() {
            return "FieldInfo [name=" + name + ", format=" + format + ", child=" + child + "]";
        }

        String name() {
            return alias != null ? alias : name;
        }

        /** 返回默认值 */
        Object defaultValue(Object value) {
            if (value != null) {
                return value;
            }
            if (isBaseType(type)) {
                return NullValue.instance;
            } else if (Map.class.isAssignableFrom(type)) {
                return emptyMap;
            } else if (Collection.class.isAssignableFrom(type)) {
                return emptyList;
            }
            // 剩下的都是自定义对象，所以用map集合方式返回
            // return emptyMap;
            return null; // 不返回为null的自定义对象
        }

        /** 返回元素默认值 */
        Object defaultElementValue(Object value) {
            if (value != null) {
                return value;
            }
            if (isBaseType(elementType)) {
                return NullValue.instance;
            } else if (Map.class.isAssignableFrom(elementType)) {
                return emptyMap;
            } else if (Collection.class.isAssignableFrom(elementType)) {
                return emptyList;
            }
            // 剩下的都是自定义对象，所以用map集合方式返回
            // return emptyMap;
            return null; // 不返回为null的自定义对象
        }

    }

    /**
     * 集合Map转换器
     * @author caicai, 2015年11月23日 下午2:38:14
     */
    private static class ListMapConverter extends MapConverter {

        /**
         * 创建一个集合对象转换器
         * @author caicai, 2015年11月23日 下午2:38:43
         * @param type
         */
        public ListMapConverter(Class<?> type) {
            super(type);
        }

        private ListMapConverter(MapConverter map, Class<?> type) {
            super(map, type);
        }

    }

    /** 专门用于在fastjson转换为null字符串的特殊类 */
    public static class NullValue {

        public static NullValue instance = null;

        private NullValue() {}

    }

    ////////////////////////////////////////////////////////////////////////////////////
    //
    //    /**
    //     * 创建一个Map转换器的便捷方法
    //     * @author caicai, 2015年11月23日 下午2:59:34
    //     * @param type 跟对象的类型
    //     * @param names 可选，同时加入这些字段（基本类型）
    //     * @return
    //     */
    //    public static MapConverter newInstance(Class<?> type, String... names) {
    //        MapConverter m = new MapConverter(type);
    //        if (names != null && names.length > 0) {
    //            m.add(names);
    //        }
    //        return m;
    //    }
    ////////////////////////////////////////////////////////////////////////////////////
    //    public static class Test {
    //
    //        public int id = 1;
    //
    //        public Integer age = 2;
    //
    //        public String name = "3";
    //
    //        public Date time = new Date();
    //
    //        public Book book = new Book();
    //
    //    }
    //
    //    public static class Book {
    //
    //        public int id = 1;
    //
    //        public String name = "3";
    //
    //        public Date time = new Date();
    //
    //        public List<Question> questionList = new ArrayList<>();
    //
    //        public Book() {
    //            questionList.add(new Question());
    //            questionList.add(new Question());
    //        }
    //
    //    }
    //
    //    public static class Question {
    //
    //        public int id = 1;
    //
    //        public String number = "3";
    //
    //        public String name = "3";
    //
    //        public Answer answer = new Answer();
    //
    //    }
    //
    //    public static class Answer {
    //
    //        public int id = 1;
    //
    //        public String number = "3";
    //
    //        public String name = "3";
    //
    //    }
    //
    //    public static void main(String[] args) {
    //
    //        MapConverter m = new MapConverter(Test.class) //
    //                .add("id") // 增加id字段
    //                .add("name") // 增加name字段
    //                .add("book") // 增加第一层子对象
    //                .add(/* */"*") // 增加第一层子对象的所有字段
    //                .del(/* */"name") // 移除第一层子对象的name字段
    //                .add2(/* */"time", "yyyyMMdd") // 增加time字段并指定格式化样式
    //                .add(/* */"questionList") // 增加第二层子集合
    //                .add(/*              */"id") // 
    //                .add(/*              */"number") //
    //                .add(/*              */"name") //
    //                .add(/*              */"answer") // 增加第三层子对象
    //                .add(/*                     */"*") // 
    //        ;
    //        Map<String, Object> map = m.toMap(new Test());
    //        System.out.println("-----------------------");
    //        System.out.println(map);
    //
    //        List<Test> list = new ArrayList<>();
    //        list.add(new Test());
    //        list.add(new Test());
    //        System.out.println(m.toMap(list));
    //
    //    }
}
