package com.yl.payinterface.core.handler;

import java.util.Map;

import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * 钱包支付交易接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年09月22日
 * @version V1.0.0
 */
public interface WalletPayHandler {

	/**
	 * 交易接口
	 * @param map 钱包支付交易参数
	 * @return Map<String, String>
	 */
	Map<String, String> pay(Map<String, String> map);

	/**
	 * 查詢接口
	 * @param map 钱包支付查询参数
	 * @return Map<String, String>
	 */
	Map<String, String> query(Map<String, String> map);
	
	/**
	 * 完成接口
	 * @param map 钱包支付完成参数
	 * @return
	 */
	InterfaceRequest complete(Map<String, Object> map);

}
