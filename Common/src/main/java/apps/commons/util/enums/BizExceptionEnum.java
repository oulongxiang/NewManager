package apps.commons.util.enums;



/**
 * @author YJH
 * @date 2019/12/6 22:40
 * 错误枚举类
 */
public enum BizExceptionEnum implements ExceptionEnum {


    /**
     * 注意状态码 8000预留TOKEN问题
     */
    TOKEN_OVERDUE("8004", "TOKEN过期"),
    TOKEN_ERROR("401", "用户token不正确，请重新登录"),
    TOKEN_CHECK("401", "token校验失败，请重新登录"),
    TOKEN_LOGINERROR("401", "当前用户已在别处登录，请尝试更改密码或重新登录"),
    TOKEN_LONGTIMENOOPERATION("401", "您长时间未进行操作,请重新登录"),
    TOKEN_NOPERMISSION("8006", "没有权限控制"),
    TOKEN_USEPWD_ERROR("8005", "用户名或者密码错误"),
    USER_AUTHENTICATION_ERROR("8005", "用户认证失败!请先登录!"),
    NOT_USER("401", "账号不存在"),
    TOKEN_ALGORITHM_ERROR("401", "TOKEN签名生成失败!"),
    MAINTAIN_STATUS("405", "系统正在维护!"),

    BODY_NOT_MATCH("400", "数据请求格式失败"),
    OPERATION_FAILED("400", "操作失败"),
    INTERNAL_SERVER_ERROR("500", "服务器错误"),
    AUTHENTICATION_ERROR("401", "用户名或者密码错误"),
    NOT_DATA("9000", "不是预期的数据格式"),
    /**
     * 文件上传
     */
    FILE_READING_ERROR("400", "FILE_READING_ERROR!"),
    FILE_NOT_FOUND("404", "FILE_NOT_FOUND!"),
    UPLOAD_ERROR("500", "上传图片出错"),

    /**
     * 超过数据限制
     */
    NAME_MAX("9000", "超过数据最大限制"),
    /**
     * 404
     */
    NOT_FOUND("404", "NOT_FOUND!"),

    /**
     * 权限和数据问题
     */
    DB_RESOURCE_NULL("400", "数据库中没有该资源"),
    REQUEST_INVALIDATE("400", "请求数据格式不正确"),
    NO_PERMITION("405", "权限异常"),
    NO_PERMITION_INTERCEPTOR("405", "权限异常,拦截器进行权限拦截"),
    INVALID_KAPTCHA("500", "验证码错误"),
    CANT_DELETE_ADMIN("408", "不能删除超级管理员"),
    CANT_FREEZE_ADMIN("408", "不能冻结超级管理员"),
    CANT_CHANGE_ADMIN("408", "不能修改超级管理员角色"),

    /**
     * 账户问题
     */
    USER_ALREADY_REG("400", "用户已注册"),
    USER_NOT_HAVE("400", "账号不存在"),
    USER_NOT_EXISTED("400", "用户信息错误"),
    ACCOUNT_FREEZED("400", "用户已冻结"),
    ACCOUNT_LOCK_SEND("400", "用户已禁止发言"),
    ACCOUNT_DELETE("400", "用户信息错误"),
    OLD_PWD_NOT_RIGHT("402", "密码错误"),
    EMAIL_CODE_RIGHT("402", "邮箱验证码错误"),
    SEND_EMAIL_ERROR("500", "发送邮件失败"),
    TWO_PWD_NOT_MATCH("402", "两次输入密码不一致"),
    AUTH_REQUEST_ERROR("402", "账号或密码错误"),
    UN_AUTH_ERROR("401", "未登陆权限"),
    AUTH_SUCCESS("200", "已登陆"),
    NO_ROLE_ERROR("400", "用户没有角色"),
    NO_EXIST_EMAIL("400", "没有绑定邮箱"),
    SUPERIOR_ERROR("501", "上级领导不正确"),
    PHONE_ERROR("502", "手机号码已存在"),

