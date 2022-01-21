package apps.commons.util.file;

public class FileUtils {
    /**
     * 获取一个文件名的后缀名
     * @author caicai, 2014年10月24日 下午4:16:30
     * @param filename 文件完整名称
     * @param defaultSuffix 没有找到后缀时返回这个字符窜作为默认后缀
     * @return 返回的后缀没有“.”
     */
    public static String getSuffix(String filename, String defaultSuffix) {
        int lastPoint = filename.lastIndexOf('.');
        if (lastPoint == -1 || lastPoint == filename.length() - 1) {
            return defaultSuffix;
        } else {
            return filename.substring(lastPoint + 1, filename.length());
        }
    }
}
