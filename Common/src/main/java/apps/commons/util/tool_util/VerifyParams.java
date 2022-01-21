package apps.commons.util.tool_util;

import apps.commons.exception.ServiceException;
import apps.commons.util.enums.ExceptionEnum;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.StrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class VerifyParams {

    private static Logger logger = LoggerFactory.getLogger(VerifyParams.class);


    /* 功能描述: <br>
     * 检查一个字符串参数<br>
     * 如果为空且minLength>0，则抛出：请填写xxx<br>
     * 如果长度小于minLength，则抛出：xxx至少填写minLength个字符<br>
     * 如果长度大于maxLength，则抛出：xxx不能超过maxLength个字符（已超出n个字符）<br>
     * @author yangxianghua  2021年01月18日 下午12:00:18 周一
     * @param: col 参数值
     * @param: colName 参数名称
     * @param: minLength 最少要求字符数量
     * @param: maxLength 最大要求字符数量
     * @param: allowNull 是否允许null
     * @return: void
     */
    public static void checkStrings(String col, String colName, Integer minLength, Integer maxLength, boolean notNull) {

        if (ToolUtil.isEmpty(col) && notNull) {
            throw new ServiceException("请填写：" + colName);
        }

        int length = col.length(); //字符长度

        if (minLength.equals(maxLength) && length != minLength) {
            throw new ServiceException("{1}需要填写{2}个字符", colName, minLength);// 小于固定长度
        }
        if (length < minLength) {
            throw new ServiceException("{1}至少填写{2}个字符", colName, minLength); // 小于最小值
        }
        if (length > maxLength) {
            throw new ServiceException("{1}不能超过{2}个字符（已超出{3}个字符）", colName, maxLength, (maxLength - maxLength)); // 大于最大值
        }
    }

    /* 功能描述: <br>
     * 检查一个对象是否为空
     * @author yangxianghua  2021年01月18日 下午13:55:43 周一
     * @param: o
     * @param: colName
     * @return: void
     */
    public static void checkEmpty(Object o, String tips) {
        if (ToolUtil.isEmpty(o)) {
            throw new ServiceException("{1}不能为空！", tips);
        }
    }

    /**
     * 功能描述: <br>
     * 检查一个对象是否为空，如果为空则抛出提示语
     *
     * @author yangxianghua  2021年03月12日 下午14:51:09 周五
     * @param: o 判断的对象
     * @param: tips 提示语
     * @return: void
     */
    public static void throwEmpty(Object o, String tips) {
        if (ToolUtil.isEmpty(o)) {
            throw new ServiceException(tips);
        }
    }

    /* 功能描述: <br>
     * 检查一个对象是否为空
     * @author yangxianghua  2021年01月18日 下午13:55:43 周一
     * @param: o
     * @param: colName
     * @return: void
     */
    public static void checkNotEmpty(Object o, String tips) {
        if (ToolUtil.isNotEmpty(o)) {
            throw new ServiceException(tips);
        }
    }

    /***
     * 字符串为空时，抛出错误信息 (null和空字符串)
     * @author zengyu
     * @date 2021年09月17日 09:06
     * @param str 要检验的字符串
     * @param tip 为空时的错误提醒
     */
    public static void throwStrBlank(CharSequence str,String tip){
        if(StrUtil.isBlank(str)){
            throw new ServiceException(tip);
        }
    }

    /***
     * 字符串为空时，抛出错误信息 (null和空字符串)
     * @author zengyu
     * @date 2021年09月17日 09:07
     * @param str 要检验的字符串
     * @param exceptionEnum 错误枚举
     */
    public static void throwStrBlank(CharSequence str, ExceptionEnum exceptionEnum){
        if(StrUtil.isBlank(str)){
            throw new ServiceException(exceptionEnum.getMessage());
        }
    }

    /***
     * 字符串不为空时，抛出错误信息
     * @author zengyu
     * @date 2021年09月17日 09:06
     * @param str 要检验的字符串
     * @param tip 不空时的错误提醒
     */
    public static void throwStrNotBlank(CharSequence str,String tip){
        if(StrUtil.isNotBlank(str)){
            throw new ServiceException(tip);
        }
    }

    /***
     * 字符串不为空时，抛出错误信息
     * @author zengyu
     * @date 2021年09月17日 09:07
     * @param str 要检验的字符串
     * @param exceptionEnum 错误枚举
     */
    public static void throwStrNotBlank(CharSequence str, ExceptionEnum exceptionEnum){
        if(StrUtil.isNotBlank(str)){
            throw new ServiceException(exceptionEnum.getMessage());
        }
    }

    /***
     * 列表为空时，抛出错误信息 (null和空列表)
     * @author zengyu
     * @date 2021年09月17日 09:06
     * @param iterable 要检验的列表
     * @param tip 为空时的错误提醒
     */
    public static void throwIterEmpty(Iterable<?> iterable,String tip){
        if (IterUtil.isEmpty(iterable)) {
            throw new ServiceException(tip);
        }
    }

    /***
     * 列表为空时，抛出错误信息 (null和空列表)
     * @author zengyu
     * @date 2021年09月17日 09:06
     * @param iterable 要检验的列表
     * @param exceptionEnum 错误枚举
     */
    public static void throwIterEmpty(Iterable<?> iterable,ExceptionEnum exceptionEnum){
        if (IterUtil.isEmpty(iterable)) {
            throw new ServiceException(exceptionEnum.getMessage());
        }
    }

    /***
     * 对象为null时，抛出错误信息
     * @author zengyu
     * @date 2021年09月17日 09:14
     * @param bean 要检验的对象
     * @param exceptionEnum 错误提醒
     */
    public static void throwObjectEmpty(Object bean, ExceptionEnum exceptionEnum){
        if(bean==null){
            throw new ServiceException(exceptionEnum.getMessage());
        }
    }

    /***
     * 对象为null时，抛出错误信息
     * @author zengyu
     * @date 2021年09月17日 09:14
     * @param bean 要检验的对象
     * @param tip 错误枚举
     */
    public static void throwObjectEmpty(Object bean, String tip){
        if(bean==null){
            throw new ServiceException(tip);
        }
    }


    /**
     * 格式化一个字符串
     *
     * @param str  字符串原文
     * @param objs 自动根据顺序替换原文中得“{n}”这样的字符串，n从1开始
     * @return
     * @author caicai, 2015年3月6日 下午10:23:18
     */
    public static String formats(String str, Object... objs) {
        if (str == null || str.length() == 0) {
            return str;
        }
        for (int i = 0; i < objs.length; i++) {
            String obj;
            if (objs[i] == null) {
                obj = "";
            } else {
                obj = objs[i].toString().replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
            }
            str = str.replaceAll("\\{" + (i + 1) + "\\}", obj);
        }
        return str;
    }

    /* 功能描述: <br>
     * 检查一个数字是否为有效数字，为null或者小于0时抛出ServiceException异常
     * @author yangxianghua  2021年01月18日 下午14:56:08 周一
     * @param: num
     * @param: paramName 如果obj为null则抛出“参数无效：paramName = num
     * @return: void
     */
    public static void checkValidIntegerParams(Integer num, String paramName) {
        if (num == null || num <= 0) {
            throw new ServiceException("参数无效: " + paramName + " = " + num);
        }
    }

    /* 功能描述: <br>
     * 检查一个Number参数<br>
     * 如果为null且nullable=false，则抛出：请填写 xxx<br>
     * 如果小于min，则抛出：xxx 不能少于min<br>
     * 如果大于max，则抛出：xxx 不能大于max
     * @author yangxianghua  2021年01月18日 下午15:03:37 周一
     * @param: num
     * @param: name
     * @param: nullable
     * @param: min
     * @param: max
     * @param: unit 数据单位，为了方便才用可变参数，可以不传，要传只能传一个。
     * @return: void
     */
    public static <T extends Number> void checkNumber(T num, String name, boolean nullable, T min, T max, String unit) {
        if (num == null) {
            if (!nullable) {
                throw new ServiceException("请填写{1}", name);
            }
        } else {
            if (min == null) {
                throw new RuntimeException("请传入参数：min");
            }
            if (max == null) {
                throw new RuntimeException("请传入参数：max");
            }
            boolean tooSmall, tooBig;
            if (Integer.class.isAssignableFrom(num.getClass()) //
                    || int.class.isAssignableFrom(num.getClass())) {
                tooSmall = num.intValue() < min.intValue();
                tooBig = num.intValue() > max.intValue();

            } else if (Float.class.isAssignableFrom(num.getClass()) //
                    || int.class.isAssignableFrom(num.getClass())) {
                tooSmall = num.floatValue() < min.floatValue();
                tooBig = num.floatValue() > max.floatValue();

            } else if (Long.class.isAssignableFrom(num.getClass()) //
                    || int.class.isAssignableFrom(num.getClass())) {
                tooSmall = num.longValue() < min.longValue();
                tooBig = num.longValue() > max.longValue();

            } else if (Double.class.isAssignableFrom(num.getClass()) //
                    || int.class.isAssignableFrom(num.getClass())) {
                tooSmall = num.doubleValue() < min.doubleValue();
                tooBig = num.doubleValue() > max.doubleValue();

            } else if (Short.class.isAssignableFrom(num.getClass()) //
                    || int.class.isAssignableFrom(num.getClass())) {
                tooSmall = num.shortValue() < min.shortValue();
                tooBig = num.shortValue() > max.shortValue();

            } else {
                throw new RuntimeException("不支持的类型：" + num.getClass().getName());

            }
            if (tooSmall) {
                throw new ServiceException("{1}不能少于{2}{3}", name, min, unit);
            } else if (tooBig) {
                throw new ServiceException("{1}不能大于{2}{3}", name, max, unit);
            }
        }
    }

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
}