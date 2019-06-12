package com.yl.boss.api.interfaces;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.boss.api.bean.Agent;
import com.yl.boss.api.bean.AgentCert;
import com.yl.boss.api.bean.AgentDeviceOrderBean;
import com.yl.boss.api.bean.AgentFee;
import com.yl.boss.api.bean.AgentSettle;
import com.yl.boss.api.bean.AppVersionBean;
import com.yl.boss.api.bean.DeviceTypeBean;
import com.yl.boss.api.enums.ProductType;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;

/**
 * 服务商远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public interface AgentInterface {
	
	/**
	 * 根据服务商编获取服务商费率
	 * @param agentNo
	 * @param productType
	 * @return
	 */
	public AgentFee getAgentFee(String agentNo, ProductType productType);
	
	/**
	 * 
	 * @Description 根据服务商编号获取服务商费率信息
	 * @param agentNo
	 * @return
	 * @date 2016年10月29日
	 */
	public List<AgentFee> getAgentFee(String agentNo);
	
	/**
	 * 根据服务商编获取服务商结算卡信息
	 * @param agentNo
	 * @return
	 */
	public AgentSettle getAgentSettle(String agentNo);
	public List<AgentSettle> getListAgentSettle(String agentNo);
	
	/**
	 * 根据服务商编获取服务商信息
	 * @param agentNo
	 * @return
	 */
	public Agent getAgent(String agentNo);
	
	/**
	 * 通过订单号查询原交易记录
	 */
	AgentDeviceOrderBean toPayAgain(String outOrderId);
	
	/**
	 * 修改服务商设备订单
	 */
	public void updateAgentDeviceOrder(AgentDeviceOrderBean agentDeviceOrderBean,String oper);
	
	/**
	 * 添加服务商设备订单
	 * @param agentDeviceOrderBean
	 * @param oper
	 */
	public void addAgentDeviceOrder(AgentDeviceOrderBean agentDeviceOrderBean,String oper);
	
	/**
	 * 判断是否申请过水牌
	 * @param agentDeviceOrderBean
	 * @return
	 */
	public int yOrNoOrder(AgentDeviceOrderBean agentDeviceOrderBean);
	
	/**
	 * 根据具服务商获取设备订单列表
	 * @param params
	 * @return
	 */
	public Page queryAgentDeviceOrderByAgentNo(Map<String, Object> params);
	/**
	 * 根据具服务商获取设备列表
	 * @param params
	 * @return
	 */
	public Page queryAgentDeviceByAgentNo(Map<String, Object> params);
	/**
	 * 获取设备类型列表
	 * @return
	 */
	public List<DeviceTypeBean> getDeviceList();
	/**
	 * 新增服务商
	 * @param receiveConfigInfoBean
	 * @param agent
	 * @param agentCert
	 * @param agentSettle
	 * @param agentFees
	 * @param serviceConfigBean
	 * @param oper
	 * @param cycle
	 */
	public void createAgent(ReceiveConfigInfoBean receiveConfigInfoBean,Agent agent, AgentCert agentCert, AgentSettle agentSettle, List<AgentFee> agentFees, String serviceConfigBean, String oper, int cycle);
	
	/**
	 * 服务商查询
	 * @param queryId
	 * @param params
	 * @return
	 */
	Page queryAgent(String queryId, Map<String, Object> params);
	
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
	public void updateAgent(Agent agent,List<AgentFee> feeList,ReceiveConfigInfoBean receiveConfigInfoBean,AgentCert agentCert,AgentSettle agentSettle,String serviceConfigBean, String oper, int cycle);
	
	/**
	 * 根据服务商编号查询
	 * @param agentNo
	 * @return
	 */
	public AgentCert findByAgentNo(String agentNo);
	
	/**
	 * 根据服务商全称和简称，检查是否有重复
	 * @param fullName
	 * @param shortName
	 * @return
	 */
	public Map<String,String> checkAgentName(String fullName,String shortName);
	

	/**
	 * 获取配置商户号
	 * @return
	 */
	String getConfigKey();

	/**
	 * 根据服务商编号查询服务商等级
	 * @param agentNo
	 * @return
	 */
	public int queryAgentLevelByAgentNo(String agentNo);
	
	/**
	 * APP获取通道费率
	 */
	public List<Map<String, Object>> appGetAgentFeeIn(String agentNo);
	
	/**
	 * APP获取通道费率
	 */
	public List<Map<String, Object>> appGetAgentFeeOut(String agentNo);
	
	/**
	 * 根据服务商手机查询
	 * @param shortName
	 * @return
	 */
	Agent findByPhone(String phone);
	/**
	 * 根据oem和类型查找当前版本号信息
	 * @param type
	 * @param oem
	 * @return
	 */
	public AppVersionBean getAppVersion(String type,String oem);
	
	/**
	 * 根据服务商编号查询旗下所有商户编号
	 * @param agentNo
	 * @return
	 */
	public List<String> findAllCustomerNoByAgentNo(String agentNo);
	
}
