package com.yl.payinterface.core.handler;

import java.util.Map;

/**
 * 订单关闭接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年09月22日
 * @version V1.0.0
 */
public interface CloseOrderHandler {
	
	/**
	 * 订单关闭
	 * @param closeOrderParams
	 * @return
	 */
	public Map<String, String> closeOrder(Map<String, String> closeOrderParams);

}
