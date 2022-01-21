package apps.commons.util.wrapper.map;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 对象功能：构建map
 * 开发人员：曾煜
 * 创建时间：2021/3/20 22:33
 * </pre>
 *
 * @author zengyu
 */
public abstract class AbstractWrapper<T, R, Children extends AbstractWrapper<T, R, Children>> implements Compare<Children, R> {
    /**
     * 传入的list
     */
    private final List<T> data;

    /**
     * 返回值
     */
    private List<Map<String, Object>> result;

    /**
     * 泛型类型
     */
    private Class<?> dataClass;

    /**
     * 基础构造函数,只是为了先初始化一个和传入list相同长度的空返回值
     *
     * @param data 传入的list
     */
    protected AbstractWrapper(List<T> data) {
        this.data = data;
        this.result = new ArrayList<>();
        if (this.data != null && this.data.size() > 0) {
            this.dataClass = this.data.get(0).getClass();
            List<Map<String, Object>> temp = new ArrayList<>();
            for (int i = 0; i < this.data.size(); i++) {
                temp.add(new HashMap<>());
            }
            this.result = temp;
        }
    }

    public List<Map<String, Object>> build() {
        return result;
    }


    @Override
    public Children put(R column, String key) {
        String lambdaColumnName = getLambdaColumnName(column);
        for (int i = 0; i < this.data.size(); i++) {
            try {
                Field field = this.data.get(i).getClass().getDeclaredField(lambdaColumnName);
                field.setAccessible(true);
                if (key != null && key.trim().length() > 0) {
                    this.result.get(i).put(key, field.get(this.data.get(i)));
                } else {
                    this.result.get(i).put(lambdaColumnName, field.get(this.data.get(i)));
                }

            } catch (Exception e) {
                return returnError();
            }
        }
        return (Children) this;
    }

    @Override
    public Children remove(R column) {
        if (this.result == null || this.result.size() == 0) {
            return returnError();
        }
        if (this.result.get(0).isEmpty()) {
            Field[] declaredFields = this.dataClass.getDeclaredFields();
            for (int i = 0; i < this.data.size(); i++) {
                for (Field declaredField : declaredFields) {
                    try {
                        Field field = this.data.get(i).getClass().getDeclaredField(declaredField.getName());
                        field.setAccessible(true);
                        Map<String, Object> temp = new HashMap<>();
                        if (this.result.get(i) != null) {
                            temp = this.result.get(i);
                        } else {
                            this.result.add(temp);
                        }
                        temp.put(declaredField.getName(), field.get(this.data.get(i)));
                    } catch (Exception e) {
                        return returnError();
                    }
                }
            }
        }
        for (Map<String, Object> stringObjectMap : this.result) {
            String lambdaColumnName = getLambdaColumnName(column);
            stringObjectMap.remove(lambdaColumnName);
        }
        return (Children) this;
    }

    /**
     * 获取列名称
     *
     * @param lambda lambda
     * @return String
     */
    private String getLambdaColumnName(R lambda) {
        try {
            Method method = lambda.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(lambda);
            String getter = serializedLambda.getImplMethodName();
            return Introspector.decapitalize(getter.replace("get", ""));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 错误时返回空列表
     *
     * @return 空列表
     */
    private Children returnError() {
        this.result = new ArrayList<>();
        return (Children) this;
    }

}