    /**
     * 错误的请求
     */
    REQUEST_NULL("400", "请求有错误"),
    SESSION_TIMEOUT("401", "会话超时"),
    REDIS_ERROR("406", "redis操作异常"),
    MYSQL_ERROR("407", "mysql操作异常"),
    SERVER_ERROR("500", "访问失败"),
    INSERT_USER_ERROR("500", "插入用户失败"),
    INSERT_ROLE_ERROR("500", "插入角色失败"),
    INSERT_CLIENT_ERROR("500", "导入客户失败,请检查数据是否重复!"),
    INSERT_COUNT_ERROR("500", "导入客户失败,每个用户只允许绑定150位客户!"),


    /**
     * 业务返回 接口
     */
    MODULE_EMPTY("9001", "查询数据为空"),
    MODULE_EXISTS("9002", "存在重复数据"),

    /**
     * 报账、预算审核页面接口
     */
    UPDATE_ADDAPPLY("9100", "提交失败，存在下属未审核的数据！请先审核下属的数据，在进行提交"),
    UPDate_REPEATADDPLY("9100", "提交失败！不允许反复提交"),
    UPDATE_NOADDAPPLY("9100", "免提交失败！下游数据存在未审核的数据"),
    UPDATE_unCHECKAPPLY("9100", "上级已审核或者已提交数据，不予许反审核！"),
    ADD_ACCOUNTS("9100", "报账已审核或者提交，不允许新增明细或者修改明细"),
    ADD_ACCOUNTS1("9100", "报账已审核，不允许新增明细或者修改明细"),
    DELETE_ACCOUNTS("9100", "报账已审核或者提交，不允许删除明细"),
    DELETE_ACCOUNTS1("9100", "报账已审核，不允许删除明细"),
    ACCOUNTS_APPLYSUMBITNO("9100", "未提交数据，不允许反提交！"),
    ACCOUNTS_APPLYCHECKYES("9050", "已审核，不允许反提交！"),


    /**
     * 报账录入接口
     */
    DELETE_AccountList("9101", "存在已预算数据，不允许删除，只能修改报账数据"),


    /**
     * 预算接口  错误返回
     */
    BUDGET_APPLYID_NOTONLYONE("9050", "当前期间自己存在不止一条业务表数据，请联系电脑部排查！"),
    BUDGET_APPLYLEADERID_NOTONLYONE("9050", "当前期间上级存在不止一条业务表数据，请联系电脑部排查！"),
    BUDGET_APPLYSUMBITYES("9050", "已提交，不予许重复提交！"),
    BUDGET_APPLYCHECKYES("9050", "已审核，不予许重复提交或者反提交！"),
    BUDGET_APPLYNOSUMBITANDCHECK("9050", "下属存在未审核或者未提交数据，请先处理！"),
    BUDGET_APPLYSUMBITNO("9050", "未提交数据，不允许反提交！"),
    BUDGET_APPLYSUMBITNOC("9050", "该员工的数据还未提交，请先提交或者免提交再审核！"),
    BUDGET_APPLYCHECKYESC("9050", "已审核，不予许重复审核！"),
    BUDGET_APPLYCHECKNO("9050", "不是审核状态，不予许反审核！"),
    BUDGET_APPLYSUMBITANDCHECKYES("9050", "上级已审核或者已提交数据，不予许反审核！"),
    BUDGET_NOSAVE("9050", "已提交数据不允许操作，请联系上级帮忙调整数据！"),
    /**
     * Role错误
     */
    ROLE_ADDUSER("9300", "提交失败，当前未选中用户"),
    ROLE_ADDROLE("9300", "提交失败，当前未选中角色"),

    /**
     * 核准错误
     */

    /**
     * 权限错误
     */
    RPM_DATALOSS("9300", "提交失败，获取不到用户，请重新登录！"),
    RPM_NOTNAR("400", "您没有任何登录信息！"),

    POSTTION_EXTIS("9001", "数据已存在"),
    SCORER_DATA("9400", "必须提供至少一项"),

    /**
     * 表单设置
     */
    SCORERE_QUESTION("9401", "并未提供所有问题回答。"),
    SCORERE_EXITS("9402", "该表单已经提交，无需再度提交。"),


    ACT_AUTH_ERROR("403", "无权限操作"),

    /**
     * 规则明细 错误返回
     */
    RULELIST_STATE_ERROR("403", "规则明细状态正在执行不允许删除");

    BizExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}

