package apps.commons.tips;

import apps.commons.util.tool_util.ResponseEntity;
import com.alibaba.fastjson.JSONObject;

/**
 *  返回给前台的成功提示
 * @author ZJL
 * @date 2019年10月22日 下午11:11:40
 */
public class SuccessTip extends Tip {

    public SuccessTip() {
        super.code = "200";
        super.message = "操作成功";
    }

    public SuccessTip(String message) {
        super.code = "200";
        super.message = message;
    }

    public JSONObject getSuccessTip(Object... args) {
        JSONObject result = ResponseEntity.newJSON(args);
        if (!result.containsKey("code")) {
            result.put("code", 200);
        }
        if (!result.containsKey("message")) {
            result.put("message", "操作成功");
        }
        return result;
    }

    @Override
    public String toString() {
        return "SuccessTip [code=" + code + ", message=" + message + "]";
    }
}

