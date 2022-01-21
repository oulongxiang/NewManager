package com.tul.manage.currency;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description: 用于返回级联数据的对象
 * @author: znegyu
 * @create: 2021-08-12 08:48
 **/
@Data
public class Cascade {

    @ApiModelProperty(value = "级联选择的值")
    private String value;

    @ApiModelProperty(value = "级联选择的文本")
    private String label;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "级联下级")
    List<Cascade> children;
}
