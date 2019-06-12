package com.yl.receive.core.remote.hessian.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yl.receive.core.valuelist.ValueListAction;
import com.yl.receive.hessian.NameListQuery;
import com.yl.receive.hessian.ReceiveFacade;

import net.mlw.vlh.ValueList;

/**
 * 白名单查询接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年1月7日
 * @version V1.0.0
 */
@Service("nameListQuery")
public class NameListQueryImpl implements NameListQuery {
	
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