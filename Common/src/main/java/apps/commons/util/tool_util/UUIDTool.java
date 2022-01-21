package apps.commons.util.tool_util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.util.UUID;

public class UUIDTool {

    public UUIDTool() {
    }

    /**
     * 自动生成32位的UUID，对应数据库的主键id进行插入用。
     * @return
     */
    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 自动生成64位的UUID，对应数据库的主键id进行插入用。
     * @return
     */
    public static String getUUID64() {
        String u1 = UUID.randomUUID().toString().replace("-", "");
        String u2 = UUID.randomUUID().toString().replace("-", "");
        return u1+u2;
    }


    /**
     * 截取8位的UUID
     * @return
     */
    public static String getUUID8() {
        String str = UUID.randomUUID().toString().replace("-", "");
        return  str.substring(0, 8);
    }


    /**
     * 生成数字类的UUID
     * @return
     */
    public static String getId() {
        return String.valueOf(IdWorker.getId());
    }


    /**
     * 生成数字类的UUID
     * @return
     */
    public static long getIdLong() {
        return IdWorker.getId();
    }


    /**
     * 生成32为UUID
     * @return
     */
    public static String get32UUID() {
        return IdWorker.get32UUID();
    }


    /**
     * 生成64为UUID
     * @return
     */
    public static String get64UUID() {
        return IdWorker.get32UUID()+IdWorker.get32UUID();
    }


    public static void main(String[] args) {

    }


}
