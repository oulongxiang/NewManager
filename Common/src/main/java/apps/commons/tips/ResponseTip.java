package apps.commons.tips;

import io.swagger.annotations.ApiModel;


/**
 * 返回给前台的List封装信息
 *
 * @param <T>
 * @author YJH
 * @date 2019年10月22日 下午11:11:14
 */
@ApiModel(value = "ResponseTip", description = "通用返回对象")
public class ResponseTip<T> extends Tip {

    private T data;

    public ResponseTip() {
        super(true);
    }

    public ResponseTip(boolean success) {
        super(success);
    }

    /**
     * 自定义提示code和返回数据类型
     *
     * @param code
     * @param data
     */
    public ResponseTip(String code, T data) {
        super.code = code;
        this.data = data;
        if (!"200".equals(code)) {
            super.success = false;
            super.message = "访问失败";
        } else {
            super.message = "操作成功";
            super.success = true;
        }
    }

    public ResponseTip(String code, T data, String message) {
        super.code = code;
        this.data = data;
        super.message = message;
        if (!"200".equals(code)) {
            super.success = false;
        } else {
            super.success = true;
        }
    }

    public ResponseTip(T data) {
        super.code = "200";
        this.data = data;
        super.message = "操作成功";
        super.success = true;
    }

    public ResponseTip(T data, String message) {
        super.code = "200";
        this.data = data;
        super.message = message;
        super.success = true;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
