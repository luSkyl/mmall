package com.mmall.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author lcy
 * @Date 2020/2/27
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo {

    private Long orderNo;
    private BigDecimal payment;
    private Integer paymentType;
    private String paymentTypeDesc;
    private Integer postage;
    private Integer status;
    private String statusDesc;

    private String paymentTime;
    private String sendTime;
    private String endTime;
    private String closeTime;
    private String createTime;

    /**
     * 订单明细
     */
    private List<OrderItemVo> orderItemVoList;
    /**
     * 图片地址
     */
    private String imageHost;

    /**
     * 收货地址
     */
    private Integer shippingId;
    /**
     * 收货人名字
     */
    private String receiveName;

    /**
     * 收货地址信息
     */
    private ShippingVo shippingVo;



}
