package com.yl.online.trade.web.filter;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {
			try {
				request.setCharacterEncoding(Charset.forName(request.getParameter("inputCharset")).displayName());
			} catch (Exception e) {
				request.setCharacterEncoding(this.encoding);
			}
			if (this.forceEncoding) {
				response.setCharacterEncoding(this.encoding);
			}
		}
		chain.doFilter(request, response);

	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setForceEncoding(boolean forceEncoding) {
		this.forceEncoding = forceEncoding;
	}

}
