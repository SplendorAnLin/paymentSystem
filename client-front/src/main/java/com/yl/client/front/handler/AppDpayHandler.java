package com.yl.client.front.handler;

import java.util.Map;

import com.yl.client.front.common.AppRuntimeException;

/**
 * App代付处理
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public interface AppDpayHandler extends AppHandler{
	/**
	 * 提现申请反馈
	 * @param param
	 * @return
	 * @throws AppRuntimeException
	 */
	Map<String,Object> goDrawCash(Map<String,Object> param) throws AppRuntimeException;
	/**
	 * 提现订单发起
	 * @param param
	 * @return
	 * @throws AppRuntimeException
	 */
	Map<String,Object> DrawCash(Map<String,Object> param) throws AppRuntimeException;
	/**
	 * 提现订单查询
	 * @param params
	 * @return
	 * @throws AppRuntimeException
	 */
	public Map<String, Object> selectRequest(Map<String, Object> params) throws AppRuntimeException;
	/**
	 * 提现订单查询详情
	 * @param params
	 * @return
	 * @throws AppRuntimeException
	 */
	public Map<String, Object> selectRequestDetailed(Map<String, Object> params) throws AppRuntimeException;
	
}