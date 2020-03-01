package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * @Author lcy
 * @Date 2020/2/25
 * @Description
 */
public interface ICartService {
    /**
     * 购物车增加商品
     *
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    /**
     * 更新购物车中的商品数量
     *
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);

    /**
     * 删除购物车中的商品
     *
     * @param userId
     * @param productIds
     * @return
     */
    ServerResponse<CartVo> delete(Integer userId, String productIds);

    /**
     * 遍历购物车中的商品
     *
     * @param userId
     * @return
     */
    ServerResponse<CartVo> list(Integer userId);

    /**
     * productId为空时
     * 全选或全反选购物车中的商品
     * productId不为空时
     * 单选或取消单选购物车中的商品
     *
     * @param userId
     * @param checked
     * @param productId
     * @return
     */
    ServerResponse<CartVo> selectOrUnSelectAll(Integer userId, Integer productId, Integer checked);


    /**
     * 查询购物车中商品的数量
     * @param userId
     * @return
     */
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
