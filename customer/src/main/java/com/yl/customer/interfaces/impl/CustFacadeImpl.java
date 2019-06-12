package com.yl.customer.interfaces.impl;

import java.util.HashMap;
import java.util.Map;

import com.yl.customer.api.interfaces.CustFacade;
import com.yl.customer.valuelist.ValueListRemoteAction;

import net.mlw.vlh.ValueList;

/**
 * 商户查询远程接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月24日
 * @version V1.0.0
 */
public class CustFacadeImpl implements CustFacade {
	
	private ValueListRemoteAction valueListRemoteAction;
	
	@Override
	public Map<String, Object> query(String queryId, Map<String, Object> params) {
		ValueList vl = valueListRemoteAction.execute(queryId, params).get(queryId);
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put(CustFacade.VALUELIST_INFO, vl.getValueListInfo());
		map.put(CustFacade.VALUELIST, vl.getList());
		return map;
	}

	public void setValueListRemoteAction(ValueListRemoteAction valueListRemoteAction) {
		this.valueListRemoteAction = valueListRemoteAction;
	}
	
}
