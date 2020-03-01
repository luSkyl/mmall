package com.mmall.dao;

import com.mmall.pojo.PayInfo;

/**
 * @author :lcy
 */
public interface PayInfoMapper {
    /**
     * 删除支付信息
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入支付信息
     * @param record
     * @return
     */
    int insert(PayInfo record);

    /**
     * 插入支付信息
     * @param record
     * @return
     */
    int insertSelective(PayInfo record);

    /**
     * 查询支付信息
     * @param id
     * @return
     */
    PayInfo selectByPrimaryKey(Integer id);

    /**
     * 更新支付信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PayInfo record);

    /**
     * 更新支付信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(PayInfo record);
}