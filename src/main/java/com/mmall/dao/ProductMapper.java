package com.mmall.dao;

import com.mmall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    /**
     * 删除商品
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入商品
     *
     * @param record
     * @return
     */
    int insert(Product record);

    /**
     * 插入商品
     *
     * @param record
     * @return
     */
    int insertSelective(Product record);

    /**
     * 根据Id查询商品
     *
     * @param id
     * @return
     */
    Product selectByPrimaryKey(Integer id);

    /**
     * 更新商品
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Product record);

    /**
     * 更新商品
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Product record);

    /**
     * 查询所有商品
     *
     * @return
     */
    List<Product> selectList();

    /**
     *根据商品名字跟Id进行商品查询
     * @param productName
     * @param productId
     * @return
     */
    List<Product> selectByNameAndProductId(@Param("productName") String productName, @Param("productId") Integer productId);


    /**
     * 根据Name跟品类名查找商品
     * @param categoryIdList 种类名列表
     * @param productName
     * @return
     */
   List<Product> selectByNameAndCategoryIds(@Param("productName")String productName,@Param("categoryIdList")List<Integer> categoryIdList);
}