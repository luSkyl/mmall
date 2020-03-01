package com.mmall.vo;

import com.mmall.pojo.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lcy
 * @Date 2020/2/27
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductVo {
    private List<OrderItemVo> orderItemVoList;
    private BigDecimal productTotalPrice;
    private String imageHost;
}
