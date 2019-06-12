package com.yl.pay.pos.action;

import java.io.IOException;
import java.io.PrintWriter;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yl.pay.pos.bean.ProxyPayResponseBean;
import com.yl.pay.pos.entity.ProxyPayInfo;
import com.yl.pay.pos.service.ProxyPayInfoService;
import com.yl.pay.pos.util.Base64Utils;

@Controller
public class ProxyPayCallbackAction extends Struts2ActionSupport {
	private static final Log log = LogFactory.getLog(ProxyPayCallbackAction.class);
	
	private ProxyPayResponseBean proxyPayResponseBean;
	
	
	public ProxyPayResponseBean getProxyPayResponseBean() {
		return proxyPayResponseBean;
	}

	public void setProxyPayResponseBean(ProxyPayResponseBean proxyPayResponseBean) {
		this.proxyPayResponseBean = proxyPayResponseBean;
	}

	public ProxyPayInfoService getProxyPayInfoService() {
		return proxyPayInfoService;
	}

	public void setProxyPayInfoService(ProxyPayInfoService proxyPayInfoService) {
		this.proxyPayInfoService = proxyPayInfoService;
	}

	@Autowired
	private ProxyPayInfoService proxyPayInfoService;

	public void callback(){
		
		PrintWriter out = null;
		try {
			proxyPayResponseBean = new ProxyPayResponseBean();
			proxyPayResponseBean.setOrdernum(getHttpRequest().getParameter("ordernum"));
			proxyPayResponseBean.setCode(getHttpRequest().getParameter("code"));
			String message = getHttpRequest().getParameter("message");
			proxyPayResponseBean.setMsg(new String(Base64Utils.decode(message),"UTF-8"));
			log.info("++++++代付推送结果信息"+proxyPayResponseBean.toString());
			if(checkRespInfo(proxyPayResponseBean)){
					out = getHttpResponse().getWriter();
					ProxyPayInfo proxyPayInfo = new ProxyPayInfo();
					proxyPayInfo.setOrderNo(proxyPayResponseBean.getOrdernum());
					proxyPayInfo.setRspMsg(proxyPayResponseBean.getMsg());
					proxyPayInfo.setRspCode(proxyPayResponseBean.getCode());
					proxyPayInfoService.complete(proxyPayInfo);
					out.print("SUCCESS");
					
				
			} 
		} catch (IOException e) {
			log.error("接收代付响应结果异常,ordernum:"+proxyPayResponseBean.getOrdernum()+",code:"+proxyPayResponseBean.getCode()+",message:"+proxyPayResponseBean.getMsg(),e);
		} finally {
			if (null != out){
				out.close();
			}
		}
		
		
		
	}
	
	public boolean checkRespInfo(ProxyPayResponseBean bean){
		if(null == bean ){
			return false;
		}
		if (null == bean.getOrdernum()){
			return false;
		}
		if (null == bean.getCode()){
			return false;
		}
		return true;
	}
	
	
}
