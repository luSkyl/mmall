package com.mmall.converter;

import com.mmall.pojo.Cart;
import com.mmall.vo.CartProductVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author lcy
 * @Date 2020/2/25
 * @Description
 */
@Mapper
public interface CartConverter {
    CartConverter INSTANCE = Mappers.getMapper(CartConverter.class);

    CartProductVo cartToCartProductVo(Cart cart);
}
