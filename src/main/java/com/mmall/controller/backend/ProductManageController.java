package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author lcy
 * @Date 2020/2/25
 * @Description
 */
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

    @PostMapping("save.do")
    public ServerResponse productSave(HttpServletRequest request, Product product) {
        return iProductService.saveOrUpdateProduct(product);
    }

    @PostMapping("set_sale_status.do")
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        return iProductService.setSaleStatus(productId, status);
    }

    @GetMapping("detail.do")
    public ServerResponse getDetail(Integer productId) {
        return iProductService.manageProductDetail(productId);
    }


    @GetMapping("list.do")
    public ServerResponse getList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iProductService.getProductList(pageNum, pageSize);
    }

    @GetMapping("search.do")
    public ServerResponse productSearch(String productName, Integer productId, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iProductService.searchProduct(productName, productId, pageNum, pageSize);
    }

    @PostMapping("upload.do")
    public ServerResponse upload(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFile = iFileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile;
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFile);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);
    }

    @PostMapping("richtext_img_upload.do")
    public Map richtextImgUpload(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFile = iFileService.upload(file, path);
        if(StringUtils.isBlank(targetFile)){
            resultMap.put("success",false);
            resultMap.put("msg","上传文件失败");
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile;
        resultMap.put("success",true);
        resultMap.put("msg","上传文件成功");
        resultMap.put("file_path","上传成功");
        response.addHeader("Access-Control-Allow-Headers","X-File-Name");
        return resultMap;
    }


}
