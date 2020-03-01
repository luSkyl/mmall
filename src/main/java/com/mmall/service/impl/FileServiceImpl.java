package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author lcy
 * @Date 2020/2/25
 * @Description
 */
@Transactional(rollbackFor = Throwable.class)
@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf("."));
        String uploadFileName = UUID.randomUUID() + fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名:{},上传的路径:{},上传的文件名:{}", fileName, path, uploadFileName);
        //文件夹
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            //赋予可写权限
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        //文件
        File targetFile = new File(path, uploadFileName);
        try {
            //保存文件
            file.transferTo(targetFile);
           //将targetFile 放入到我们的TFP服务器上
            FtpUtil.uploadFile(Lists.newArrayList(targetFile));
            //上传完成后 删除upload上面的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.info("上传文件异常，e:{}", e);
            return null;
        }
        return targetFile.getName();
    }
}
