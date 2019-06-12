package com.yl.pay.pos.action.trans;

import java.util.Map;

/**
 * Title: 交易处理工厂
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun.yu
 */

public class TransactionFactory {
	private Map<String, ITransaction> serviceMap;

	public Map<String, ITransaction> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<String, ITransaction> serviceMap) {
		this.serviceMap = serviceMap;
	}

	//根据业务处理类型获取处理服务
	public ITransaction getTransaction(String key) {
		return serviceMap.get(key);
	}
}
