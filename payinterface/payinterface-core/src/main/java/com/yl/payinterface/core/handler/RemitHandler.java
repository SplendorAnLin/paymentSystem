package com.yl.payinterface.core.handler;

import java.util.Map;

/**
 * 付款交易接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年09月22日
 * @version V1.0.0
 */
public interface RemitHandler {

	/**
	 * @Description 付款
	 * @param map 付款单hessian实体
	 * @return Map<String,Object>
	 */
	Map<String,String> remit(Map<String, String> map);

	/**
	 * @Description 查詢付款
	 * @param map 付款批次hessian实体
	 * @return Map<String,Object>
	 */
	Map<String,String> query(Map<String, String> map);

}
