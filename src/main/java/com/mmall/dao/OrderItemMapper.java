package com.mmall.dao;

import com.mmall.pojo.Order;
import com.mmall.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author :lcy
 */
public interface OrderItemMapper {
    /**
     * 删除订单详情
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入订单详情
     * @param record
     * @return
     */
    int insert(OrderItem record);

    /**
     * 插入订单详情
     * @param record
     * @return
     */
    int insertSelective(OrderItem record);

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    OrderItem selectByPrimaryKey(Integer id);

    /**
     * 更新订单详情
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(OrderItem record);

    /**
     * 更新订单详情
     * @param record
     * @return
     */
    int updateByPrimaryKey(OrderItem record);

    /**
     * 根据用户Id跟订单号查询订单详情
     * @param userId 可以为空
     * @param orderNo
     * @return
     */
    List<OrderItem> selectByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);


    /**
     * 批量插入订单详情
     * @param orderItemList
     * @return
     */
    int batchInsert(@Param("orderItemList") List<OrderItem> orderItemList);


}