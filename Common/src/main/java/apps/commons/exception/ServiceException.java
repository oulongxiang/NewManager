package apps.commons.exception;

import apps.commons.util.enums.ExceptionEnum;
import cn.hutool.core.text.CharSequenceUtil;

/**
 * @description 封装自定义的异常
 * @author yangxianghua  2020年10月28日 下午17:09:48 周三
 */
public class ServiceException extends java.lang.RuntimeException {
    private static final long serialVersionUID = -8490759249013729324L;

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    /**
     * 构建错误
     * @param exceptionEnum 错误枚举类
     */
    public ServiceException(ExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public ServiceException(String code, String message, Object... objects) {
        this.code = code;
        this.message = CharSequenceUtil.format(message, objects);
    }
    /**
     * 构建返回
     * @param message 返回信息
     */
    public ServiceException(String message) {
        this.code = "00000";
        this.message = message;
    }

    /**
     * 构建返回
     * @param code 错误码
     * @param message 错误信息
     */
    public ServiceException(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
