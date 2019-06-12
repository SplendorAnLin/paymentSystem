package com.yl.boss.dao;

import java.util.List;
import java.util.Map;

import com.yl.boss.entity.Agent;

/**
 * 服务商信息数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface AgentDao {
	
	/**
	 * 新建服务商信息
	 * @param agent
	 */
	void create(Agent agent);
	
	/**
	 * 根据服务商编号查询
	 * @param agentNo
	 * @return
	 */
	Agent findByNo(String agentNo);
	
	/**
	 * 根据服务商全称查询
	 * @param fullName
	 * @return
	 */
	Agent findByFullName(String fullName);
	
	/**
	 * 根据服务商手机查询
	 * @param shortName
	 * @return
	 */
	Agent findByPhone(String phone);
	
	/**
	 * 根据服务商简称查询
	 * @param shortName
	 * @return
	 */
	Agent findByShortName(String shortName);
	
	/**
	 * 更新服务商信息
	 * @param agent
	 */
	void update(Agent agent);
	
	/**
	 * 
	 * @return
	 */
	public String getMaxAgentNo();

	/**
	 * 根据服务商全称和简称，检查是否有重复
	 * @param fullName
	 * @param shortName
	 * @return
	 */
	public Map<String,String> checkAgentName(String fullName,String shortName);
	
	/**
	 * 根据服务商编号查询服务商等级
	 * @param agentNo
	 * @return
	 */
	public int queryAgentLevelByAgentNo(String agentNo);
	
	/**
	 * 根据服务商编号查询名下服务商编号
	 */
	public List<String> findAgentByParen(String paren);
	
	/**
	 * 根据服务商编号查询旗下所有商户编号
	 * @param agentNo
	 * @return
	 */
	public List<String> findAllCustomerNoByAgentNo(String agentNo);
	
	/**
     * 根据服务商编号查询当前服务商全称
     * @param agentNo
     * @return
     */
    public String queryAgentFullNameByAgentNo(String agentNo);
    
    /**
     * 根据服务商编号查询所属父级服务商编号
     * @param agentNo
     * @return
     */
    public String queryParenIdByAgentNo(String agentNo);
    
    /**
     * 根据服务商编号查询当前服务商简称
     * @param agentNo
     * @return
     */
    public String queryAgentShortNameByAgentNo(String agentNo);
}