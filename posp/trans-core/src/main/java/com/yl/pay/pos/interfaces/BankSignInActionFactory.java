package com.yl.pay.pos.interfaces;

import java.util.Map;


/**
 * Title: 银行终端签到处理工厂
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author jun
 */

public class BankSignInActionFactory {
	private Map<String, IBankSignInAction> serviceMap;

	public Map<String, IBankSignInAction> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<String, IBankSignInAction> serviceMap) {
		this.serviceMap = serviceMap;
	}

	//根据业务处理类型获取处理服务
	public IBankSignInAction getBankSignInAction(String key) {
		return serviceMap.get(key);
	}
	
}
