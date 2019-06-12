package com.yl.agent.interfaces.impl;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.agent.api.bean.AgentOperator;
import com.yl.agent.api.interfaces.AgentOperInterface;
import com.yl.agent.entity.Operator;
import com.yl.agent.service.OperatorService;

/**
 * 代理商操作员接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月11日
 * @version V1.0.0
 */
public class AgentOperInterfaceImpl implements AgentOperInterface {
	
	private OperatorService operatorService;

	@Override	
	public void create(AgentOperator operator) {
		operatorService.add((Operator)JsonUtils.toJsonToObject(operator, Operator.class));
	}

	@Override
	public void update(AgentOperator operator) {
		operatorService.update((Operator)JsonUtils.toJsonToObject(operator, Operator.class));
	}
	
	@Override
	public String resetPassword(String agentNo) {
		return operatorService.resetPassword(agentNo);
		
	}

	@Override
	public AgentOperator findByAgentNo(String agentNo) {
		Operator operator = operatorService.findByAgentNo(agentNo);
		if(operator != null){
			AgentOperator agentOperator=new AgentOperator();
			agentOperator.setAgentNo(operator.getAgentNo());
			agentOperator.setRealname(operator.getRealname());
			agentOperator.setUsername(operator.getUsername());
			return agentOperator;
		}
		return null;
	}

	@Override
	public AgentOperator findByUserName(String userName) {
		Operator operator = operatorService.findByUsername(userName);
		if(operator != null){
			AgentOperator agentOperator=new AgentOperator();
			agentOperator.setAgentNo(operator.getAgentNo());
			agentOperator.setRealname(operator.getRealname());
			agentOperator.setUsername(operator.getUsername());
			return agentOperator;
		}
		return null;
	}

	public OperatorService getOperatorService() {
		return operatorService;
	}

	public void setOperatorService(OperatorService operatorService) {
		this.operatorService = operatorService;
	}

}
