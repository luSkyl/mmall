package com.mmall.converter;

import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Author lcy
 * @Date 2020/2/25
 * @Description
 */
@Mapper
public interface ProductConverter {
    ProductConverter INSTANCE = Mappers.getMapper(ProductConverter.class);

    /**
     * Product转化为ProductDetailVo
     * @param product
     * @return
     */
    @Mappings({
            @Mapping(target ="createTime",expression = "java(com.mmall.util.DateTimeUtil.dateToStr(product.getCreateTime()))"),
            @Mapping(target ="updateTime",expression = "java(com.mmall.util.DateTimeUtil.dateToStr(product.getUpdateTime()))")
    })
    ProductDetailVo  productToProductDetailVo(Product product);

    /**
     * Product转化为ProductListVo
     * @param product
     * @return
     */
    ProductListVo productToProductListVo(Product product);
}
