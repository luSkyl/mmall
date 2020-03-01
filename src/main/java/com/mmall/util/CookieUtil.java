package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author lcy
 * @Date 2020/3/1
 * @Description
 * https://www.cnblogs.com/tianguook/p/3451609.html
 */
@Slf4j
public class CookieUtil {
    private final static String COOKIE_DOMAIN = ".lcy.com";
    private final static String COOKIE_NAME = "mmall_login_token";

    /**
     * 写入Cookie
     * @param response
     * @param Token
     */
    public static void writeLoginToken(HttpServletResponse response, String Token) {
        Cookie cookie = new Cookie(COOKIE_NAME, Token);
        cookie.setDomain(COOKIE_DOMAIN);
        //设置在根目录
        cookie.setPath("/");
        //设置最大年龄 单位是秒 如果不设置的话 MaxAge就不会被写入到硬盘，而是写在内存中，只在当前页有效
        cookie.setMaxAge(60 * 60 * 24 * 365);
        //如果在Cookie中设置了"HttpOnly"属性，那么通过JavaScript脚本将无法读取到Cookie信息，这样能有效的防止XSS攻击，让网站应用更加安全。
        cookie.setHttpOnly(true);
        log.info("【写入Cookie】 CookieName:{}  CookieValue:{}",cookie.getName(),cookie.getValue());
        response.addCookie(cookie);
    }

    /**
     * 读取Cookie
     * @param request
     * @return
     */
    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                log.info("【读取Cookie】 CookieName:{} , CookieValue:{}",cookie.getName(),cookie.getValue());
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    log.info("【读取Cookie】 返回的Cookie CookieName:{} , CookieValue:{}",cookie.getName(),cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 删除Cookie
     * @param request
     * @param response
     */
    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    log.info("【删除Cookie】 CookieName:{} , CookieValue:{}",cookie.getName(),cookie.getValue());
                    response.addCookie(cookie);
                }
            }
        }
    }

}
