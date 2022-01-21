package com.tul.manage.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tul.manage.security.enums.AnnexModuleEnum;
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
 * @since 2021-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Annex对象", description="附件表")
public class Annex extends Model<Annex> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "附件表id 主键")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "模块id **外键** 对应指定模块名的 id")
    @TableField("module_id")
    private String moduleId;

    @ApiModelProperty(value = "模块名")
    @TableField("module_name")
    private AnnexModuleEnum moduleName;

    @ApiModelProperty(value = "上传用户的id 外键 对应 用户表 【user_info】字段 【id】")
    @TableField("upload_user_id")
    private String uploadUserId;

    @ApiModelProperty(value = "上传的文件名")
    @TableField("upload_file_name")
    private String uploadFileName;

    @ApiModelProperty(value = "上传的文件类型 后缀 如 jpg")
    @TableField("ext")
    private String ext;

    @ApiModelProperty(value = "上传的文件大小 以MB为单位")
    @TableField("size")
    private Float size;

    @ApiModelProperty(value = "文件下载路径")
    @TableField("file_url")
    private String fileUrl;

    @ApiModelProperty(value = "缩略图下载路径")
    @TableField("img_thumb_url")
    private String imgThumbUrl;

    @ApiModelProperty(value = "下载次数")
    @TableField("download")
    private Integer download;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "上传时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
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
