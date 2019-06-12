package com.yl.boss.service;

import java.util.List;

import com.yl.boss.api.enums.ProductType;
import com.yl.boss.entity.AgentFee;
import com.yl.boss.entity.AgentFeeHistory;


/**
 * 服务商费率业务访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface AgentFeeService {
	
	/**
	 * 创建服务商费率
	 * @param agentFee
	 * @param oper
	 */
	public void create(AgentFee agentFee, String oper);
	
	/**
	 * 更新服务商费率
	 * @param agentFee
	 * @param oper
	 */
	public void update(AgentFee agentFee, String oper);
	
	/**
	 * 根据服务商编、产品类型查询
	 * @param agentNo
	 * @param productType
	 * @return
	 */
	public AgentFee findBy(String agentNo,ProductType productType);
	
	/**
	 * 根据服务商编查询
	 * @param agentNo
	 * @return
	 */
	public List<AgentFee> findByAgentNo(String agentNo);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public AgentFee findById(long id);
	
	/**
	 * 根据服务商编号查询历史
	 * @param agentNo
	 * @return
	 */
	public List<AgentFeeHistory> findHistByAgentNo(String agentNo);

	/**
	 * 服务商修改  费率删除
	 */
	public void delect(AgentFee agentFee, String oper);
}