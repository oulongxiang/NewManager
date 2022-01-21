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
 * 数据字典表
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Dict对象", description="数据字典表")
public class Dict extends Model<Dict> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "字典类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "类型说明(废弃字段)")
    @TableField("type_explain")
    private String typeExplain;

    @ApiModelProperty(value = "说明")
    @TableField("explain")
    private String explain;

    @ApiModelProperty(value = "字典编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "实际值")
    @TableField("value")
    private String value;

    @ApiModelProperty(value = "排序(大的排后面)")
    @TableField("sort")
    private Integer sort;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
