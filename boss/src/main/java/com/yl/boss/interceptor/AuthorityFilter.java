package com.yl.boss.interceptor;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yl.boss.Constant;
import com.yl.boss.entity.Authorization;

/**
 * 权限检查拦截器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class AuthorityFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
    	
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        HttpSession session = request.getSession(true);
        Authorization auth = (Authorization) session.getAttribute(Constant.SESSION_AUTH);
        
        if (auth == null) {
            if (!uri.endsWith("/") &&
            	!uri.endsWith("/jsp/login.jsp") &&
            	!uri.endsWith("/jsp/noPermit.jsp")) {
            	
                String redirectUri = request.getScheme() + "://"+ 
                					 request.getServerName() + ":" + 
                					 request.getServerPort() + 
					                 request.getContextPath()+ 
					                 "/jsp/noPermit.jsp?type=2";

                response.sendRedirect(redirectUri);
                return;
            }
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }

}

