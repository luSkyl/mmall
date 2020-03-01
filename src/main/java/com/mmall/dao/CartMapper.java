package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    /**
     * 删除购物车中的商品
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 增加购物车中的商品
     *
     * @param record
     * @return
     */
    int insert(Cart record);

    /**
     * 增加购物车中的商品
     *
     * @param record
     * @return
     */
    int insertSelective(Cart record);

    /**
     * 根据Id查找购物车中的商品
     *
     * @param id
     * @return
     */
    Cart selectByPrimaryKey(Integer id);

    /**
     * 更新购物车中的商品
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Cart record);

    /**
     * 更新购物车中的商品
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Cart record);

    /**
     * 根据用户ID跟商品ID查询购物车
     *
     * @param userId
     * @param productId
     * @return
     */
    Cart selectCartByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    /**
     * 查找某用户的购物车
     *
     * @param userId
     * @return
     */
    List<Cart> selectCartByUserId(Integer userId);

    /**
     * 根据用户Id查询购物车处于未选中状态的个数
     *
     * @param userId
     * @return
     */
    int selectCartProductCheckedSatusByUserId(Integer userId);

    /**
     * 根据用户Id跟产品Id删除购物车中的商品
     *
     * @param userId
     * @param productIdList
     */
    void deleteByUserIdAndProductIds(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);

    /**
     * 全选或全反选购物车中的商品(无商品ID时)
     * 单选或取消单选购物车中的商品(有商品时)
     *
     * @param userId
     * @param checked
     * @param productId
     * @return
     */
    int checkedOrUnCheckedAllProduct(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("checked") Integer checked);

    /**
     * 获取购物车中商品的数量
     * @param userId
     * @return
     */
    int selectCartProductCount(@Param("userId") Integer userId);

    /**
     * 查找购物车里被选中的商品
     * @param userId
     * @return
     */
    List<Cart> selectCheckedCartByUserId(@Param("userId")Integer userId );
}