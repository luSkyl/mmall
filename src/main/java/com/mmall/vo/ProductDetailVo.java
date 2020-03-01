package com.mmall.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lcy
 * @Date 2020/2/25
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailVo {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImages;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private String createTime;
    private String updateTime;

    /**
     * 图片服务器的Url的前缀
     */
    private String imageHost;

    /**
     * 父分类的Id
     */
    private Integer parentCategoryId;
}
