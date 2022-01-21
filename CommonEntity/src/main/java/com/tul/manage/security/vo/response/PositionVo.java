package com.tul.manage.security.vo.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 日志vo
 * @author zengyu
 */
@Data
public class PositionVo {
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "组织id")
    private String postId;
    @ApiModelProperty(value = "组织名称")
    private String postName;
    @ApiModelProperty(value = "说明")
    private String description;
}
