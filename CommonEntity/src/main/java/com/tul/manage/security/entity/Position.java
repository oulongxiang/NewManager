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
 * 
 * </p>
 *
 * @author Automatic Generation
 * @since 2021-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Position对象", description="岗位表")
public class Position extends Model<Position> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "岗位id")
    @TableField("post_id")
    private String postId;

    @ApiModelProperty(value = "岗位名称")
    @TableField("post_name")
    private String postName;

    @ApiModelProperty(value = "说明")
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

    public Position self(){
        return this;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
