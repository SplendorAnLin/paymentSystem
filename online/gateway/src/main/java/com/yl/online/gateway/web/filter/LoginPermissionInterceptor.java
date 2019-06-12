package com.yl.online.gateway.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录权限拦截
 *
 * @author AnLin
 * @version v1.0.0
 * @since 2018/1/16
 */
public class LoginPermissionInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginPermissionInterceptor.class);

    private String redirectPath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        } else {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String className = handlerMethod.getMethod().getDeclaringClass().getName();
            String methodName = handlerMethod.getMethod().getName();
            return false;
        }
    }

    public LoginPermissionInterceptor(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    public LoginPermissionInterceptor() {
    }
}