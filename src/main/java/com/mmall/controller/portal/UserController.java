package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author lcy
 * @Date 2020/2/24
 * @Description
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登入
     *
     * @param username
     * @param password
     * @param httpSession
     * @return
     */
    @PostMapping("login.do")
    public ServerResponse<User> login(String username, String password, HttpSession httpSession, HttpServletResponse response) {
        ServerResponse<User> serverResponse = iUserService.login(username, password);
        if (serverResponse.isSuccess()) {
            CookieUtil.writeLoginToken(response,httpSession.getId());
            RedisPoolUtil.setEx(httpSession.getId(), JsonUtil.objToString(serverResponse.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return serverResponse;
    }

    @PostMapping("logout.do")
    public ServerResponse<User> logout(HttpServletRequest request,HttpServletResponse response) {
        String loginToken = CookieUtil.readLoginToken(request);
        if(StringUtils.isBlank(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登入，无法注销!");
        }
        CookieUtil.delLoginToken(request,response);
        RedisPoolUtil.del(loginToken);
        return ServerResponse.createBySuccess();
    }

    @PostMapping("register.do")
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 校验
     *
     * @param str  值
     * @param type 类型
     * @return
     */
    @PostMapping("check_valid.do")
    public ServerResponse<String> checkVaild(String str, String type) {
        return iUserService.checkVaild(str, type);
    }

    @GetMapping("get_user_info.do")
    public ServerResponse<User> getUserInfo(HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = JsonUtil.stringToObj(RedisPoolUtil.get(loginToken), User.class);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        return ServerResponse.createBySuccess(user);
    }

    @PostMapping("forget_get_question.do")
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    @PostMapping("forget_check_answer.do")
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    @PostMapping("forget_reset_password.do")
    public ServerResponse<String> forgetResetPassword(String username, String password, String forgetToken) {
        return iUserService.forgetResetPassword(username, password, forgetToken);
    }

    @PostMapping("reset_password.do")
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, HttpServletRequest request) {
        String loginToken = CookieUtil.readLoginToken(request);
        User user = JsonUtil.stringToObj(RedisPoolUtil.get(loginToken), User.class);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, user);
    }

    @PostMapping("update_information.do")
    public ServerResponse<User> update_information(HttpServletRequest request, User user) {
        String loginToken = CookieUtil.readLoginToken(request);
        User currentUser = JsonUtil.stringToObj(RedisPoolUtil.get(loginToken), User.class);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> userServerResponse = iUserService.updateInformation(user);
        if(userServerResponse.isSuccess()){
            RedisPoolUtil.setEx(loginToken, JsonUtil.objToString(userServerResponse.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return  userServerResponse;
    }

    @GetMapping("get_information.do")
    public ServerResponse<User> get_information(HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        User user = JsonUtil.stringToObj(RedisPoolUtil.get(loginToken), User.class);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,需要强制登录");
        }
        return iUserService.getInformation(user.getId());
    }

}
