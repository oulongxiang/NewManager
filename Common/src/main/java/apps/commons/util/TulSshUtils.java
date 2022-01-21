package apps.commons.util;

import apps.commons.exception.ServiceException;
import apps.commons.util.tool_util.VerifyParams;
import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.Vector;

/**
 * 联邦制药SFTP工具类
 *
 * @author yangxianghua, 2021年1月4日 上午10:45:05
 */
@Slf4j
public class TulSshUtils {

    /**
     * 对外访问url
     */
    public static final String domain = "http://oss.tul.cn:9009/";

    private static final String projectName = "tul-manage";

    private static final String username = "ftpuser";

    private static final String password = "tul3933";

    public static final String directory = "/tulfile/";

    private static final int port = 6636;

    private static ChannelSftp sftp;

    private static TulSshUtils instance = null;


    /**
     * 原图文件夹名
     **/
    private final static String FOLDER_FULL = "full";
    /**
     * 缩略图
     **/
    private final static String FOLDER_THUM = "thum";


    private TulSshUtils() {
    }

    static {
        if (instance == null) {
            instance = new TulSshUtils();
            // 获取连接
            sftp = instance.connect("oss.tul.cn", port, username, password);
        }
    }


    /**
     * 连接sftp服务器
     *
     * @param host     主机
     * @param port     端口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public ChannelSftp connect(String host, int port, String username, String password) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            log.info("SFTP Session connected.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            log.info("Connected to " + host);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return sftp;
    }

    /**
     * 上传图片
     *
     * @param folderPath 可空,目标文件夹的路径,不传则上传至主目录
     * @param imageFile  需要上传的图片文件
     * @param isCompress 是否生成缩略图 true-是 false-否
     * @return 文件的地址
     * @throws SftpException
     * @author yangxianghua, 2021年1月4日 上午11:13:16
     */
    public static String uploadImage(String folderPath, MultipartFile imageFile, boolean isCompress) throws SftpException {
        StringBuffer result = new StringBuffer("");
        String fileDirectory = projectName;
        // 判断是否传进文件夹参数
        if (!VerifyParams.isEmpty(folderPath)) {
            // 判断处理文件夹
            if (!folderPath.substring(0, 1).equals("/")) {
                folderPath = "/" + folderPath;
            }
            fileDirectory += folderPath;
        }

        result.append(fileDirectory);
        fileDirectory = directory + fileDirectory;
        String pathArry[] = fileDirectory.split("/");
        StringBuffer filePath = new StringBuffer("/");
        //如果文件夹不存在，则创建文件夹
        for (String path : pathArry) {
            if (path.equals("")) {
                continue;
            }
            filePath.append(path + "/");
            sftp.cd(filePath.toString());
            if (isExistDir(filePath.toString())) {
                sftp.cd(filePath.toString());
            } else {
                // 建立目录
                sftp.mkdir(filePath.toString());
                // 进入并设置为当前目录
                sftp.cd(filePath.toString());
            }
        }
        // 切换到指定文件夹
        sftp.cd(fileDirectory);
        // 设置唯一文件名
        String fileName = getFileName(imageFile);
        String jpgFileName=   fileName.substring(0,fileName.lastIndexOf("."))+".jpg";
        //如果需要创建缩略图,需要额外创建 原图 和 缩略图 两个文件夹
        if (isCompress) {
            //原图文件夹 和 缩略图文件夹 不存在时 创建对应文件夹
            result.append("/" + FOLDER_FULL);
            if (!isExistDir(fileDirectory + "/" + FOLDER_FULL)) {
                sftp.mkdir(fileDirectory + "/" + FOLDER_FULL);
            }
            if (!isExistDir(fileDirectory + "/" + FOLDER_THUM)) {
                sftp.mkdir(fileDirectory + "/" + FOLDER_THUM);
            }
            //进入原图文件夹
            sftp.cd(fileDirectory + "/" + FOLDER_FULL);
        }
        OutputStream os = null;
        try {
            result.append("/" + jpgFileName);
            // 上传
            os = sftp.put(fileName);
            os.write(imageFile.getBytes());
            //重命名
            sftp.rename(fileName,jpgFileName);
            os.close();
            // 关闭流
            if (isCompress) {
                // 生成缩略图
                InputStream inputStream = sftp.get(jpgFileName);
                BufferedImage bi = Thumbnails.of(inputStream).scale(1f).outputQuality(0.15d).asBufferedImage();
                bi=ensureOpaque(bi);
                inputStream.close();
                sftp.cd(fileDirectory + "/" + FOLDER_THUM);
                os=sftp.put(jpgFileName);
                ByteArrayOutputStream bo=new ByteArrayOutputStream();
                ImageIO.write(bi,"jpg",bo);
                byte[] bytes = bo.toByteArray();

                //InputStream is = bufferedImageToInputStream(bi, getFileSuffix(imageFile));
                //进入缩略图文件夹
                //sftp.cd(fileDirectory + "/" + FOLDER_THUM);
                os.write(bytes);
                bo.close();
                os.close();
                //sftp.put(is, jpgFileName);
                //is.close();
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        return result.toString();
    }


    /**
     * 上传文件
     *
     * @param folderPath 可空,目标文件夹的路径,不传则上传至主目录
     * @param file       需要上传的文件
     * @return 文件的地址
     * @throws SftpException
     * @author yangxianghua, 2021年1月4日 上午11:13:16
     */
    public static String upload(String folderPath, MultipartFile file) throws SftpException {
        StringBuffer result = new StringBuffer("");

        String fileDirectory = projectName;

        // 判断是否传进文件夹参数
        if (!VerifyParams.isEmpty(folderPath)) {
            // 判断处理文件夹
            if (folderPath.charAt(0) != '/') {
                folderPath = "/" + folderPath;
            }
            fileDirectory += folderPath;
        }

        result.append(fileDirectory);

        fileDirectory = directory + fileDirectory;
        String pathArry[] = fileDirectory.split("/");
        StringBuffer filePath = new StringBuffer("/");

        //如果文件夹不存在，则创建文件夹
        for (String path : pathArry) {
            if (path.equals("")) {
                continue;
            }
            filePath.append(path + "/");
            if (isExistDir(filePath.toString())) {
                sftp.cd(filePath.toString());
            } else {
                // 建立目录
                sftp.mkdir(filePath.toString());
                // 进入并设置为当前目录
                sftp.cd(filePath.toString());
            }
        }
        //切换到指定文件夹
        sftp.cd(fileDirectory);
        // 设置唯一文件名
        String fileName = getFileName(file);
        OutputStream os = null;
        try {
            result.append("/").append(fileName);
            sftp.put(file.getInputStream(), fileName);
            
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
        return result.toString();
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public File download(String directory, String downloadFile, String saveFile) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            sftp.get(downloadFile, fileOutputStream);
            fileOutputStream.close();
            return file;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 下载文件
     *
     * @param downloadFilePath 下载的文件完整目录
     * @param saveFile         存在本地的路径
     */
    public File download(String downloadFilePath, String saveFile) {
        try {
            int i = downloadFilePath.lastIndexOf('/');
            if (i == -1) {
                return null;
            }
            sftp.cd(downloadFilePath.substring(0, i));
            File file = new File(saveFile);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            sftp.get(downloadFilePath.substring(i + 1), fileOutputStream);
            fileOutputStream.close();
            return file;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }

    }

    /**
     * 删除文件 </br>
     * 注意: 计算是从tulfile这层开始算的
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public static boolean delete(String directory, String deleteFile) {
        boolean result = false;

        // 处理文件目录
        if (directory.charAt(0) == '/') {
            directory = directory.substring(1, directory.length());
        }

        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
            result = true;
        } catch (Exception e) {
            log.error(e.getMessage());
            result = false;
            throw new ServiceException(e.getMessage());
        }

        // 断开连接
        //instance.disconnect();
        return result;
    }

    public void disconnect() {
        try {
            sftp.getSession().disconnect();
        } catch (JSchException e) {
            log.error(e.getMessage());
        }
        sftp.quit();
        sftp.disconnect();
    }

    // 判断是否存在某个目录
    public static boolean isExistDir(String path) throws SftpException {
        boolean isExist = false;
        try {
            SftpATTRS lstat = sftp.lstat(path);
            return lstat.isDir();
        } catch (Exception e) {
            isExist = false;
        }
        return isExist;

    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @throws SftpException
     */
    public Vector<LsEntry> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    /**
     * BufferedImage转is流
     *
     * @param image 对应图片的 BufferedImage
     * @param type  图片类型 后缀
     * @return is流
     */
    public static InputStream bufferedImageToInputStream(BufferedImage image, String type) {
        BufferedImage bi = ensureOpaque(image);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, type, os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件名设置
     *
     * @param file -上传的文件
     * @return 文件名
     */
    private static String getFileName(MultipartFile file) {
        // 上传文件名
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyyMMdd");
        // 设置文件名
        String fileName = dateFmt.format(new Date()) + UUID.randomUUID();
        String result = fileName + "." + getFileSuffix(file);
        return result;
    }

    /**
     * 文件名设置
     *
     * @param file -上传的文件
     * @return 文件名
     */
    private static String getImageFileName(MultipartFile file) {
        // 上传文件名
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyyMMdd");
        // 设置文件名
        String fileName = dateFmt.format(new Date()) + UUID.randomUUID();
        return fileName + ".jpg";
    }

    /**
     * 获取文件后缀,并转换为小写
     *
     * @param file -文件
     * @return -文件后缀 如 jpg
     */
    private static String getFileSuffix(MultipartFile file) {
        // 获取文件名 文件格式
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        return fileType;
    }

    /**
     * 解决无法上传jpg的问题
     *
     * @param bi
     * @return
     */
    private static BufferedImage ensureOpaque(BufferedImage bi) {
        if (bi.getTransparency() == BufferedImage.OPAQUE) {
            return bi;
        }
        int w = bi.getWidth();
        int h = bi.getHeight();
        int[] pixels = new int[w * h];
        bi.getRGB(0, 0, w, h, pixels, 0, w);
        BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        bi2.setRGB(0, 0, w, h, pixels, 0, w);
        return bi2;
    }

}
