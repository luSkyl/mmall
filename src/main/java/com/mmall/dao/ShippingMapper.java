package com.mmall.dao;

import com.mmall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    /**
     * 删除地址
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增地址(id主键自增)
     *
     * @param record
     * @return
     */
    int insert(Shipping record);

    /**
     * 新增地址
     *
     * @param record
     * @return
     */
    int insertSelective(Shipping record);

    /**
     * 根据Id查询地址
     *
     * @param id
     * @return
     */
    Shipping selectByPrimaryKey(Integer id);

    /**
     * 更新地址
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Shipping record);

    /**
     * 更新地址
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Shipping record);

    /**
     * 根据用户Id跟地址Id删除地址
     *
     * @param userId
     * @param shippingId
     * @return
     */
    int deleteByUserIdAndShippingId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    /**
     * 更新地址
     *
     * @param shipping
     * @return
     */
    int updateByShipping(Shipping shipping);

    /**
     * 根据用户Id跟地址Id查询地址
     *
     * @param userId
     * @param shippingId
     * @return
     */
    Shipping selectByUserIdAndShippingId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    /**
     * 根据用户ID查询地址
     * @param userId
     * @return
     */
    List<Shipping> selectByUserId(@Param("userId") Integer userId);
}