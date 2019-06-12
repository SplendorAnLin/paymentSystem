package com.yl.payinterface.core.handle.impl.wallet.yyg100001.utils;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;

import java.net.URI;

public class LaxRedirectStrategyCustom extends DefaultRedirectStrategy {

	@Override
	public boolean isRedirected(HttpRequest request, HttpResponse response,
                                HttpContext context) throws ProtocolException {
		int responseCode = response.getStatusLine().getStatusCode();
		return responseCode == 301 || responseCode == 302;
	}

	@Override
	public HttpUriRequest getRedirect(HttpRequest request,
                                      HttpResponse response, HttpContext context)
			throws ProtocolException {
		URI uri = getLocationURI(request, response, context);
		String method = request.getRequestLine().getMethod();
		if (HttpPost.METHOD_NAME.equalsIgnoreCase(method)) {
			try {
				HttpRequestWrapper httpRequestWrapper = null;
				if (request instanceof HttpRequestWrapper) {
					httpRequestWrapper = (HttpRequestWrapper) request;
					httpRequestWrapper.setURI(uri);
					httpRequestWrapper.removeHeaders("Content-Length");
					return httpRequestWrapper;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("HttpRequestWrapper处理异常");
			}
			return new HttpPost(uri);
		} else {
			return new HttpGet(uri);
		}
	}
}
