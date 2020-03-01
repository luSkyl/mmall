package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author lcy
 * @Date 2020/2/26
 * @Description
 */
@Transactional(rollbackFor = Throwable.class)
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        int resultCount = shippingMapper.insert(shipping);
        if (resultCount > 0) {
            Map resultMap = Maps.newHashMap();
            resultMap.put("shippingId", shipping.getId());
            return ServerResponse.createBySuccess("新建地址成功", resultMap);
        }
        return ServerResponse.createBySuccess("新建地址失败");
    }

    @Override
    public ServerResponse<String> delete(Integer userId, Integer shippingId) {
        //防止横向越权
        if (shippingId == null || shippingMapper.deleteByUserIdAndShippingId(userId, shippingId) == 0) {
            return ServerResponse.createByErrorMessage("删除地址失败");
        }
        return ServerResponse.createBySuccess("删除地址成功");
    }

    @Override
    public ServerResponse<String> update(Integer userId, Shipping shipping) {
        //防止横向越权
        shipping.setUserId(userId);
        int resultCount = shippingMapper.updateByShipping(shipping);
        if (resultCount > 0) {
            return ServerResponse.createBySuccess("更新地址成功");
        }
        return ServerResponse.createBySuccess("更新地址失败");
    }

    @Override
    public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {
        //防止横向越权
        Shipping shipping;
        if (shippingId == null || (shipping = shippingMapper.selectByUserIdAndShippingId(userId, shippingId)) == null) {
            return ServerResponse.createByErrorMessage("查询地址失败");
        }
        return ServerResponse.createBySuccess("查询地址成功", shipping);
    }

    @Override
    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        if (CollectionUtils.isEmpty(shippingList)) {
            return ServerResponse.createByErrorMessage("查询地址失败");
        }
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);

    }


}
