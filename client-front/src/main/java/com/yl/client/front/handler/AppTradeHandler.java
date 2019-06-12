package com.yl.client.front.handler;

import java.util.Map;

import com.yl.client.front.common.AppRuntimeException;

/**
 * App交易处理
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public interface AppTradeHandler extends AppHandler{
	/**
	 * 查询交易金额 笔数
	 * @param param
	 * @return
	 */
	Map<String, Object> today(Map<String,Object> param) throws Exception;
	/**
	 * 充值
	 * @param param
	 * @return
	 */
	Map<String, Object> recharge(Map<String,Object> param) throws Exception;
	/**
	 * 查询交易订单
	 * @param params
	 * @return
	 */
	Map<String, Object> selectTradeOrder(Map<String,Object> params) throws AppRuntimeException;
	/**
	 * 查询交易订单详细
	 * @param params
	 * @return
	 */
	Map<String, Object> selectTradeOrderDetailed(Map<String,Object> params) throws AppRuntimeException;
	
	/**
	 * 微信条码支付
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> barcodePay(Map<String,Object> param) throws Exception;
}