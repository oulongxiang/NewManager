package apps.commons.util.tool_util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.concurrent.ConcurrentHashMap;


public class CgCopierUtil {



    /**
     * BeanCopier的缓存
     */
    static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    /**
     * BeanCopier的copy
     * @param source 源文件的
     * @param target 目标文件
     */
    public static void copy(Object source, Object target) {
        String key = genKey(source.getClass(), target.getClass());
        BeanCopier beanCopier;
        if (BEAN_COPIER_CACHE.containsKey(key)) {
            beanCopier = BEAN_COPIER_CACHE.get(key);
        } else {
            beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
            BEAN_COPIER_CACHE.put(key, beanCopier);
        }
        beanCopier.copy(source, target, null);
    }

    /**
     * 生成key
     * @param srcClazz 源文件的class
     * @param tgtClazz 目标文件的class
     * @return string
     */
    private static String genKey(Class<?> srcClazz, Class<?> tgtClazz) {
        return srcClazz.getName() + tgtClazz.getName();
    }


//    /**
//     * 单个对象属性拷贝
//     * @param source 源对象
//     * @param clazz 目标对象Class
//     * @param <T> 目标对象类型
//     * @param <M> 源对象类型
//     * @return 目标对象
//     */
//    public static <T, M> T copyProperties(M source, Class<T> clazz){
//        if (Objects.isNull(source) || Objects.isNull(clazz))
//            throw new IllegalArgumentException();
//        return copyProperties(source, clazz, null);
//    }
//
//    /**
//     * 列表对象拷贝
//     * @param sources 源列表
//     * @param clazz 源列表对象Class
//     * @param <T> 目标列表对象类型
//     * @param <M> 源列表对象类型
//     * @return 目标列表
//     */
//    public static <T, M> List<T> copyObjects(List<M> sources, Class<T> clazz) {
//        if (Objects.isNull(sources) || Objects.isNull(clazz) || sources.isEmpty())
//            throw new IllegalArgumentException();
//        BeanCopier copier = BeanCopier.create(sources.get(0).getClass(), clazz, false);
//        return Optional.of(sources)
//                .orElse(new ArrayList<>())
//                .stream().map(m -> copyProperties(m, clazz, copier))
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * 单个对象属性拷贝
//     * @param source 源对象
//     * @param clazz 目标对象Class
//     * @param copier copier
//     * @param <T> 目标对象类型
//     * @param <M> 源对象类型
//     * @return 目标对象
//     */
//    private static <T, M> T copyProperties(M source, Class<T> clazz, BeanCopier copier){
//        if (null == copier){
//            copier = BeanCopier.create(source.getClass(), clazz, false);
//        }
//        T t = null;
//        try {
//            t = clazz.newInstance();
//            copier.copy(source, t, null);
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return t;
//    }

}
