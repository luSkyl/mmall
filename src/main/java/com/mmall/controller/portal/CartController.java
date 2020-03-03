package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author lcy
 * @Date 2020/2/25
 * @Description
 */
@RestController
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @PostMapping("add.do")
    public ServerResponse<CartVo> add(HttpServletRequest request, Integer count, Integer productId) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = JsonUtil.stringToObj(RedisShardedPoolUtil.get(loginToken), User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        return iCartService.add(user.getId(), productId, count);
    }

    @PostMapping("update.do")
    public ServerResponse<CartVo> update(HttpServletRequest request, Integer count, Integer productId) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = JsonUtil.stringToObj(RedisShardedPoolUtil.get(loginToken), User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(user.getId(), productId, count);
    }

    @PostMapping("delete.do")
    public ServerResponse<CartVo> delete(HttpServletRequest request, String productIds) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = JsonUtil.stringToObj(RedisShardedPoolUtil.get(loginToken), User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.delete(user.getId(), productIds);
    }

    @GetMapping("list.do")
    public ServerResponse<CartVo> delete(HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = JsonUtil.stringToObj(RedisShardedPoolUtil.get(loginToken), User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(user.getId());
    }

    @PostMapping("select_all.do")
    public ServerResponse<CartVo> selectAll(HttpServletRequest request) {
        return selectOrUnSelectAll(request, null, Const.Cart.CHECKED);
    }

    @PostMapping("un_select_all.do")
    public ServerResponse<CartVo> unSelectAll(HttpServletRequest request) {
        return selectOrUnSelectAll(request, null, Const.Cart.UN_CHECKED);
    }

    @PostMapping("select.do")
    public ServerResponse<CartVo> select(HttpServletRequest request, Integer productId) {
        return selectOrUnSelectAll(request, productId, Const.Cart.CHECKED);
    }

    @PostMapping("un_select.do")
    public ServerResponse<CartVo> unSelect(HttpServletRequest request, Integer productId) {
        return selectOrUnSelectAll(request, productId, Const.Cart.UN_CHECKED);
    }

    private ServerResponse<CartVo> selectOrUnSelectAll(HttpServletRequest request, Integer productId, Integer checkedStatus) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = JsonUtil.stringToObj(RedisShardedPoolUtil.get(loginToken), User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelectAll(user.getId(), productId, checkedStatus);
    }

    @GetMapping("get_cart_product_count.do")
    public ServerResponse<Integer> getCartProductCount(HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = JsonUtil.stringToObj(RedisShardedPoolUtil.get(loginToken), User.class);
        if (user == null) {
            //未登录用户 看到的的购物车记录为0
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(user.getId());
    }


}
