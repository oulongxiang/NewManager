package com.tul.manage.security.vo.request;

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
@ApiModel(value="Organization对象", description="组织表")
public class OrganizationListVo extends Model<OrganizationListVo> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "组织id")
    @TableField("org_id")
    private String orgId;

    @ApiModelProperty(value = "缩写编码")
    private String abridgeCode;

    @ApiModelProperty(value = "风险负责人id **外键** 对应用户表【user_info】字段【id】")
    private String riskDirectorId;

    @ApiModelProperty(value = "风险负责人名")
    private String riskDirectorName;

    @ApiModelProperty(value = "上级组织 org_id")
    @TableField("parent_org_id")
    private String parentOrgId;

    @ApiModelProperty(value = "组织名称")
    @TableField("org_name")
    private String orgName;

    @ApiModelProperty(value = "所属组织机构名字")
    private String parentName;

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

    public OrganizationListVo self(){
        return this;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
