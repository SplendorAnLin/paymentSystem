package com.yl.boss.interceptor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class EditFilter extends StrutsPrepareAndExecuteFilter{
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String url = request.getRequestURI();
		if (url.contains("/js/edit/jsp/controller.jsp")) {              
			chain.doFilter(req, res);         
		}else{
			super.doFilter(req, res, chain);         
		} 
	}
}
