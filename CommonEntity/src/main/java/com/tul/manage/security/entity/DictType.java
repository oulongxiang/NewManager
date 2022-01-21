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
 * 字典类型表
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="DictType对象", description="字典类型表")
public class DictType extends Model<DictType> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id 主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "字典类型名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "排序(大的排后面)")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "字典类型说明")
    @TableField("explain")
    private String explain;

    @ApiModelProperty(value = "是否删除")
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "描述")
    @TableField("describe")
    private String describe;

    public DictType self(){
        return this;
    }


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
