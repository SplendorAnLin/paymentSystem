package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.api.enums.ProductType;
import com.yl.boss.entity.AgentFee;

/**
 * 服务商费率数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface AgentFeeDao {
	
	/**
	 * 创建服务商费率
	 * @param agentFee
	 */
	public void create(AgentFee agentFee);
	
	/**
	 * 更新服务商费率
	 * @param agentFee
	 */
	public void update(AgentFee agentFee);
	
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
	 * 删除费率
	 */
	public void delect(AgentFee agentFee);
}