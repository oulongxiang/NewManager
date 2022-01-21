package com.tul.manage.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.tul.manage.security.enums.ApiLogTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ApiLog对象", description="请求日志对象")
public class ApiLog extends Model<ApiLog> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "类型")
    @TableField("type")
    private ApiLogTypeEnum type;

    @ApiModelProperty(value = "请求参数json")
    @TableField("param")
    private String param;

    @ApiModelProperty(value = "请求url")
    @TableField("api_url")
    private String apiUrl;

    @ApiModelProperty(value = "响应数据")
    @TableField("result")
    private String result;

    @ApiModelProperty(value = "响应状态 成功-true 失败-false")
    @TableField("response_state")
    private Boolean responseState;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "创建/推送时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除")
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
