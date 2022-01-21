package apps.commons.util.tool_util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户设备信息
 * @author ZJL
 * @date 2019年10月22日 下午8:51:51
 */
@Data
@ApiModel(value="UserAgentMessage对象", description="用户设备信息")
public class UserAgentMessage implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "请求地址", required=false)
	private String uri;

	@ApiModelProperty(value = "IP地址", required=false)
	private String ip;
	
	@ApiModelProperty(value = "请求类型", required=false)
	private String method;
	
	@ApiModelProperty(value = "设备类型", required=false)
	private String deviceType;
	
	@ApiModelProperty(value = "浏览器内核", required=false)
	private String browser;
	
	@ApiModelProperty(value = "代理信息", required=false)
	private String userAgent;
	
	@ApiModelProperty(value = "请求参数", required=false)
	private String params;
	
	
}
