package com.pay.sign.business;

import java.util.Map;


/**
 * Title:终端业务处理工厂 
 * Description:   
 * Copyright: 2015年6月12日下午2:49:42
 * Company: SDJ
 * @author zhongxiang.ren
 */
public class TerminalBusinessFactory {
	private Map<String, ITerminalBusiness> serviceMap;

	public Map<String, ITerminalBusiness> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<String, ITerminalBusiness> serviceMap) {
		this.serviceMap = serviceMap;
	}

	//根据业务处理类型获取处理服务
	public ITerminalBusiness getBusiness(String key) {
		return serviceMap.get(key);
	}
}
