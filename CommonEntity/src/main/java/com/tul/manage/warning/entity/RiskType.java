package com.tul.manage.warning.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 风险类别表
 *
 * @author Automatic Generation
 * @date 2021-11-15 16:21:09
 */
@Data
@TableName("risk_type")
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "风险类别表")
public class RiskType extends Model<RiskType> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="主键")
    private String id;

    /**
     * 风险类别名称
     */
    @ApiModelProperty(value="风险类别名称")
    @TableField("risk_type")
    private String riskType;

    /**
     * 录入人
     */
    @ApiModelProperty(value="录入人")
    @TableField("create_emp_id")
    private String createEmpId;

    /**
     * 录入时间
     */
    @ApiModelProperty(value="录入时间")
    @TableField(value="create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    @TableField(value="update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @ApiModelProperty(value="是否删除")
    @TableLogic
    @TableField(value="is_deleted",fill = FieldFill.INSERT)
    private Boolean isDeleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
