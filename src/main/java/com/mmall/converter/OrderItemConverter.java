package com.mmall.converter;

import com.mmall.pojo.OrderItem;
import com.mmall.vo.OrderItemVo;
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
public interface OrderItemConverter {
    OrderItemConverter INSTANCE = Mappers.getMapper(OrderItemConverter.class);

    /**
     * 将OrderItem转化为OrderItemVo
     * @param orderItem
     * @return
     */
    @Mappings({
            @Mapping(target = "createTime",expression = "java(com.mmall.util.DateTimeUtil.dateToStr(orderItem.getCreateTime()))")
    })
    OrderItemVo orderItemToOrderItemVo(OrderItem orderItem);
}
