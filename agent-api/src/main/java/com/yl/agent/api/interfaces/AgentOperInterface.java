package com.yl.agent.api.interfaces;

import com.yl.agent.api.bean.AgentOperator;

/**
 * 服务商操作员接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月22日
 * @version V1.0.0
 */
public interface AgentOperInterface {
	
	/**
	 * 创建操作员
	 * @param operator
	 */
	public void create(AgentOperator operator);
	
	/**
	 * 修改操作员
	 * @param operator
	 */
	public void update(AgentOperator operator);
	
	/**
	 * 重置服务商密码，
	 * 返回密码
	 * @param operator
	 * @return
	 */
	public String resetPassword(String agentNo);
	
	/**
	 * 根据服务商编查询
	 * @param agentNo
	 * @return
	 */
	public AgentOperator findByAgentNo(String agentNo);
	
	/**
	 * 根据登录账号查询
	 * @param userName
	 * @return
	 */
	public AgentOperator findByUserName(String userName);

}
