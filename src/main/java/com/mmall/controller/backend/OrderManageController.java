package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import com.mmall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lcy
 * @Date 2020/2/27
 * @Description
 */
@RestController
@RequestMapping("/manage/order/")
public class OrderManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;

    @GetMapping("list.do")
    public ServerResponse<PageInfo> orderList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        //填充我们增加产品的业务逻辑
        return iOrderService.manageList(pageNum,pageSize);
    }

    @GetMapping("detail.do")
    public ServerResponse<OrderVo> orderDetail(Long orderNo){
        //填充我们增加产品的业务逻辑
        return iOrderService.manageDetail(orderNo);
    }

    @GetMapping("search.do")
    public ServerResponse<PageInfo> orderSearch(Long orderNo,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        //填充我们增加产品的业务逻辑
        return iOrderService.manageSearch(orderNo,pageNum,pageSize);
    }

    @PostMapping("send_goods.do")
    public ServerResponse<String> orderSendGoods(Long orderNo){

        //填充我们增加产品的业务逻辑
        return iOrderService.manageSendGoods(orderNo);
    }


}
