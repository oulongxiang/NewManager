package apps.commons.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 图片上传路径接收Bean
 * @author: znegyu
 * @create: 2021-06-18 16:56
 **/
@Data
@AllArgsConstructor
public class TulFileBean {
    @ApiModelProperty(value = "原图url")
    private String originalPictureUrl;
    @ApiModelProperty(value = "压缩图url")
    private String compressedPictureUrl;
}
