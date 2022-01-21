package com.tul.manage.security.vo.response;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 日志vo
 * @author zengyu
 */
@Data
public class OrganizationVo {
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "组织id")
    private String orgId;
    @ApiModelProperty(value = "风险负责人id userInfo表id")
    private String riskDirectorId;
    @ApiModelProperty(value = "缩写编码")
    private String abridgeCode;
    @ApiModelProperty(value = "上级组织 org_id")
    private String parentOrgId;
    @ApiModelProperty(value = "组织名称")
    private String orgName;
    @ApiModelProperty(value = "说明")
    private String description;


}
