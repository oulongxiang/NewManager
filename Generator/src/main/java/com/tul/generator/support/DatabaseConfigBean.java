package com.tul.generator.support;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据库相关信息bean
 * @author zengyu
 * @date 2021-08-26 08:52
 **/
@Data
public class DatabaseConfigBean {
    @ApiModelProperty(value = "数据库地址")
    private String url;
    @ApiModelProperty(value = "数据库连接驱动")
    private String driverName;
    @ApiModelProperty(value = "数据源名")
    private String dataSourceName;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "数据库类型")
    private String dbType;
    @ApiModelProperty(value = "dao包路径")
    private String daoPackageName;
    @ApiModelProperty(value = "service路径")
    private String servicePackageName;
    @ApiModelProperty(value = "serviceImpl路径")
    private String serviceImplPackageName;
    @ApiModelProperty(value = "entity路径")
    private String entityPackageName;
    @ApiModelProperty(value = "作者")
    private String author;
    @ApiModelProperty(value = "表前缀")
    private String tablePrefix;
    @ApiModelProperty(value = "说明")
    private String comments;
    @ApiModelProperty(value = "是否开启swagger注解")
    private Boolean openSwagger2;
    @ApiModelProperty(value = "是否开启Lombok")
    private Boolean openLombok;
    @ApiModelProperty(value = "是否生成restController")
    private Boolean restController;
    @ApiModelProperty(value = "父包名")
    private String parent;
    @ApiModelProperty(value = "模块名")
    private String moduleName;
    @ApiModelProperty(value = "持久层后缀(一般是 Dao 和 Mapper)")
    private String mapperName;
    @ApiModelProperty(value = "是否开启逻辑删除")
    private Boolean logicDelete;
    @ApiModelProperty(value = "逻辑删除的字段")
    private String logicDeleteFieldName;
    @ApiModelProperty(value = "是否开启创建时间自动填充")
    private Boolean createTimeAuto;
    @ApiModelProperty(value = "创建时间字段名")
    private String createTimeFieldName;
    @ApiModelProperty(value = "是否开启修改时间自动填充")
    private Boolean updateTimeAuto;
    @ApiModelProperty(value = "修改时间字段名")
    private String updateTimeFieldName;

}
