package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author lcy
 * @Date 2020/2/25
 * @Description
 */
public interface IFileService {
    /**
     * 上传文件
     * @param file
     * @param path
     * @return
     */
    String upload(MultipartFile file, String path);
}
