package com.mmall.converter;

import com.mmall.pojo.Shipping;
import com.mmall.vo.ShippingVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author lcy
 * @Date 2020/2/27
 * @Description
 */
@Mapper
public interface ShippingConverter {
    ShippingConverter INSTANCE = Mappers.getMapper(ShippingConverter.class);

    /**
     * Shipping转化成ShippingVo
     * @param shipping
     * @return
     */

    ShippingVo shippingToShippingVo(Shipping shipping);
}
