package com.yl.client.front.handler;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.client.front.common.AppRuntimeException;

/**
 * App商户处理
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public interface AppCustomerHandler extends AppHandler{
	
	/**
	 * 商户登陆
	 * @param param
	 * @return
	 * @throws AppRuntimeException
	 */
	public Map<String,Object> login(Map<String,Object> param) throws AppRuntimeException;
	
	/**
	 * 商户信息
	 * @param param
	 * @return
	 * @throws AppRuntimeException
	 */
	public Map<String,Object> getLoginInfo(Map<String,Object> param) throws AppRuntimeException;
	
	/**
	 * 商户密码修改
	 */
	public Map<String,Object> appUpdatePassword(Map<String,Object> param) throws AppRuntimeException;
	
	/**
	 * 查询文档信息
	 * @param params
	 * @return
	 */
	public Map<String,Object> queryProtocolManagements(Map<String, Object> params) throws AppRuntimeException;
	
	/**
	 * 认证支付
	 */
	public Map<String, Object> authpay(Map<String, Object> params) throws AppRuntimeException;
	/**
	 * 查询设备信息
	 * @param params
	 * @return
	 * @throws AppRuntimeException
	 */
	public Map<String, Object> getDevices(Map<String, Object> params) throws AppRuntimeException;
	/**
	 * App POS申请
	 */
	public Map<String, Object> appApplication(Map<String, Object> params) throws AppRuntimeException;
	
	/**
	 * APP 水牌申请
	 */
	public Map<String, Object> appDevice(Map<String, Object> params) throws AppRuntimeException;
	
	/**
	 * APP 获取水牌类型
	 */
	public Map<String, Object> getDevicesInfo(Map<String, Object> params) throws AppRuntimeException;
	
	/**
	 * 商户状态查询
	 */
	public Map<String, Object> queryCustomerStatus(Map<String, Object> params) throws AppRuntimeException;
	
	/**
	 * 商户结算卡
	 */
	public Map<String, Object> custSettleCard(Map<String, Object> params) throws AppRuntimeException;
	
	/**
	 * 修改商户结算卡
	 */
	public Map<String, Object> updateCreateSettle(Map<String, Object> params) throws AppRuntimeException;
	
	/**
	 * 
	 * @Description 修改商户基本信息
	 * @param params
	 * @return
	 * @throws AppRuntimeException
	 * @date 2017年9月24日
	 */
	public Map<String, Object> updateCustomerBaseInfo(Map<String, Object> params) throws AppRuntimeException;
	
	/**
	 * 查询结算卡信息
	 */
	public Map<String, Object> querySettle(Map<String, Object> params) throws AppRuntimeException;

	/**
	 * 微信基础信息获取
	 */
	public Map<String, Object> wechatInfo(Map<String, Object> params) throws AppRuntimeException;
}