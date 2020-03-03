package com.mmall.controller.common.interceptor;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author lcy
 * @Date 2020/3/3
 * @Description
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        //请求中Controller的方法名
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //请求的方法名 以及 类名
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();

        if (StringUtils.equals(methodName, Const.AuthorityInterceptor.LOGIN_CONTROLLER_METHOD_NAME) && StringUtils.equals(className, Const.AuthorityInterceptor.LOGIN_CONTROLLER_CLASS_NAME)) {
            log.info("【拦截器拦截】 className:{} methodName:{}", className, methodName);
            //如果拦截到登陆请求,不打印参数，因为参数里面有密码，全部会打印到日志中,防止日志泄露
            return true;
        }

        //解析参数
        StringBuilder requestParamBuffer = new StringBuilder();
        Map parameterMap = request.getParameterMap();
        Iterator iterator = parameterMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = StringUtils.EMPTY;
            //request 返回的ParameterMap是<String,String[]>
            Object obj = entry.getValue();
            if (obj instanceof String[]) {
                String[] strs = (String[]) obj;
                value = Arrays.toString(strs);
            }
            requestParamBuffer.append(key).append("=").append(value);
        }

        User user = null;
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isNotBlank(loginToken)) {
            user = JsonUtil.stringToObj(RedisShardedPoolUtil.get(loginToken), User.class);
        }
        if (user == null || user.getRole().intValue() != Const.Role.ROLE_ADMIN) {
            //这里要添加Reset 否则会报异常 getWriter() has already bean called for this response
            response.reset();
            //这里要设置编码 否则会发生乱码
            response.setCharacterEncoding("UTF-8");
            //设置返回值的类型。因为全是Json接口
            response.setContentType("application/json; charset=UTF-8");

            PrintWriter out = response.getWriter();
            if (user == null) {
                if (StringUtils.equals(methodName, Const.AuthorityInterceptor.UPLOAD_CONTROLLER_METHOD_NAME) && StringUtils.equals(className, Const.AuthorityInterceptor.UPLOAD_CONTROLLER_CLASS_NAME)) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "请登录管理员");
                    out.println(JsonUtil.objToString(resultMap));
                } else {
                    out.println(JsonUtil.objToString(ServerResponse.createByErrorMessage("【拦截器拦截】 用户未登录")));
                }
            } else {
                if (StringUtils.equals(methodName, Const.AuthorityInterceptor.UPLOAD_CONTROLLER_METHOD_NAME) && StringUtils.equals(className, Const.AuthorityInterceptor.UPLOAD_CONTROLLER_CLASS_NAME)) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "无权限操作");
                    out.println(JsonUtil.objToString(resultMap));
                } else {
                    out.println(JsonUtil.objToString(ServerResponse.createByErrorMessage("【拦截器拦截】 用户无操作权限")));
                }
            }
            //清空out流中的数据 并关闭
            out.flush();
            out.close();

            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
    }
}
