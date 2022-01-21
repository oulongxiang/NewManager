package apps.commons.util.tool_util;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @author YJH
 * @date 2019/12/6 22:46
 */

public class ResponseEntity {

    /**
     * 新建一个Set
     *
     * @param args
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> newSet(T... args) {
        int length = args == null ? 1 : args.length;
        Set<T> set = new HashSet<T>(length);
        if (args == null) {
            set.add(null);
        } else {
            for (int i = 0; i < args.length; i++) {
                set.add(args[i]);
            }
        }
        return set;
    }

    /**
     * 新建一个List
     *
     * @param args
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> newList(T... args) {
        int length = args == null ? 1 : args.length;
        List<T> list = new ArrayList<T>(length);
        if (args == null) {
            list.add(null);
        } else {
            for (int i = 0; i < args.length; i++) {
                list.add(args[i]);
            }
        }
        return list;
    }

    /**
     * 新建一个JSON对象，必须是偶数个参数
     *
     * @param args
     * @return
     */
    public static JSONObject newJSON(Object... args) {
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
     * 新建一个Map，必须是偶数个参数 注意，这是一个同步的Map，且key和value不能为null
     *
     * @param args
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> newMap(Object... args) {
        Map<K, V> map = new HashMap<K, V>();
        if (args != null) {
            if (args.length % 2 != 0) {
                throw new IllegalArgumentException("The number of arguments must be an even number");
            }
            for (int i = 0; i < args.length; i += 2) {
                map.put((K) args[i], (V) args[i + 1]);
            }
        }
        return map;
    }

    @Override
    public String toString() {
        return "ResponseEntity [toString()=" + super.toString() + "]";
    }

}
