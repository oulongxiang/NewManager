package com.tul.manage.security.vo.response;

import com.tul.manage.security.entity.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class DictVo extends Dict {

    @ApiModelProperty(value = "主表类型名")
    private String typeName;

}
