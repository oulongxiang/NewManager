package com.tul.manage.security.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tul.manage.security.enums.IconTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 图标表(基于阿里图标库)
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Icon对象", description="图标表(基于阿里图标库)")
public class Icon extends Model<Icon> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "图标id(主键)")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "图标名称")
    @TableField("icon_name")
    private String iconName;

    @ApiModelProperty(value = "图标代码(要与阿里图标库保持一致)")
    @TableField("icon_code")
    private String iconCode;

    @ApiModelProperty(value = "图标分类")
    @TableField("icon_type")
    private IconTypeEnum iconType;

    @ApiModelProperty(value = "说明")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "是否删除")
    @TableField("is_delete")
    private Boolean isDelete;

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
