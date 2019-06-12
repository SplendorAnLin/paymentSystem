package com.yl.online.gateway.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.online.gateway.Constants;

/**
 * 字符编码过滤器
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月21日
 * @version V1.0.0
 */
public class CharacterEncodingFilter implements Filter {
	private String encoding;

	private boolean forceEncoding = false;

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//只设置response编码，request编码根据inputCharset有RequestProxy.java设置
		if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {
			if(StringUtils.isNotBlank(request.getParameter(Constants.PARAM_NAME_INPUT_CHARSET))){
				response.setCharacterEncoding(request.getParameter(Constants.PARAM_NAME_INPUT_CHARSET));
			}else if (this.forceEncoding) {
				response.setCharacterEncoding(encoding);
			}
		}	
		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setForceEncoding(boolean forceEncoding) {
		this.forceEncoding = forceEncoding;
	}

}
