package com.tul.manage.warning.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 负责区域表
 *
 * @author Automatic Generation
 * @date 2021-09-14 14:30:37
 */
@Data
@TableName("territory")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "负责区域表")
public class Territory extends Model<Territory> {

    private static final long serialVersionUID=1L;

    /**
     * id - 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="id - 主键")
    private String id;

    /**
     * 负责人 userId **外键** 对应 用户表【user_info】 字段【id】
     */
    @ApiModelProperty(value="负责人 userId **外键** 对应 用户表【user_info】 字段【id】")
    @TableField("user_id")
    private String userId;

    /**
     * 负责的组织id **外键** 对应 组织表【organization】 字段【org_id】
     */
    @ApiModelProperty(value="负责的组织id **外键** 对应 组织表【organization】 字段【org_id】")
    @TableField("org_id")
    private String orgId;

    /**
     * 是否删除
     */
    @ApiModelProperty(value="是否删除")
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @TableField(value="create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    @TableField(value="update_time",fill = FieldFill.INSERT)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
