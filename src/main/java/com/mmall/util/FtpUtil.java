package com.mmall.util;

import lombok.Data;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Author lcy
 * @Date 2020/2/25
 * @Description 实现文件上传
 */
@Data
public class FtpUtil {
    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);
    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    private static int ftpPort = Integer.parseInt(PropertiesUtil.getProperty("ftp.server.port"));
    private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");
    private static final String REMOTEPATH = "img";

    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public FtpUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    public static boolean uploadFile(List<File> fileList) throws IOException {
        FtpUtil ftpUtil = new FtpUtil(ftpIp, ftpPort, ftpUser, ftpPass);
        logger.info("开始连接FTP服务器");
        boolean result = ftpUtil.uploadFile(REMOTEPATH, fileList);
        logger.info("FTP服务器连接结束，连接结果:{}",result);
        return result;
    }

    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream inputStream = null;
        //连接FTP服务器
        if (connectServer(this.ip, this.port, this.user, this.pwd)) {
            try {
                //更改FTP会话的当前工作目录
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                //设置成二进制模式
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                //打开被动模式
                ftpClient.enterLocalPassiveMode();
                for (File file : fileList) {
                    inputStream = new FileInputStream(file);
                    //根据inputStream 存储文件 这个方法不会关闭给定的InputStream
                    ftpClient.storeFile(file.getName(), inputStream);
                }
            } catch (IOException e) {
                logger.info("上传文件异常，e:{}", e);
                uploaded = false;
            } finally {
                inputStream.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }

    private boolean connectServer(String ip, int port, String user, String pwd) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip, port);
            isSuccess = ftpClient.login(user, pwd);
        } catch (IOException e) {
            logger.info("连接FTP服务器异常,e:{}", e);
        }
        return isSuccess;
    }
}
