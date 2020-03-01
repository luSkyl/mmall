package com.mmall.dao;

import com.mmall.pojo.Cart;
import com.mmall.pojo.Order;
import com.mmall.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author :lcy
 */
public interface OrderMapper {
    /**
     * 删除订单
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 增加订单
     * @param record
     * @return
     */
    int insert(Order record);

    /**
     * 增加订单
     * @param record
     * @return
     */
    int insertSelective(Order record);

    /**
     * 查询订单
     * @param id
     * @return
     */
    Order selectByPrimaryKey(Integer id);

    /**
     * 更新订单
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * 更新订单
     * @param record
     * @return
     */
    int updateByPrimaryKey(Order record);

    /**
     * 根据用户Id跟订单号查询订单
     * @param userId
     * @param orderNo
     * @return
     */
    Order selectByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);

    /**
     * 根据订单号查询订单
     * @param orderNo
     * @return
     */
    Order selectByOrderNo(Long orderNo);



    /**
     * 查出所有属于该用户的订单(包括已支付和未支付的)
     * @param userId
     * @return
     */
    List<Order> selectByUserId(Integer userId);

    /**
     * 查询所有订单(所有用户)
     * @return
     */
    List<Order> selectAllOrder();

}