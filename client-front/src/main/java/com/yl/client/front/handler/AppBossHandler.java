package com.yl.client.front.handler;

import java.util.Map;

/**
 * App运营处理
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public interface AppBossHandler extends AppHandler{

	/**
	 * 根据商编获取商户信息
	 * @param customerNo
	 * @return
	 */
	public Map<String,Object> getCustomer(Map<String,Object> param) throws Exception;
	
	/**
	 * 根据商编获取商户结算卡信息
	 * @param customerNo
	 * @return
	 */
	public Map<String,Object> getCustomerSettle(Map<String,Object> param) throws Exception;
	
	/**
	 * 根据商编获取商户费率
	 * @param param
	 * @return
	 */
	public Map<String,Object> getCustomerFeeList(Map<String,Object> param) throws Exception;
	
	/**
	 * 查询收款码
	 * @param param
	 * @return
	 */
	public Map<String,Object> getQRcode(Map<String,Object> param) throws Exception;
	
	/**
	 * 广告查询
	 * @return
	 */
	public Map<String,Object> getAdAll(Map<String,Object> param) throws Exception;
	
	/**
	 * 查询客户端信息
	 * @param param
	 * @return
	 */
	public Map<String,Object> getProtolAll(Map<String,Object> param) throws Exception;

	/**
	 * 获取商户费率
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getCustomerFee(Map<String,Object> param) throws Exception;
	
	/**
	 * 意见反馈新增
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> userFeedbackAdd(Map<String,Object> param) throws Exception;
	
	/**
	 * 服务商费率
	 */
	public Map<String,Object> getAgentFee(Map<String,Object> param) throws Exception;
	
	/**
	 * 首页 - 周销售趋势
	 * 服务商  商户统一查询入口
	 */
	public Map<String, Object> weeklySales(Map<String, Object> param) throws Exception;
	/**
	 * APP升级检测
	 * @param param
	 * @return
	 */
	public Map<String, Object> upApp(Map<String, Object> param) throws Exception;
	
	/**
	 * 绑定交易卡片
	 */
	public Map<String, Object> addTansCard(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询交易卡片
	 */
	public Map<String, Object> findByCustNo(Map<String, Object> param) throws Exception;
	
	/**
	 * 解绑交易卡片
	 */
	public Map<String, Object> unlockTansCard(Map<String, Object> param) throws Exception;
	
	/**
	 * 验证交易卡是否存在
	 */
	public Map<String, Object> checkTransCard(Map<String, Object> param) throws Exception;
	
	/**
	 * 入网初始化
	 */
	public Map<String, Object> initialization(Map<String, Object> param) throws Exception;
}