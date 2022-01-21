package apps.commons.tips;

import apps.commons.util.enums.BizExceptionEnum;

/**
 * 返回给前台的提示（最终转化为json形式）
 * @author cuijing
 * @date 2020年10月22日 下午11:12:00
 */
public class Tip {

    protected String code;
    protected String message;
    protected boolean success;

    public Tip() {
        super();
    }

    /**
     * 根据业务异常枚举返回提示信息
     * @param bizException
     */
    public Tip(BizExceptionEnum bizException) {
        this.code = bizException.getCode();
        this.message = bizException.getMessage();
        this.success = false;
    }
    /**
     * success为true则返回操作成功，否则返回操作失败
     * @param success 是否成功
     */
    public Tip(boolean success) {
        this.success = success;
        if (success) {
            this.code = "200";
            this.message = "操作成功";

        } else {
            this.code = BizExceptionEnum.OPERATION_FAILED.getCode();
            this.message = BizExceptionEnum.OPERATION_FAILED.getMessage();
        }
    }

    /**
     * success为true则返回操作成功，否则返回操作失败
     * @param success success 是否成功
     * @param message 返回信息
     */
    public Tip(boolean success, String message) {
        this.success = success;
        if (success) {
            this.code = "200";
            this.message = message;
        } else {
            this.code = BizExceptionEnum.OPERATION_FAILED.getCode();
            this.message = message;
        }
    }

    public boolean isSuccess() {
        return "200".equals(this.code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Tip [code=" + code + ", message=" + message + "]";
    }

    public Tip(String code, String message,boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }
}

