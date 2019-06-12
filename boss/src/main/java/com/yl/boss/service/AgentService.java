package com.yl.boss.service;

import java.util.List;
import java.util.Map;

import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;
import com.yl.boss.entity.Agent;
import com.yl.boss.entity.AgentCert;
import com.yl.boss.entity.AgentFee;
import com.yl.boss.entity.AgentHistory;
import com.yl.boss.entity.AgentSettle;
import com.yl.dpay.hessian.beans.ServiceConfigBean;

/**
 * 服务商信息业务访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface AgentService {
	
	/**
	 * 分页查询
	 * @return
	 */
	String findAll(Map<String, Object> param);
	/**
	 * 新增功能
	 * @return
	 */
	String addFunction();
	/**
	 * 编辑功能
	 * @return
	 */
	String modify();
	/**
	 * 删除功能
	 * @return
	 */
	String delete();
	/**
	 * 新增
	 * @return
	 */
	String agentAddChildMenu();
	/**
	 * 菜单修改
	 * @return
	 */
	String agentModifyMenu();
	/**
	 * 菜单编辑
	 * @return
	 */
	String toagentMenuEdit();
	
	/**
	 * 服务商菜单查询
	 * @return
	 */
	String agentMenuQuery();
	
	/**
	 * 新建服务商信息
	 * @param agent
	 * @param agentCert
	 * @param agentSettle
	 * @param agentFee
	 * @param serviceConfigBean
	 * @param oper
	 * @param cycle
	 */
	void create(ReceiveConfigInfoBean receiveConfigInfoBean,Agent agent, AgentCert agentCert, AgentSettle agentSettle, List<AgentFee> agentFees,ServiceConfigBean serviceConfigBean, String oper, int cycle);
	
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
	 * 根据服务商简称查询
	 * @param shortName
	 * @return
	 */
	Agent findByShortName(String shortName);
	
	/**
	 * 根据服务商手机查询
	 * @param shortName
	 * @return
	 */
	Agent findByPhone(String phone);
	
	/**
	 * 更新服务商信息
	 * @param agent
	 * @param oper
	 */
	void update(Agent agent, String oper);
	
	/**
	 * 根据服务商编号查询历史
	 * @param agentNo
	 * @return
	 */
	List<AgentHistory> findHistByAgentNo(String agentNo);
	
	/**
	 * 根据商户编号更新服务商密码
	 * @param operator
	 */
	public void resetPassword(String agentNo);
	
	/**
	 * 新增服务商(外部入网)
	 * @param receiveConfigInfoBean
	 * @param agent
	 * @param agentCert
	 * @param agentSettle
	 * @param agentFees
	 * @param serviceConfigBean
	 * @param oper
	 * @param cycle
	 */
	public void createAgent(ReceiveConfigInfoBean receiveConfigInfoBean,Agent agent, AgentCert agentCert, AgentSettle agentSettle, List<AgentFee> agentFees, ServiceConfigBean serviceConfigBean, String oper, int cycle);
	
	/**
	 * 服务商审核
	 * @param agent 服务商信息bean
	 * @param feeList 服务商费率信息List
	 * @param oper 操作人
	 */
	public void auditAgent(Agent agent,List<AgentFee> feeList,String oper);

	/**
	 * 修改服务商信息(外部修改)
	 * @param agent
	 * @param feeList
	 * @param receiveConfigInfoBean
	 * @param agentCert
	 * @param agentSettle
	 * @param serviceConfigBean
	 * @param oper
	 * @param cycle
	 */
	public void updateAgent(Agent agent,List<AgentFee> feeList,ReceiveConfigInfoBean receiveConfigInfoBean,AgentCert agentCert,AgentSettle agentSettle,ServiceConfigBean serviceConfigBean, String oper, int cycle);
	
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