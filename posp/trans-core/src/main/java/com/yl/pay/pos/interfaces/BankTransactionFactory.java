package com.yl.pay.pos.interfaces;

import java.util.Map;

/**
 * Title: 银行交易处理工厂
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author jun.yu
 */

public class BankTransactionFactory {
	private Map<String, IBankTransaction> serviceMap;

	public Map<String, IBankTransaction> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<String, IBankTransaction> serviceMap) {
		this.serviceMap = serviceMap;
	}

	//根据业务处理类型获取处理服务
	public IBankTransaction getBankTransaction(String key) {
		return serviceMap.get(key);
	}
}
