package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ServerResponse<CartVo> add(HttpSession httpSession, Integer count, Integer productId) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(user.getId(), productId, count);
    }

    @PostMapping("update.do")
    public ServerResponse<CartVo> update(HttpSession httpSession, Integer count, Integer productId) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(user.getId(), productId, count);
    }

    @PostMapping("delete.do")
    public ServerResponse<CartVo> delete(HttpSession httpSession, String productIds) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.delete(user.getId(), productIds);
    }

    @GetMapping("list.do")
    public ServerResponse<CartVo> delete(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(user.getId());
    }

    @PostMapping("select_all.do")
    public ServerResponse<CartVo> selectAll(HttpSession httpSession) {
        return selectOrUnSelectAll(httpSession, null, Const.Cart.CHECKED);
    }

    @PostMapping("un_select_all.do")
    public ServerResponse<CartVo> unSelectAll(HttpSession httpSession) {
        return selectOrUnSelectAll(httpSession, null, Const.Cart.UN_CHECKED);
    }

    @PostMapping("select.do")
    public ServerResponse<CartVo> select(HttpSession httpSession, Integer productId) {
        return selectOrUnSelectAll(httpSession, productId, Const.Cart.CHECKED);
    }

    @PostMapping("un_select.do")
    public ServerResponse<CartVo> unSelect(HttpSession httpSession, Integer productId) {
        return selectOrUnSelectAll(httpSession, productId, Const.Cart.UN_CHECKED);
    }

    private ServerResponse<CartVo> selectOrUnSelectAll(HttpSession httpSession, Integer productId, Integer checkedStatus) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelectAll(user.getId(), productId, checkedStatus);
    }

    @GetMapping("get_cart_product_count.do")
    public ServerResponse<Integer> getCartProductCount(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            //未登录用户 看到的的购物车记录为0
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(user.getId());
    }


}
