package com.yl.pay.pos.interfaces.impl;

import java.util.HashMap;
import java.util.Map;

import com.yl.pay.pos.api.interfaces.VlhQuery;
import com.yl.pay.pos.valuelist.ValueListAction;

import net.mlw.vlh.ValueList;

public class VlhQueryImpl implements VlhQuery{
	
	private ValueListAction valueListAction;
	
	@Override
	public Map<String, Object> query(String queryId, Map<String, Object> params) {
		ValueList vl = valueListAction.execute(queryId, params).get(queryId);
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("valueListInfo", vl.getValueListInfo());
		map.put("valueList", vl.getList());
		return map;
	}

	public ValueListAction getValueListAction() {
		return valueListAction;
	}
	public void setValueListAction(ValueListAction valueListAction) {
		this.valueListAction = valueListAction;
	}
}
