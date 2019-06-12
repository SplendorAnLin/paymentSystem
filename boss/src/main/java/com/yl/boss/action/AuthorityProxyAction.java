package com.yl.boss.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.common.util.*;
import com.yl.boss.Constant;
import com.yl.boss.entity.Authorization;

/**
 * 权限代理Action
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class AuthorityProxyAction extends Struts2ActionSupport {
	
	protected static Log logger = LogFactory.getLog(AuthorityProxyAction.class);
	private String url;
	
	public String execute(){				
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);	
		PropertyUtil propertyUtil =	PropertyUtil.getInstance("system");
		
		//权限检查
		if(auth==null){
			return "sessionExpired";
		}
		
		//获得本机IP
		String operator = auth.getUsername();
		String requestIp = "";		
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			requestIp = inetAddress.getHostAddress().toString();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		//生成安全签名
		DigestUtil des = new DigestUtil();
        des.getKey(propertyUtil.getProperty("webserver.mac.mk")); 
        
		StringBuffer macSb = new StringBuffer();
		macSb.append(requestIp + ",");
		macSb.append(operator + ",");
		macSb.append(System.currentTimeMillis()+",");
		String mac = macSb.toString();
		//mac = des.getEncString(macSb.toString());
		logger.info("request mac:" + mac );
		
		//生成请求URL
		String appName = url.substring(1, url.indexOf("/", 1)); 
		String appHead = propertyUtil.getProperty("webserver.url." + appName);	
		
		try {
			mac=URLEncoder.encode(mac, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		//追加MAC
		String targetUrl = appHead + url;
		if(url.indexOf("?")>-1){
			targetUrl = targetUrl + "&mac=" + mac;
		}else{
			targetUrl = targetUrl + "?mac=" + mac;
		}					
		logger.info("authority proxy request :" + targetUrl );
		
		//请求重定向
		try {
			getHttpResponse().sendRedirect(targetUrl);
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		return SUCCESS;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
