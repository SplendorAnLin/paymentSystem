package com.yl.agent.service;

import java.util.List;

import com.yl.agent.entity.Authorization;
import com.yl.agent.entity.Operator;

/**
 * 操作员管理
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public interface OperatorService {
	

	/**
	 * 登陆密码重置。
	 * @param operatorId 操作员id
	 * @param newPwd 新密码
	 */
   
	public Operator updatePassword(String username, String password, Authorization auth) throws ServiceException;
	
	/**
	 * 查找指定username的操作员
	 * @param operatorId
	 * @return
	 */
	public Operator findByUsername(String username);
	
	public List<Operator> findAll();

	public Operator update(Operator operator);
	
	public void add(Operator operator);
	
	public Operator findByAgentNo(String agentNo);
	
	/**
	 * 重置代理商密码
	 * 返回密码
	 * @param agentNo
	 * @return
	 */
	public String resetPassword(String agentNo);
}
