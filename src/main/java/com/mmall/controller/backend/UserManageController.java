package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Author lcy
 * @Date 2020/2/24
 * @Description
 */
@RestController
@RequestMapping("/manage/user/")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @PostMapping("login.do")
    public ServerResponse<User> login(String username, String password, HttpSession httpSession){
        ServerResponse<User> loginResponse = iUserService.login(username, password);
        if(loginResponse.isSuccess()){
            User user = loginResponse.getData();
            if(iUserService.checkAdmin(user).isSuccess()){
                //说明登录的是管理员
                httpSession.setAttribute(Const.CURRENT_USER,user);
                return loginResponse;
            }else {
                return ServerResponse.createByErrorMessage("不是管理员，无法登录");
            }
        }
        return loginResponse;
    }
}
