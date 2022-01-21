package com.tul.manage.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 控件元素表
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Element对象", description="控件元素表")
public class Element extends Model<Element> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "控件元素id(主键)")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "控件元素编码")
    @TableField("element_code")
    private String elementCode;

    @ApiModelProperty(value = "控件元素名称")
    @TableField("element_name")
    private String elementName;

    @ApiModelProperty(value = "控件元素图标(有的话)")
    @TableField("element_icon")
    private String elementIcon;

    @ApiModelProperty(value = "点击控件执行的接口URI(有的话)")
    @TableField("api_uri")
    private String apiUri;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "是否删除")
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
