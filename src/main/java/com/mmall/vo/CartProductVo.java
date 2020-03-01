package com.mmall.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lcy
 * @Date 2020/2/25
 * @Description 集合了产品跟购物车的抽象对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProductVo {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Integer productChecked;


    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    /**
     * 这件商品总价=商品数量*商品单价
     */
    private BigDecimal productTotalPrice;
    private Integer productStock;
    private Integer productStatus;

    /**
     * 防止超卖
     */
    private String limitQuantity;

}
