package com.yl.agent.interfaces.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yl.agent.api.interfaces.AgentFacade;
import com.yl.agent.valuelist.ValueListRemoteAction;

import net.mlw.vlh.ValueList;
/**
 * 代理商接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月11日
 * @version V1.0.0
 */
@Service("agentFacade")
public class AgentFacadeImpl implements AgentFacade {

	@Autowired
	private ValueListRemoteAction valueListRemoteAction;
	@Override
	public Map<String, Object> query(String queryId, Map<String, Object> params) {
		ValueList vl = valueListRemoteAction.execute(queryId, params).get(queryId);
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put(AgentFacade.VALUELIST_INFO, vl.getValueListInfo());
		map.put(AgentFacade.VALUELIST, vl.getList());
		return map;
	}
	public ValueListRemoteAction getValueListRemoteAction() {
		return valueListRemoteAction;
	}
	public void setValueListRemoteAction(ValueListRemoteAction valueListRemoteAction) {
		this.valueListRemoteAction = valueListRemoteAction;
	}
	
	
}
