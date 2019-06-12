package com.yl.receive.core.remote.hessian.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yl.receive.core.valuelist.ValueListAction;
import com.yl.receive.hessian.ReceiveFacade;

import net.mlw.vlh.ValueList;

/**
 * 查询实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年1月7日
 * @version V1.0.0
 */
@Service("recfFacade")
public class ReceiveFacadeImpl implements ReceiveFacade {

	@Autowired
	private ValueListAction valueListAction;
	
	@Override
	public Map<String, Object> query(String queryId, Map<String, Object> params) {
		ValueList vl = valueListAction.execute(queryId, params).get(queryId);
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put(ReceiveFacade.VALUELIST_INFO, vl.getValueListInfo());
		map.put(ReceiveFacade.VALUELIST, vl.getList());
		return map;
	}

}
