package apps.commons.util;

import apps.commons.entity.TulFileBean;
import apps.commons.exception.ServiceException;
import apps.commons.util.tool_util.HttpKit;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @description: 联邦文件操作工具
 * @author: znegyu
 * @create: 2021-06-09 13:31
 **/
public class TulFileUtil {

    public static final String DOMAIN="http://oss.tul.cn:9009/";

    /**
     * 上传到文件服务器
     * @param file 文件
     * @param url  文件保存路径
     * @return 上传成功后的文件路径
     */
    public static String uploadFile(MultipartFile file, String url) {
        if (file == null) {
            throw new ServiceException("上传文件为空");
        }
        // 上传路径路径
        String dirPathFull ="/"+ url;
        try {
            return TulSshUtils.upload(dirPathFull, file);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 上传图片 会在图片服务器产生 两张图片文件 一张是原图 一张是压缩后的图片
     *
     * @param imageFile -要上传的图片文件
     * @return 上传后的路径
     */
    public static TulFileBean uploadImage(MultipartFile imageFile, String url) {
        if (imageFile == null) {
            throw new ServiceException("上传图片为空");
        }
        // 上传路径路径
        String dirPathFull = "/"+ url;

        try {
            String result = TulSshUtils.uploadImage(dirPathFull, imageFile, true);
            return new TulFileBean(result,result.substring(0, result.lastIndexOf("full"))+"thum"+result.substring(result.lastIndexOf("full")+4));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 获取文件在文件服务器的完整路径
     * @param fileUrl 文件路径
     * @return 文件在文件服务器的完整路径
     */
    public static String getFileCompleteUrl(String fileUrl){
        if(StrUtil.isBlank(fileUrl)){
            throw new ServiceException("文件路径为空");
        }
        return DOMAIN+fileUrl;
    }


    /**
     * 从文件服务器下载文件
     * @param fileUrl 文件路径
     * @param realFileName 重命名文件名
     */
    public static void downloadFile(String fileUrl,String realFileName){
        HttpServletResponse response = HttpKit.getResponse();
        assert response != null;
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/octet-stream");
        response.setHeader("Access-Control-Expose-Headers", "fileName");

        try {
            String downLoadPath =DOMAIN+fileUrl;
            URL url = new URL(downLoadPath);
            try {
                response.setHeader("fileName", URLEncoder.encode(realFileName, "UTF-8"));
                URLConnection conn = url.openConnection();
                Long fileLength = conn.getContentLengthLong();
                response.setHeader("Content-disposition",
                        "attachment; filename=" + new String(realFileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
                response.setHeader("Content-Length", String.valueOf(fileLength));
                BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[1024];
                int bytesRead;
                while (-1 != (bytesRead = bufferedInputStream.read(buff, 0, buff.length))) {
                    bufferedOutputStream.write(buff, 0, bytesRead);
                }
                response.flushBuffer();
                bufferedInputStream.close();
                bufferedOutputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     * @param fileUrl 文件路径
     */
    public static boolean delFile(String fileUrl){
        if(fileUrl.lastIndexOf("/")==-1){
            throw new ServiceException("文件路径错误");
        }
        //截取文件名
        String path=fileUrl.substring(0,fileUrl.lastIndexOf("/"));
        String fileName=fileUrl.substring(fileUrl.lastIndexOf("/")+1);
        return TulSshUtils.delete(path,fileName);
    }





}
