package com.yl.payinterface.core.handle.impl.wallet.hfb100001.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

/**
 * Http 工具
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/13
 */
public class Httpz extends HttpFacade{

    private static final Logger logger = LoggerFactory.getLogger(Httpz.class);

	private String encoding = "utf-8";
	private int readTimeout = 18000;
	private int sendTimeout = 18000;
	
	public Httpz(String encoding, int readTimeout, int sendTimeout){
		this.encoding = encoding;
		this.readTimeout = readTimeout;
		this.sendTimeout = sendTimeout;
	}
	
	public Httpz(){}
	
	@Override
	public String post(String url, Map params) throws Exception {
        logger.info("发送交易：url---->" + url + "; 内容---->" + params );
		HttpFacade hf = null;
		if(isHttps(url)){
			hf = new HttpsHandler();
		}else{
			hf =  new HttpHandler();
		}
		hf.setDefaultCharset(encoding);
		hf.setDEFAULT_CONNECT_TIMEOUT(sendTimeout);
		hf.setDEFAULT_READ_TIMEOUT(readTimeout);
		return hf.post(url, params);
	}

	@Override
	public String get(String url) throws Exception {
        logger.info("发送交易：url---->" + url );
		HttpFacade hf = null;
		if(isHttps(url)){
			hf = new HttpsHandler();
		}else{
			hf =  new HttpHandler();
		}
		hf.setDefaultCharset(encoding);
		hf.setDEFAULT_CONNECT_TIMEOUT(sendTimeout);
		hf.setDEFAULT_READ_TIMEOUT(readTimeout);
		return hf.get(url);
	}
	
	
	/**
	 * 是否为https地址
	 * @param url
	 * @return
	 */
	private boolean isHttps(String url){
		return url.startsWith("https") || url.startsWith("HTTPS");
	}
	
}