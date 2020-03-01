package com.mmall.converter;

import com.mmall.pojo.Order;
import com.mmall.vo.OrderVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Author lcy
 * @Date 2020/2/27
 * @Description
 */
@Mapper
public interface OrderConverter {
    OrderConverter INSTANCE = Mappers.getMapper(OrderConverter.class);

    /**
     * 将Order转化为OrderVo
     * @param order
     * @return
     */
    @Mappings({
            @Mapping(target = "paymentTime",expression = "java(com.mmall.util.DateTimeUtil.dateToStr(order.getPaymentTime()))"),
            @Mapping(target = "sendTime",expression = "java(com.mmall.util.DateTimeUtil.dateToStr(order.getSendTime()))"),
            @Mapping(target = "endTime",expression = "java(com.mmall.util.DateTimeUtil.dateToStr(order.getEndTime()))"),
            @Mapping(target = "closeTime",expression = "java(com.mmall.util.DateTimeUtil.dateToStr(order.getCloseTime()))"),
            @Mapping(target = "createTime",expression = "java(com.mmall.util.DateTimeUtil.dateToStr(order.getCreateTime()))")
    })
    OrderVo orderToOrderVo(Order order);
}
